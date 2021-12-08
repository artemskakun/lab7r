package Server.Commands;

import Lib.Data.Worker;
import Lib.Exceptions.EmptyCollectionException;
import Lib.Exceptions.WrongNumberOfElementsException;
import Lib.User;
import Lib.WorkerSer;
import Server.Program.CollectionManager;
import Server.ResponseOut;

public class RemoveLowerCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public RemoveLowerCommand(CollectionManager collectionManager){
        super("remove_lower","удалить из коллекции все элементы, меньшие, чем заданный");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command
     * @return exit status of command
     * **/
    @Override
    public boolean startExecute(String arg, Object o, User user) {

        try{
            if (!arg.isEmpty() || o == null) throw new WrongNumberOfElementsException("У данной команды должен быть объектный аргумент!");
            if(collectionManager.collectionSize() == 0) throw new EmptyCollectionException("Коллекция пуста!");
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
            collectionManager.removeLower(prod, user);
            ResponseOut.println("Продукты успешно удалены!");
            return true;
        } catch (EmptyCollectionException | WrongNumberOfElementsException e){
            ResponseOut.println(e.getMessage());
            return false;
        }

    }
}