package commands;

import data.Ticket;
import utility.CollectionManager;
import utility.Console;
import utility.Respond;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

/**
 * The command class for save.
 */
public class Save extends AbstractCommand {
    final int countOfArguments = 1;
    /**
     * Class constructor
     *
     * @param collection       class for save elements from collection
     */
    public Save(CollectionManager collection) {
        super(collection);
    }


    /**
     * save collection into file.csv
     *
     * @param arguments empty list
     */
    @Override
    public Respond execute(Object argument){
        String outputFileName = (String) argument;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            writer.write("name;coordinates_x;coordinates_y;price;comment;type;" +
                    "person_birthday;person_height;person_weight;person_pasport_id");
            writer.write("\n");

            for (Ticket ticket : collectionManager.getAllElements()) {
                int year = ticket.getPerson().getBirthday().getYear();
                int month = ticket.getPerson().getBirthday().getMonthValue();
                int day = ticket.getPerson().getBirthday().getDayOfMonth();
                String dayStr= String.valueOf(day);
                String monthStr = String.valueOf(month);
                if(day<10){
                    dayStr = "0" + dayStr;
                }
                if (month<10){
                    monthStr = "0" + monthStr;
                }
                writer.write(ticket.getName() + ";" + ticket.getCoordinates().getX() + ";" + ticket.getCoordinates().getY() + ";" +
                        ticket.getPrice() + ";" + ticket.getComment() + ";" + ticket.getType() + ";" + dayStr + "-"
                        +monthStr+"-"+year
                        + ";" + ticket.getPerson().getHeight() + ";" + ticket.getPerson().getWeight() + ";" +
                        ticket.getPerson().getPassportID());
                writer.write("\n");
            }
        } catch (Exception e) {
            System.out.println("File access error");
        }

        return new Respond("File saved");
    }
}