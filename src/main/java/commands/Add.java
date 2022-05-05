/**
 * The command class for add.
 */

package commands;

import data.Ticket;
import dbutility.DBWorker;
import utility.CollectionManager;
import utility.Respond;


public class Add extends AbstractCommand {
    final int countOfArguments = 10;

    /**
     * Class constructor
     *
     * @param collection class for add new elements to collection
     */
    public Add(CollectionManager collection) {
        super(collection);
    }

    public int getCountOfArguments() {
        return countOfArguments;
    }


    public Respond execute(Object argument) {
        Respond respond;
        locker.lock();
        Ticket ticket = (Ticket) argument;
        if (DBWorker.addTicket(getUsername(), ticket)) {
            collectionManager.add(ticket);
            respond = new Respond("Object added");
        } else {
            respond = new Respond("Can't add object");
        }
        locker.unlock();
        return respond;
    }
}