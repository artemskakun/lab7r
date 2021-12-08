package Server.Commands;

import Lib.Exceptions.WrongNumberOfElementsException;
import Lib.User;
import Server.Program.CollectionManager;
import Server.ResponseOut;

public class SaveCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public SaveCommand(CollectionManager collectionManager){
        super("save","сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }
    /**
     * Executes the command
     * @return exit status of command
     * **/
    public boolean startExecute(String arg, Object o, User user){
        try {
            if (!arg.isEmpty()) throw new WrongNumberOfElementsException("У данной команды не должно быть аргумента!");
            collectionManager.saveCollection();
            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        return false;
    }
}