package Client;

import Lib.Exceptions.CommandUsageException;
import Lib.Exceptions.ScriptRecursionException;
import Lib.Exceptions.WrongCommandException;
import Lib.MainConsole;
import Lib.User;
import Lib.WorkerSer;
import Lib.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class ClientConsole {
    private final int maxRewriteAttempts = 1;

    private Scanner userScanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public ClientConsole(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Receives user input.
     *
     * @param serverResponseStatus Last server's response status.
     * @return New request to server.
     */
    public Request handle(int serverResponseStatus, User user) {
        String userInput;
        String[] userCommand = {"", ""};
        int processingCode;
        int rewriteAttempts = 0;
        try {
            do {
                try {
                    if (fileMode() && (serverResponseStatus == 0 || serverResponseStatus == 2)) {
                        throw new WrongCommandException("В скрипте есть некорректная команда!");
                    }

                    while (fileMode() && !userScanner.hasNextLine()) {
                        userScanner.close();
                        userScanner = scannerStack.pop();
                    }
                    if (fileMode()) {
                        userInput = userScanner.nextLine();
                        if (!userInput.isEmpty()) {
                            MainConsole.println(userInput);
                        }
                    } else {
                        userInput = userScanner.nextLine();
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    MainConsole.println("Произошла ошибка при вводе команды!");
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                        MainConsole.println("Превышено количество попыток ввода!");
                        System.exit(0);
                    }
                } catch (WrongCommandException e) {
                    MainConsole.println(e.getMessage());
                }
                processingCode = processCommand(userCommand[0], userCommand[1]);
            } while (processingCode == 0 && !fileMode() || userCommand[0].isEmpty());
            try {
                if (fileMode() && (serverResponseStatus == 0 || processingCode == 0))
                    throw new WrongCommandException("В скрипте есть некорректная команда!");
                switch (processingCode) {
                    case 2:
                        WorkerSer prod = generateProduct();
                        return new Request(userCommand[0], userCommand[1], prod, user);
                    case 3:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursionException("Скрипты не могут вызываться рекурсивно!");
                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        MainConsole.println("Выполняю скрипт '" + scriptFile.getName() + "'...");
                        break;
                }
            } catch (FileNotFoundException exception) {
                MainConsole.println("Файл со скриптом не найден!");
            } catch (ScriptRecursionException exception) {
                MainConsole.println(exception.getMessage());
                throw new WrongCommandException("В скрипте есть некорректная команда!");
            }
        } catch (WrongCommandException exception) {
            MainConsole.println(exception.getMessage());
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return new Request("","", null, user);
        }
        return new Request(userCommand[0], userCommand[1], null, user);
    }

    /**
     * Processes the entered command.
     *
     * @return Status of code.
     */
    private int processCommand(String commandName, String commandArgument) {
        try {
            switch (commandName) {
                case "":
                    return 0;
                case "help":
                case "show":
                case "info":
                case "clear":
                case "exit":
                case "add_if_max":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "remove_all_by_status":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "remove_by_id":
                case "print_ascending":
                case "count_by_position":
                case "add_if_min":
                    if (commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "add":
                case "remove_lower":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    return 2;
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException();
                    return 2;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException();
                    return 3;
                default:
                    MainConsole.println("Команда '" + commandName + "' не найдена!");
                    return 0;
            }
        } catch (CommandUsageException exception) {
            MainConsole.println("Проверьте введённые аргументы на правильность, проверка наличия/отсутствия аргументов вызвало ошибку!");
            return 0;
        }
        return 1;
    }

    /**
     * Generates Product to add.
     *
     * @return Product to add.
     * @throws WrongCommandException When something went wrong in script.
     */
    private WorkerSer generateProduct() {
        AskManager askManager = new AskManager(userScanner);
        if (fileMode()) askManager.setFileMode();
        return new WorkerSer(
                askManager.askName(),
                askManager.askCoordinates(),
                LocalDateTime.now(),
                askManager.askSalary(),
                askManager.askPosition(),
                askManager.askStatus(),
                askManager.askOrganization()

        );
    }



    /**
     * Checks if UserHandler is in file mode now.
     *
     * @return Is UserHandler in file mode now boolean.
     */
    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}
