package Lib.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Worker
 */
public class Worker implements Serializable, Comparable<Worker> {

    private int id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double salary; //Значение поля должно быть больше 0
    private Position position; //Поле не может быть null
    private Status status; //Поле может быть null
    private Organization organization; //Поле может быть null


    public Worker(int id, String name, Coordinates coordinates, LocalDateTime creationDate, double salary, Position position, Status status, Organization organization) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.salary = salary;
        this.position = position;
        this.status = status;
        this.organization = organization;
    }


    /**
     * @return coordinates
     **/
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return name
     **/
    public String getName() {
        return this.name;
    }

    /**
     * @return id
     **/
    public int getId() {
        return this.id;
    }

    /**
     * @return creation date
     */
    public LocalDateTime getCreationDate(){ return creationDate;}

    /**
     * @return salary
     **/
    public double getSalary() {
        return salary;
    }

    /**
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @return status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return organization
     */
    public Organization getOrganization() {
        return organization;
    }


    @Override
    public int compareTo(Worker worker) {
        return Integer.compare(id, worker.getId());
    }

    @Override
    public String toString() {
        String info = "";
        info += "Работник №" + id;
        info += " (добавлен " + creationDate.toString() + ")";
        info += "\n Имя: " + name;
        info += "\n Местоположение: " + coordinates;
        info += "\n Зарплата: " + salary;
        info += "\n Поизция: " + position;
        info += "\n Статус: " + status;
        info += "\n Организация: " + organization;
        return info;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + coordinates.hashCode() + (int) salary + position.hashCode() + organization.hashCode() + status.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Worker) {
            Worker worker = (Worker) obj;
            return name.equals(getName()) && coordinates.equals(getCoordinates()) &&
                    (salary == getSalary()) && (position == getPosition()) &&
                    (status == getStatus()) && organization.equals(getOrganization());
        }
        return false;
    }
}