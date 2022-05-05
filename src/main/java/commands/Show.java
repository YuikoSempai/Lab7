
package commands;

import data.Ticket;
import utility.CollectionManager;
import utility.Respond;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * The command class for save.
 */
public class Show extends AbstractCommand {
    /**
     * Class constructor
     *
     * @param collection class for show elements from collection
     */
    public Show(CollectionManager collection) {
        super(collection);
    }
    
    /**
     * print all elements of collection.
     * @param argument empty list
     * @return
     */
    @Override
    public Respond execute(Object argument) {
        locker.lock();
        StringBuilder ans = new StringBuilder();
        for (Ticket ticket : collectionManager.getAllElements()) {
            ans.append(ticket.toString()).append("\n");
        }
        if(collectionManager.size()==0){
            ans = new StringBuilder("At the moment the collection is empty");
            ans.append("\n");
        }
        Stream<Ticket> stream = collectionManager.getAllElements().stream();
        locker.unlock();
        return new Respond(ans.toString());
    }
}
