package Server.Commands;

import Lib.Exceptions.WrongNumberOfElementsException;
import Lib.User;
import Server.ResponseOut;

public class ExitCommand extends AbstractCommand {
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }
    /**
     * Start execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o, User user) {
        try {
            if (!arg.isEmpty() || o != null) throw new WrongNumberOfElementsException("У данной команды не должно быть аргументов!");

            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        return false;
    }
}