package Lib;

import Lib.Data.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class WorkerSer implements Serializable {
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private double salary;
    private Position position;
    private Status status;
    private Organization organization;

    public WorkerSer(String name, Coordinates coordinates, LocalDateTime creationDate, double salary,
                     Position position, Status status, Organization organization) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.salary = salary;
        this.position = position;
        this.status = status;
        this.organization = organization;

    }

    /**
     * @return ID of the product.
     */
    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getLDT() {
        return creationDate;
    }

    public double getSalary() {
        return salary;
    }

    public Position getPosition() {
        return position;
    }

    public Status getStatus() {
        return status;
    }

    public Organization getOrganization() {
        return organization;
    }



    @Override
    public String toString() {
        String info = "";
        info += " (добавлен " + creationDate.toString() + ")";
        info += "\n Имя: " + name;
        info += "\n Местоположение: " + coordinates;
        info += "\n Зарплата: " + salary;
        info += "\n Поизция: " + position;
        info += "\n Статус: " + status;
        info += "\n Организация: " + organization;
        return info;
    }
}