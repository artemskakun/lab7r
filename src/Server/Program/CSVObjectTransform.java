package Server.Program;

import Lib.Data.*;
import Server.Program.Console;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;

class CSVObjectTransform {
    /**
     *
     */
    CSVObjectTransform() {
    }
    private Date parseDate(String date) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.parse(date);
    }
    private String unParseDate(Date date) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date);
    }
    /**
     * @param csvline
     * @return
     */
    public Worker csvLineToWorker(String csvline) {
        String[] line = csvline.split(", ");
        if (line.length != 10) return null; // Routes need 14 parameters to be generated
        int id;
        String name;
        Long x;
        Long y;
        LocalDateTime creationDate;
        Double salary;
        Position position;
        Status status;
        Long annualTurnover;
        OrganizationType type;

        try {
            id = Integer.parseInt(line[0]);
            name = line[1];
            x = Long.parseLong(line[2]);
            y = Long.parseLong(line[3]);
            creationDate = LocalDateTime.parse(line[4]);
            salary = Double.parseDouble(line[5]);
            position = Position.valueOf(line[6]);
            status = Status.valueOf(line[7]);
            annualTurnover = Long.parseLong(line[8]);
            type = OrganizationType.valueOf(line[9]);


            Coordinates coordinates = new Coordinates(x, y);
            Organization organization = new Organization(annualTurnover,type);
            Worker generated = new Worker(id, name.trim(), coordinates, creationDate, salary, position, status, organization);
            return generated;
        } catch (NumberFormatException | DateTimeParseException e) {
            Console.println("Неверный формат данных в файле!");; // cordX cannot be null
            return null;
        }
    }

    /**
     * @param worker
     * @return
     */
    public String routeToCSVLine(Worker worker) throws ParseException {

        String id, name, x, y,
                creationDate, salary,
                position, status, annualTurnover, type;
        id = String.valueOf(worker.getId());
        name = worker.getName();
        x = String.valueOf(worker.getCoordinates().getX());
        y = String.valueOf(worker.getCoordinates().getY());
        creationDate = worker.getCreationDate().toString();
        salary = String.valueOf(worker.getSalary());
        position = String.valueOf(worker.getPosition());
        if (worker.getStatus() == null) status = "ZERO";
        else status = String.valueOf(worker.getStatus());
        annualTurnover = String.valueOf(worker.getOrganization().getAnnualTurnover());
        if (worker.getOrganization().getType() == null) type = "ZERO";
        else type = String.valueOf(worker.getOrganization().getType());
        String[] csvline = {id, name, x, y, creationDate, salary, position, status, annualTurnover, type};
        return String.join(", ", Arrays.asList(csvline));
    }
}