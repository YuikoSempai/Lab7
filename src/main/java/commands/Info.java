package commands;

import data.Ticket;
import utility.CollectionManager;
import utility.Respond;

import java.util.function.Consumer;

/**
 * The command class for info.
 */
public class Info extends AbstractCommand {
    /**
     * Class constructor
     *
     * @param collection class for work with collection
     */
    public Info(CollectionManager collection) {
        super(collection);
    }


    /**
     * Print information about collection(Type, data of init and count of objects).
     * @param argument empty list
     * @return
     */
    @Override
    public Respond execute(Object argument) {
        locker.lock();
        String s = "Type of Collection - TreeSet" + "\n" +
                "Date of init - " + collectionManager.getInitTime() + "\n" +
                "Count of object in Collection - " + collectionManager.size() + "\n";
        locker.unlock();
        return new Respond(s);
    }
}
