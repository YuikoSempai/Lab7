package commands;

import data.Ticket;
import utility.CollectionManager;
import utility.Respond;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * The command class for group_counting_by_price.
 */
public class Group_counting_by_price extends AbstractCommand{
    /**
     * Class constructor
     *
     * @param collection class for work with collection
     */
    public Group_counting_by_price(CollectionManager collection) {
        super(collection);
    }
    
    /**
     * Groups objects by price and displays the number of objects in each group
     * @param argument empty list
     * @return
     */
    @Override
    public Respond execute(Object argument) {
        locker.lock();
        Respond respond = new Respond(
                collectionManager.getAllElements()
                .stream()
                .collect(groupingBy(Ticket::getPrice, Collectors.counting()))
                .entrySet()
                    .stream()
                    .map(e-> e.getKey() + " " + e.getValue())
                    .collect(joining("\n"))
        );
        locker.unlock();
        return respond;
    }
}
