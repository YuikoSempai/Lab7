package commands;

import data.Ticket;
import utility.CollectionManager;
import utility.Respond;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The command class for help.
 */
public class Help extends AbstractCommand {
    /**
     * Class constructor
     *
     * @param collection class for work with collection
     */
    public Help(CollectionManager collection) {
        super(collection);
    }

    /**
     * Print all available commands.
     * @param argument empty list
     * @return
     */
    @Override
    public Respond execute(Object argument) {
        List<Object> help = new ArrayList<>(); // зачем создавать этот список каждый раз, если его можно сделать static
        help.add("help : output help for available commands");
        help.add("info: output information about the collection (type, initialization date, number of items, etc.) to the standard output stream.)");
        help.add("show : output to the standard output stream all the elements of the collection in a string representation");
        help.add("add {element} : add a new element to the collection");
        help.add("update id {element} : update the value of a collection element whose id is equal to the specified one");
        help.add("remove_by_id id : remove an item from the collection by its id");
        help.add("clear : clear the collection");
        help.add("execute_script file_name : read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode.");
        help.add("exit : terminate the program (without saving to a file)");
        help.add("add_if_max {element} : add a new element to the collection if its value exceeds the value of the largest element of this collection");
        help.add("remove_greater {element} : remove from the collection all elements exceeding the specified");
        help.add("remove_lower {element} : remove all elements smaller than the specified one from the collection");
        help.add("remove_any_by_type type : remove one element from the collection whose type field value is equivalent to the specified one");
        help.add("group_counting_by_price : group the collection items by the value of the price field, output the number of items in each group");
        help.add("print_field_descending_comment : print the values of the comment field of all elements in descending order");
        StringBuilder ans = new StringBuilder();
        for (Object string : help) {
            ans.append(string).append("\n");
        }
        System.out.print(ans);
        return new Respond(ans.toString());
    }
}
