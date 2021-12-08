package Server.Db;

import Lib.Data.*;
import Lib.MainConsole;
import Server.Hasher;
import Server.Program.CollectionManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.TreeSet;

public class DbHandler {
    private String dbAdress;
    private String dbLogin;
    private String dbPassword;
    private Connection dbConnection;
    private CollectionManager collectionManager;
    private static final String ADD_USER_REQUEST = "INSERT INTO USERS (login, password) VALUES (?, ?)";
    private static final String GET_USERS_REQUEST = "SELECT * FROM USERS";
    private static final String GET_COLLECTION = "SELECT * FROM COLLECTION";
    private static final String ADD_COLLECTION_ELEMENT = "INSERT INTO COLLECTION (id, name, x, y, creationdate, salary, position,status, annualturnover, type, author) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String DELITE_ELEMENT = "DELETE FROM COLLECTION WHERE id = ?";
    public DbHandler(String dbAdress, String dbLogin, String dbPassword, CollectionManager collectionManager) {
        this.dbAdress = dbAdress;
        this.dbLogin = dbLogin;
        this.dbPassword = dbPassword;
        this.collectionManager = collectionManager;
    }

    public void connectionToDb() {
        try {
            dbConnection = DriverManager.getConnection(dbAdress, dbLogin, dbPassword);
            MainConsole.println("База данных подключена!");

        } catch (SQLException throwables) {
            MainConsole.println("Ошибка при подключении базы данных!");
        }

    }
    public synchronized boolean registerUser(String login, String password) throws SQLException {
        PreparedStatement addStatement = dbConnection.prepareStatement(ADD_USER_REQUEST);
        addStatement.setString(1, login);
        addStatement.setString(2, Hasher.hashPassword(password));
        addStatement.executeUpdate();
        addStatement.close();
        return true;
    }
    public synchronized boolean checkUser(String login) throws SQLException {

        try {
            PreparedStatement getStatement = dbConnection.prepareStatement(GET_USERS_REQUEST);
            ResultSet rs = getStatement.executeQuery();
            while (rs.next()) {
                String iterLogin = rs.getString("login");
                MainConsole.println(iterLogin);
                MainConsole.println(login);
                if (iterLogin.equals(login)) {

                    return true;
                }
            }
            getStatement.close();
        } catch (SQLException throwables) {
            MainConsole.println("Ошибка базы данных!");
            throwables.printStackTrace();
        }

        return false;
    }
    public synchronized boolean checkUserPass(String login, String password) throws SQLException {

        try {
            PreparedStatement getStatement = dbConnection.prepareStatement(GET_USERS_REQUEST);
            ResultSet rs = getStatement.executeQuery();
            while (rs.next()) {
                String iterLogin = rs.getString("login");
                String iterPass = rs.getString("password");
                if (iterLogin.equals(login) && iterPass.equals(Hasher.hashPassword(password))) {
                    return true;
                }
            }
            getStatement.close();
        } catch (SQLException throwables) {
            MainConsole.println("Ошибка базы данных!");
            throwables.printStackTrace();
        }

        return false;
    }

    public synchronized TreeSet<Worker> loadCollectionFromDB() {
        TreeSet<Worker> coll = new TreeSet<>();
        try {
            PreparedStatement getStatement = dbConnection.prepareStatement(GET_COLLECTION);
            ResultSet rs = getStatement.executeQuery();
            while (rs.next()) {
                Worker p = extractWorkerFromResult(rs);
                collectionManager.addToCollection(p);
            }
            getStatement.close();
        } catch (SQLException throwables) {
            MainConsole.println("Ошибка при загрузке коллекции!");
        }
        return coll;
    }
    public synchronized Worker extractWorkerFromResult(ResultSet rs) throws SQLException {
        Worker worker = null;
        worker = new Worker(
                (int) rs.getInt("id"),
                rs.getString("name"),
                new Coordinates(rs.getLong("x"), rs.getLong("y")),
                (LocalDateTime) rs.getDate("creationdate").toLocalDate().atStartOfDay(),
                rs.getFloat("salary"),
                Position.valueOf(rs.getString("position")),
                Status.valueOf(rs.getString("status")),
                new Organization(
                        rs.getLong("annualturnover"),
                        OrganizationType.valueOf(rs.getString("type"))

                )
        );
        return worker;
    }
    public synchronized boolean addWorkerToDb(Worker w, String author) {
        try {
            PreparedStatement addStatement = dbConnection.prepareStatement(ADD_COLLECTION_ELEMENT);

            addStatement.setInt(1, w.getId());
            addStatement.setString(2, w.getName());
            addStatement.setLong(3, w.getCoordinates().getX());
            addStatement.setLong(4, w.getCoordinates().getY());
            addStatement.setDate(5, java.sql.Date.valueOf(w.getCreationDate().toLocalDate()));
            addStatement.setFloat(6, (float) w.getSalary());
            addStatement.setString(7, w.getPosition().toString());
            addStatement.setString(8, w.getStatus().toString());
            addStatement.setLong(9, w.getOrganization().getAnnualTurnover());
            addStatement.setString(10, w.getOrganization().getType().toString());
            addStatement.setString(11, author);

            addStatement.executeUpdate();
            addStatement.close();
        } catch (SQLException throwables) {
            MainConsole.println("Ошибка добавления работника в БД!");
            throwables.printStackTrace();
            return false;
        }
        return true;
    }
    public synchronized boolean removeFromDb(int id, String author) {
        try {
            PreparedStatement checkStatement = dbConnection.prepareStatement(GET_COLLECTION);
            ResultSet rs = checkStatement.executeQuery();
            while (rs.next()) {
                int iterId = rs.getInt("id");
                String iterAuthor = rs.getString("author");
                if (iterId == id && iterAuthor.equals(author)) {
                    PreparedStatement removeStatement = dbConnection.prepareStatement(DELITE_ELEMENT);
                    removeStatement.setInt(1, id);
                    removeStatement.executeUpdate();
                    removeStatement.close();
                    return true;
                }
            }
            checkStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
