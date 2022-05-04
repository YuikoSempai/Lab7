package commands;

import data.Ticket;
import dbutility.DBWorker;
import utility.CollectionManager;
import utility.Respond;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * The command class for remove_by_id.
 */
public class Remove_by_id extends AbstractCommand{
    final int countOfArguments = 1;
    /**
     * Class constructor
     *
     * @param collection class for remove elements from collection
     */
    public Remove_by_id(CollectionManager collection) {
        super(collection);
    }


    /**
     * Removes an item from the collection if its id the input value.
     * @param argument id of elements which we need to remove
     * @return
     */
    @Override
    public Respond execute(Object argument) {
        Stream<Ticket> stream = collectionManager.getAllElements().stream();
        String username = getUsername();
        Stream<Ticket> test = stream.filter(x->x.getId() == Long.parseLong((String) argument));
        Object[] arr =  test.toArray();
        for(Object obj:arr){
            if(DBWorker.remove(username,(Ticket) obj)){
                collectionManager.remove((Ticket) obj);
            }
        }
        return new Respond("Objects have been deleted");
    }
}