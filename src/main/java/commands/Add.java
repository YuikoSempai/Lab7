/**
 * The command class for add.
 */

package commands;

import com.sun.org.apache.regexp.internal.RE;
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
        Ticket ticket = (Ticket) argument;
        if (DBWorker.addTicket(getUsername(), ticket)) {
            collectionManager.add(ticket);
            return new Respond("Object added");
        }else{
            return new Respond("Can't add object");
        }
    }
}