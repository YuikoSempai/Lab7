package commands;

import data.Ticket;
import dbutility.DBWorker;
import utility.CollectionManager;
import utility.Respond;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * The command class for remove_lower.
 */
public class Remove_lower extends AbstractCommand {
    final int countOfArguments = 10;
    /**
     * Class constructor
     *
     * @param collection class for remove elements from collection
     */
    public Remove_lower(CollectionManager collection) {
        super(collection);
    }

    /**
     * Removes an item from the collection if an object created based on input values is lower.
     * @param argument arguments for create a new ticket to compare.
     * @return
     */
    @Override
    public Respond execute(Object argument) {
        Stream<Ticket> stream = collectionManager.getAllElements().stream();
        Object[] objects = stream.filter(x -> x.compareTo((Ticket) argument) < 0).toArray();
        for(Object t: objects){
            if(DBWorker.remove(getUsername(),(Ticket) t)){
                collectionManager.remove((Ticket) t);
            }
        }
        return new Respond("Objects have been deleted");
    }
}