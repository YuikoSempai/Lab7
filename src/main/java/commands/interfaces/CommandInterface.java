package commands.interfaces;


import data.Ticket;
import utility.Respond;

import java.util.function.Consumer;

public interface CommandInterface {
    Respond execute(Object argument);
    int getCountOfArguments();
    
}
