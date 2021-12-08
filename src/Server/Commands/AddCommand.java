package Server.Commands;

import Lib.Data.Worker;
import Lib.Exceptions.WrongNumberOfElementsException;
import Lib.User;
import Lib.WorkerSer;
import Server.Db.DbHandler;
import Server.Program.CollectionManager;
import Server.ResponseOut;
import Server.Server;

public class AddCommand extends AbstractCommand {

    private CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager) {
        super("add", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
    }


    /**
     * Execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o, User user) {
        try {
            if (!arg.isEmpty() || o == null) throw new WrongNumberOfElementsException("У данной команды не должно быть строкового аргумента, но должен быть передан объект!");
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
                collectionManager.addToCollection(prod);
                ResponseOut.println("Продукт успешно добавлен в коллекцию!");
                return true;
            }
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        } catch (ClassCastException exception) {
            ResponseOut.println("Переданный клиентом объект неверен!");
        }
        return false;
    }
}