package Server.Program;

import Lib.Data.Position;
import Lib.Data.Status;
import Lib.Data.Worker;
import Lib.User;
import Server.Db.DbHandler;
import Server.Server;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.NavigableSet;
import java.util.TreeSet;

public class CollectionManager {
    private NavigableSet<Worker> workersCollection = new TreeSet<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private DbHandler dbHandler;

    public CollectionManager() {
    }
    public void addDbHandlerToCM() {
        dbHandler  = Server.getDbHandler();
    }
    /**
     * @return The collecton itself.
     */
    public NavigableSet<Worker> getCollection() {
        return workersCollection;
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return workersCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return workersCollection.size();
    }

    /**
     * @return The first element of the collection or null if collection is empty.
     */
    public Worker getFirst() {
        if (workersCollection.isEmpty()) return null;
        return workersCollection.first();
    }

    /**
     * @return The last element of the collection or null if collection is empty.
     */
    public Worker getLast() {
        if (workersCollection.isEmpty()) return null;
        return workersCollection.last();
    }

    /**
     * @param id ID of the marine.
     * @return A marine by his ID or null if marine isn't found.
     */
    public Worker getById(int id) {
        for (Worker worker : workersCollection) {
            if (worker.getId() == id) return worker;
        }
        return null;
    }

    /**
     * @param workerToFind A worker who's value will be found.
     * @return A worker by his value or null if worker isn't found.
     */
    public Worker getByValue(Worker workerToFind) {
        for (Worker worker : workersCollection) {
            if (worker.equals(workerToFind)) return worker;
        }
        return null;
    }


    /**
     * @param positionToCount Position to count.
     * @return Count valid workers.
     */
    public Integer positionCountedInfo(Position positionToCount) {
        /*Integer info = 0;
        for (Worker worker : workersCollection) {
            if (worker.getPosition().equals(positionToCount)) {
                info += 1;
            }
        }*/
        Integer info = Math.toIntExact(workersCollection.stream().filter(ob -> ob.getPosition() == positionToCount).count());
        return info;
    }

    /**
     * Adds a new worker to collection.
     *
     * @param worker A worker to add.
     */
    public void addToCollection(Worker worker) {
        workersCollection.add(worker);
    }

    /**
     * Removes a new worker to collection.
     *
     * @param worker A worker to remove.
     */
    public void removeFromCollection(Worker worker) {
        workersCollection.remove(worker);
    }

    /**
     * Remove workers greater than the selected one.
     *
     * @param workerC A worker to compare with.
     */
    public void removeLower(Worker workerC, User user) {
        /** Iterator<Worker> it = workersCollection.iterator();
         while(it.hasNext()){
         Worker worker = it.next();
         if(worker.getSalary()<workerToCompare.getSalary()){
         it.remove();
         } }*/
        //workersCollection.removeIf(worker -> worker.compareTo(workerToCompare) < 0);
        for (Worker worker : workersCollection) {
            if (worker.compareTo(workerC) < 0) {
                int id = worker.getId();
                Worker workerR = getById(id);
                DbHandler dbHandler = Server.getDbHandler();
                dbHandler.removeFromDb(workerR.getId(), user.getLogin());
                removeFromCollection(workerR);
            }
        }
    }

    /**
     * Remove workers is equal to selected one.
     *
     * @param statusF A worker to compare with.
     */
    public void removeByStatus(Status statusF, User user) {
        for (Worker worker : workersCollection) {
            if (worker.getStatus().equals(statusF)) {
                int id = worker.getId();
                Worker workerR = getById(id);
                DbHandler dbHandler = Server.getDbHandler();
                dbHandler.removeFromDb(workerR.getId(), user.getLogin());
                removeFromCollection(workerR);
            }
        }
    }

    public void sortAscendingCommand() {

    }

    /**
     * Clears the collection.
     */
    public void clearCollection() {
        workersCollection.clear();
    }

    /**
     * Generates next ID. It will be (the bigger one + 1).
     *
     * @return Next ID.
     */
    public int generateNextId() {
        if (workersCollection.isEmpty()) return 1;
        return workersCollection.last().getId() + 1;
    }

    /**
     * Saves the collection to file.
     */
    public void saveCollection() {
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    public void loadCollection() {
        dbHandler.loadCollectionFromDB();
        lastInitTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        if (workersCollection.isEmpty()) return "Коллекция пуста!";

        String info = "";
        for (Worker worker : workersCollection) {
            info += worker;
            if (worker != workersCollection.last()) info += "\n\n";
        }
        return info;
    }
}