package commands;

import data.Ticket;
import utility.CollectionManager;
import utility.Respond;

import java.util.function.Consumer;

/**
 * Class for command update_id
 */
public class Update_id extends AbstractCommand {
    final int countOfArguments = 10
    ;
    /**
     * Class constructor
     *
     * @param collection class for update elements from collection
     */
    public Update_id(CollectionManager collection) {
        super(collection);
    }

    /**
     * Update the id of the object that based on input values.
     * @param argument id of element which we need to update
     * @return Respond(String answer)
     */
    @Override
    public Respond execute(Object argument) {
        for(Ticket ticket: collectionManager.getAllElements()){
            if(ticket.getId() == (Long) argument){
                ticket.updateId();
            }
        }
        return new Respond("the id has been updated");
    }
    //TODO:: rework for DataBase
}