package data;

import java.io.Serializable;

/**
 * Enum for ticket type
 */
public enum TicketType implements Serializable {
    VIP("VIP"),
    USUAL("USUAL"),
    BUDGETARY("BUDGETARY"),
    CHEAP("CHEAP");

    final String type;

    TicketType(String s){
        type = s;
    }

    public String getType(){
        return type;
    }
}