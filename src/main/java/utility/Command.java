package utility;

import java.io.Serializable;

public class Command implements Serializable {


    private final String commandName;
    private String username;
    private Object argument;

    public Command(String aCommandName){
        commandName = aCommandName;
    }

    public void setArgument(Object aArgument){
        this.argument = aArgument;
    }
    public Object getArgument(){
        return argument;
    }
    public String getCommandName(){
        return commandName;
    }
    public String getUsername(){return username;}
    public void setUsername(String aUsername){
        username = aUsername;
    }
}
