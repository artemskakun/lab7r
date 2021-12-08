package Server.Commands;

import Lib.Exceptions.WrongNumberOfElementsException;
import Lib.User;
import Server.Program.CollectionManager;
import Server.ResponseOut;

public class PrintAscendingCommand extends AbstractCommand{
    private CollectionManager collectionManager;
    public PrintAscendingCommand(CollectionManager collectionManager) {
        super("print_ascending", "вывести элементы коллекции в порядке возрастания");
        this.collectionManager = collectionManager;
    }

    /**
     * Start execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o, User user) {
        try {
            if (!arg.isEmpty()) throw new WrongNumberOfElementsException("У данной команды не должно быть аргумента!");
            ResponseOut.println(collectionManager.toString());
            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        return false;
    }
}
