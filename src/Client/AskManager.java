package Client;

import Lib.Data.*;
import Lib.Exceptions.CoordinatesException;
import Lib.Exceptions.EmptyStringException;
import Lib.Exceptions.IncorrectInputException;
import Lib.MainConsole;

import java.io.BufferedReader;
import java.util.Scanner;

public class AskManager {
    private Scanner scanner;
    private BufferedReader fileReader;
    private MainConsole console;
    private Scanner userScanner;
    private boolean fileMode;

    private final Long MAX_X = 531L;
    private final Long MIN_Y = -958L;

    public AskManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setConsole(MainConsole console) {
        this.console = console;
    }

    /**
     * Sets a scanner to scan user input.
     *
     * @param userScanner Scanner to set.
     */
    public void setUserScanner(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * @return Scanner, which uses for user input.
     */
    public Scanner getUserScanner() {
        return userScanner;
    }

    /**
     * Sets marine asker mode to 'File Mode'.
     */
    public void setFileMode() {
        fileMode = true;
    }

    /**
     * Sets marine asker mode to 'User Mode'.
     */
    public void setUserMode() {
        fileMode = false;
    }

    /**
     * Asks the name
     *
     * @return name
     **/
    public String askName() {
        console.println("Введите имя работника: ");
        String name;
        while (true) {
            try {
                name = scanner.nextLine().trim();

                if (name == null) throw new NullPointerException();
                if (name.isEmpty()) throw new EmptyStringException("Данные не были введены!");
                break;
            } catch (EmptyStringException e) {
                System.out.println(e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("Empty string");

            }

        }
        return name;
    }

    /**
     * Asks the coordinates
     *
     * @return coordinate
     **/
    public Coordinates askCoordinates() {
        return new Coordinates(askX(), askY());
    }

    /**
     * Asks the X coordinate
     *
     * @return X coordinate
     **/
    public Long askX() {
        console.println("Введите координату X: ");
        Long x;
        while (true) {
            String rawX;
            try {

                rawX = scanner.nextLine().trim();

                if (rawX.isEmpty()) throw new EmptyStringException("Данные не были введены!");
                x = Long.parseLong(rawX);
                if (x > MAX_X) throw new IncorrectInputException("X координата не может быть больше " + MAX_X);
                break;
            } catch (EmptyStringException e) {
                System.out.println(e.getMessage());
            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
                return 0L;
            } catch (NullPointerException | NumberFormatException e) {
                System.out.println("Введите корректное значение!");

            }
        }

        return x;
    }

    /**
     * Asks the Y coordinate
     *
     * @return Y coordinate
     **/
    public Long askY() {
        console.println("Введите координату Y: ");
        Long y;
        while (true) {
            String rawY;
            try {

                rawY = scanner.nextLine().trim();

                if (rawY.isEmpty()) throw new EmptyStringException("Данные не были введены!");

                y = Long.parseLong(rawY);
                if (y <= MIN_Y) throw new IncorrectInputException("Y координата должна быть больше " + MIN_Y);
                break;
            } catch (EmptyStringException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Введите корректное значение!");

            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
                return 0L;
            }
        }

        return y;
    }

    /**
     * Asks price
     *
     * @return price
     **/
    public double askSalary() {
        console.println("Введите зарплату: ");
        double salary;
        while (true) {
            String rawSalary;
            try {

                rawSalary = scanner.nextLine().trim();

                if (rawSalary.isEmpty()) throw new EmptyStringException("Данные не были введены!");

                salary = Integer.parseInt(rawSalary);
                if (salary <= 0) throw new IncorrectInputException("Зарплата должна быть больше 0");
                break;
            } catch (EmptyStringException | IncorrectInputException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Введите корректное значение!");

            }
        }

        return salary;
    }


    /**
     * Asks the UnitOfMeasure
     *
     * @return UnitOfMeasure
     **/
    public Position askPosition() {
        Class positionClass = Position.class;
        console.println("Доступные позиции: ");

        for (Object enumConstant : positionClass.getEnumConstants()) {
            console.println(enumConstant);
        }
        console.println("Введите позицию: ");
        Position position;
        while (true) {
            String rawPosition;
            try {

                rawPosition = scanner.nextLine().trim();

                if (rawPosition.isEmpty()) throw new EmptyStringException("Данные не были введены!");
                position = Position.valueOf(rawPosition.toUpperCase());


            } catch (EmptyStringException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильно введена позиция");
                continue;
            }
            break;
        }

        return position;
    }

    /**
     * Asks the UnitOfMeasure
     *
     * @return UnitOfMeasure
     **/
    public Status askStatus() {
        Class statusClass = Status.class;
        console.println("Доступные статус: ");

        for (Object enumConstant : statusClass.getEnumConstants()) {
            console.println(enumConstant);
        }
        console.println("Введите статус: ");
        Status status;
        while (true) {
            String rawStatus;
            try {

                rawStatus = scanner.nextLine().trim();

                if (rawStatus.isEmpty()) throw new EmptyStringException("Данные не были введены!");
                status = Status.valueOf(rawStatus.toUpperCase());


            } catch (EmptyStringException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильно введен статус");
                continue;
            }
            break;
        }

        return status;
    }


    /**
     * Asks the annualTurnover
     *
     * @return annualTurnover
     **/
    public Long askAnnualTurnover() {
        console.println("Введите годовой оборот: ");
        Long annualTurnover;
        while (true) {
            String rawAnnualTurnover;
            try {

                rawAnnualTurnover = scanner.nextLine().trim();

                if (rawAnnualTurnover.isEmpty()) throw new EmptyStringException("Данные не были введены!");
                if (rawAnnualTurnover == null) throw new NullPointerException();
                annualTurnover = Long.parseLong(rawAnnualTurnover);
                if (annualTurnover < 0) throw new IncorrectInputException("Число должно быть больше 0");
                break;
            } catch (EmptyStringException e) {
                System.out.println(e.getMessage());
            } catch (IncorrectInputException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Введите корректное значение!");

            }
        }

        return annualTurnover;
    }

    /**
     * Asks the OrganizationType
     *
     * @return OrganizationType
     **/
    public OrganizationType askType() {
        Class typeClass = OrganizationType.class;
        console.println("Доступные типы организаций: ");

        for (Object enumConstant : typeClass.getEnumConstants()) {
            console.println(enumConstant);
        }
        console.println("Введите тип организации: ");
        OrganizationType type;
        while (true) {
            String rawType;
            try {

                rawType = scanner.nextLine().trim();

                if (rawType.isEmpty()) throw new EmptyStringException("Данные не были введены!");
                type = OrganizationType.valueOf(rawType.toUpperCase());
                if (type == null) throw new IllegalArgumentException();

            } catch (EmptyStringException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильно введена константа");
                continue;
            }
            break;
        }

        return type;
    }

    public Organization askOrganization() {
        Long annualTurnover = askAnnualTurnover();
        OrganizationType type = askType();
        return new Organization(annualTurnover, type);
    }


}