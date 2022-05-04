package utility;

import data.Coordinates;
import data.Person;
import data.Ticket;
import data.TicketType;
import exceptions.ArgumentFromFileException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Validator {
    private final Map<String, Command> commandMap = new HashMap<>();
    private final Console console;

    public Validator(Console aConsole) {
        console = aConsole;
        initMap();
    }

    public Command validateCommand(String command) {
        return commandMap.getOrDefault(command, null);
    }

    public Object validateArgument(Command command) throws ArgumentFromFileException {
        String commandName = command.getCommandName();
        if(console.readFromFileStatus){
            return validateArgumentFromFile(command);
        }
        try {
            if (commandName.contains("add") || commandName.equals("remove_greater") || commandName.equals("remove_lower")
                    || commandName.equals("update_id")) {
                System.out.println("\tEnter the ticket name");
                String name = console.nextLine();
                System.out.println("\tEnter the ticket price (price > 0)");
                Double price = (Double) read(Double::valueOf);
                while (price <= 0) {
                    System.out.println("\tInput error, try again\n");
                    System.out.println("\tEnter the ticket price");
                    price = (Double) (read(Double::valueOf));
                }
                System.out.println("\tEnter the comment field");
                String comment = console.nextLine();
                System.out.println("\tEnter the x coordinate (x > -960)");
                Integer x = (Integer) read(Integer::valueOf);
                while (x <= -960) {
                    System.out.println("\tInput error, try again\n");

                    System.out.println("\tEnter the x coordinate");
                    x = (Integer) (read(Integer::valueOf));
                }
                System.out.println("\tEnter the y coordinate");
                Float y = (Float) read(Float::valueOf);
                System.out.println("\tSelect and enter the ticket type:");
                for (TicketType type : TicketType.values()) {
                    System.out.println("\t" + type);
                }
                TicketType type = (TicketType) read(TicketType::valueOf);
                System.out.println("\tEnter the date of birth of the character in the format yyyy-MM-dd");
                LocalDate localDate = (LocalDate) read((String str) -> LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE));
                System.out.println("\tEnter the character's height");
                Float height = (Float) read(Float::valueOf);
                while (height <= 0) {
                    System.out.println("\tInput error, try again\n");
                    System.out.println("\tEnter the character's height");
                    height = (Float) (read(Float::valueOf));
                }
                System.out.println("\tEnter the character's weight");
                Integer weight = (Integer) read(Integer::valueOf);
                while (weight <= 0) {
                    System.out.println("\tInput error, try again\n");
                    System.out.println("\tEnter the character's weight");
                    weight = (Integer) (read(Integer::valueOf));
                }
                System.out.println("\tEnter the character's passport Id");
                String passportId = console.nextLine();
                return new Ticket(name, new Coordinates(x, y), price, comment, type,
                        new Person(localDate.atStartOfDay(), height, weight, passportId));
            } else {
                if (commandName.equals("remove_any_by_type")) {
                    System.out.println("\tSelect and enter the ticket type:");
                    for (TicketType type : TicketType.values()) {
                        System.out.println(type);
                    }
                    return read(TicketType::valueOf);
                }
                if (commandName.equals("remove_by_id")) {
                    System.out.println("\tEnter the id");
                    return console.nextLine();
                }
                if (commandName.equals("execute_script")) {
                    System.out.println("\tEnter the path to the script");
                    return console.nextLine();
                }
            }
        } catch (Exception e) {
            throw new ArgumentFromFileException("Error in the input data in the file");
        }
        return null;
    }


    public Object validateArgumentFromFile(Command command) throws ArgumentFromFileException {
        String commandName = command.getCommandName();
        try {
            if (commandName.contains("add") || commandName.equals("remove_greater") || commandName.equals("remove_lower")
                    || commandName.equals("update_id")) {
                String name = console.nextLine();
                Double price = (Double) read(Double::valueOf);
                if (price < 0) {
                    throw new Exception();
                }
                String comment = console.nextLine();
                Integer x = (Integer) read(Integer::valueOf);
                if (x <= -960) {
                    throw new Exception();
                }
                Float y = (Float) read(Float::valueOf);
                TicketType type = (TicketType) read(TicketType::valueOf);
                LocalDate localDate = (LocalDate) read((String str) -> LocalDate.parse(str, DateTimeFormatter.ISO_LOCAL_DATE));
                Float height = (Float) read(Float::valueOf);
                if (height<=0){
                    throw new Exception();
                }
                Integer weight = (Integer) read(Integer::valueOf);
                if (weight <= 0) {
                    throw new Exception();
                }
                String passportId = console.nextLine();
                return new Ticket(name, new Coordinates(x, y), price, comment, type,
                        new Person(localDate.atStartOfDay(), height, weight, passportId));
            }
            if (commandName.equals("remove_any_by_type")) {
                return read(TicketType::valueOf);
            }
            if (commandName.equals("remove_by_id")) {
                System.out.println("\tEnter the id");
                return console.nextLine();
            }
            if (commandName.equals("execute_script")) {
                System.out.println("\tEnter the path to the script");
                return console.nextLine();
            }
        }catch (Exception e){
            throw new ArgumentFromFileException("Error in the input data in the file");
        }
        return null;
    }
    private Object read(Function<String, Object> check){
        while (true) {
            String str = console.nextLine();
            if (!console.readFromFileStatus) {
                try {
                    return check.apply(str);
                } catch (Exception e) {
                    System.out.println("\tInput error, try again");
                    System.out.println("\t" + e.getMessage() + "\n");
                }
            } else {
                return check.apply(str);
            }
        }
    }

    public void initMap() {
        commandMap.put("add", new Command("add"));
        commandMap.put("add_if_max", new Command("add_if_max"));
        commandMap.put("clear", new Command("clear"));
        commandMap.put("execute_script", new Command("execute_script"));
        commandMap.put("exit", new Command("exit"));
        commandMap.put("group_counting_by_price", new Command("group_counting_by_price"));
        commandMap.put("help", new Command("help"));
        commandMap.put("info", new Command("info"));
        commandMap.put("print_field_descending_comment", new Command("print_field_descending_comment"));
        commandMap.put("remove_any_by_type", new Command("remove_any_by_type"));
        commandMap.put("remove_greater", new Command("remove_greater"));
        commandMap.put("remove_lower", new Command("remove_lower"));
        commandMap.put("show", new Command("show"));
        commandMap.put("remove_by_id", new Command("remove_by_id"));
        commandMap.put("update_id", new Command("update_id"));
    }
}
