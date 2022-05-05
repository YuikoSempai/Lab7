/**
 * Abstract command for all another commands.
 */
package commands;

import commands.interfaces.CommandInterface;
import utility.CollectionManager;
import utility.Respond;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public abstract class AbstractCommand implements CommandInterface {
    final CollectionManager collectionManager;
    final int countOfArguments = 0;
    private String username;
    protected Lock locker = new ReentrantLock();

    /**
     * @param collection collection
     */

    public AbstractCommand(CollectionManager collection) {
        collectionManager = collection;
    }

    /**
     * Executing the command
     *
     * @param argument arguments for our command
     */
    @Override
    public abstract Respond execute(Object argument);

    public int getCountOfArguments() {
        return countOfArguments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String aUsername) {
        username = aUsername;
    }
}
