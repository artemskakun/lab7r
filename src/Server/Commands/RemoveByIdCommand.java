package Server.Commands;

import Lib.Data.Worker;
import Lib.Exceptions.CollectionIsEmptyException;
import Lib.Exceptions.ProductNotFoundException;
import Lib.Exceptions.WrongNumberOfElementsException;
import Lib.User;
import Server.Db.DbHandler;
import Server.Program.CollectionManager;
import Server.ResponseOut;
import Server.Server;

public class RemoveByIdCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }
    /**
     * Start execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o, User user) {
        try {
            if (arg.isEmpty()) throw new WrongNumberOfElementsException("Должен присутствовать строковый аргумент");
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException("Коллекция уже пуста!");
            int id = Integer.parseInt(arg);
            Worker workerToRemove = collectionManager.getById(id);
            if (workerToRemove == null) throw new ProductNotFoundException("Работника с таким id нет в коллекции!");
            DbHandler dbHandler = Server.getDbHandler();
            dbHandler.removeFromDb(Integer.parseInt(arg), user.getLogin());
            collectionManager.removeFromCollection(workerToRemove);
            ResponseOut.println("Работник успешно удален из коллекции!");
            return true;
        } catch (WrongNumberOfElementsException | CollectionIsEmptyException | ProductNotFoundException e) {
            ResponseOut.println(e.getMessage());
        }
        return false;
    }
}
