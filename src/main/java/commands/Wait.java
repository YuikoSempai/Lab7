
package commands;

import data.Ticket;
import utility.CollectionManager;
import utility.Respond;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * The command class for save.
 */
public class Wait extends AbstractCommand {
    /**
     * Class constructor
     *
     * @param collection class for show elements from collection
     */
    public Wait(CollectionManager collection) {
        super(collection);
    }

    /**
     * print all elements of collection.
     * @param argument empty list
     */
    @Override
    public Respond execute(Object argument) {
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Respond("is waiting");
    }
}
