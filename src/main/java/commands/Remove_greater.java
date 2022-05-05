package commands;

import data.Ticket;
import dbutility.DBWorker;
import utility.CollectionManager;
import utility.Respond;

import java.util.stream.Stream;

/**
 * The command class for remove_greater.
 */
public class Remove_greater extends AbstractCommand {
    final int countOfArguments = 10;
    /**
     * Class constructor
     *
     * @param collection class for remove elements from collection
     */
    public Remove_greater(CollectionManager collection) {
        super(collection);
    }


    /**
     * Removes an item from the collection if an object created based on input values is larger.
     * @param argument arguments for create a new ticket to compare.
     * @return
     */
    @Override
    public Respond execute(Object argument) {
        locker.lock();
        Stream<Ticket> stream = collectionManager.getAllElements().stream();
        Object[] objects = stream.filter(x -> x.compareTo((Ticket) argument) > 0).toArray();
        for(Object t: objects){
            if(DBWorker.remove(getUsername(),(Ticket) t)){
                collectionManager.remove((Ticket) t);
            }
        }
        locker.unlock();
        return new Respond("Objects have been deleted");
    }
}