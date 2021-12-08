package Server;

import Client.AskManager;
import Lib.MainConsole;
import Lib.Request;
import Lib.User;
import Server.Db.DbHandler;
import Server.Program.CollectionManager;
import Server.Program.CommandManager;
import Server.Program.Console;
import Server.Program.FileManager;
import Server.Commands.*;


import java.util.Scanner;
import java.util.logging.Logger;

public class MainServer {

    public static Logger logger = Logger.getLogger("ServerLogger");
    public static int port = 3578;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    private static String dbUser = "s311697";
    private static String dbHost = "localhost:5432";
    private static String dbPassword;
    private static String dbAddress = "jdbc:postgresql://" + dbHost + "/studs";

    public static void main(String[] args) throws InterruptedException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (args.length != 2) {
            System.out.println("Должены быть передаты порт и пароль БД");
            System.exit(0);
        }
        port = Integer.parseInt(args[0]);
        dbPassword = args[1];


        Scanner scanner = new Scanner(System.in);

        AskManager askManager = new AskManager(scanner);
        Console console = new Console(scanner, askManager);
        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new LoginCommand(),
                new RegisterCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new ClearCommand(collectionManager),
                new ExecuteScriptCommand(console),
                new ExitCommand(),
                new ExitsCommand(collectionManager),
                //new SaveCommand(collectionManager),
                new AddIfMaxCommand(collectionManager),
                new AddIfMinCommand(collectionManager),
                new RemoveLowerCommand(collectionManager),
                new CountByPositionCommand(collectionManager),
                new RemoveByStatusCommand(collectionManager),
                new PrintAscendingCommand(collectionManager)
        );
        console.setCommandManager(commandManager);


        ServerConsole serverConsole = new ServerConsole(scanner);
        RequestIn requestIn = new RequestIn(commandManager);
        Thread threadForReceiveFromTerminal = new Thread() {
            @Override
            public void run() {
                while (true) {
                    User user = new User("admin", "1234");
                    Request req = serverConsole.handle(1, user);
                    MainConsole.println(requestIn.handle(req).getResponseBody());
                }

            }
        };
        Thread startReceiveFromServerTerminal = new Thread(threadForReceiveFromTerminal);
        startReceiveFromServerTerminal.start();

        DbHandler dbHandler = new DbHandler(dbAddress, dbUser, dbPassword, collectionManager);


        Server server = new Server(port, CONNECTION_TIMEOUT, requestIn, dbHandler);
        collectionManager.addDbHandlerToCM();
        dbHandler.connectionToDb();
        collectionManager.loadCollection();
        server.run();
    }
}
