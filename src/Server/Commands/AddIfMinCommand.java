package Server.Commands;

import Lib.Data.Worker;
import Lib.Exceptions.WrongNumberOfElementsException;
import Lib.User;
import Lib.WorkerSer;
import Server.Db.DbHandler;
import Server.Program.CollectionManager;
import Server.ResponseOut;
import Server.Server;

public class AddIfMinCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        super("add_if_min", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o, User user) {

        try{
            if (!arg.isEmpty() || o != null) throw new WrongNumberOfElementsException("У данной команды не должно быть аргументов!");
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        }
        WorkerSer ps = (WorkerSer) o;
        Worker prod = new Worker(
                collectionManager.generateNextId(),
                ps.getName(),
                ps.getCoordinates(),
                ps.getLDT(),
                ps.getSalary(),
                ps.getPosition(),
                ps.getStatus(),
                ps.getOrganization()
        );
        DbHandler dbHandler = Server.getDbHandler();

        if (dbHandler.addWorkerToDb(prod, user.getLogin())) {
            if (collectionManager.collectionSize() == 0 || prod.compareTo(collectionManager.getLast()) < 0) {
                collectionManager.addToCollection(prod);
                ResponseOut.println("Работник успешно добавлен!");
                return true;
            } else ResponseOut.println("Значение работника меньше, чем значение наибольшего из работников!");
        }


        return true;

    }
}
