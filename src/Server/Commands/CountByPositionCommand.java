package Server.Commands;

import Lib.Data.Position;
import Lib.Data.Status;
import Lib.Exceptions.WrongNumberOfElementsException;
import Lib.User;
import Server.Program.CollectionManager;
import Server.ResponseOut;

public class CountByPositionCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public CountByPositionCommand(CollectionManager collectionManager) {
        super("count_by_position", "вывести количество элементов, значение поля position которых равно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute command.
     * @return Command status.
     */
    @Override
    public boolean startExecute(String arg, Object o, User user) {
        try {
            if (arg.isEmpty() || o != null) throw new WrongNumberOfElementsException("Должен присутствовать только строковый аргумент");
            Position argPos = Position.valueOf(arg.toUpperCase());
            ResponseOut.println(collectionManager.positionCountedInfo(argPos));
            return true;
        } catch (WrongNumberOfElementsException exception) {
            ResponseOut.println(exception.getMessage());
        } catch (IllegalArgumentException exception){
            ResponseOut.println("Позици нет в списке!");
            ResponseOut.println("Список позиций - " + Position.nameList());
        }
        return false;
    }
}
