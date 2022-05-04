package utility;


import commands.*;

import java.util.HashMap;
import java.util.Map;

public class Invoker {
    private final CollectionManager collectionManager;
    private final Map<String, AbstractCommand> commands;
    
    public Invoker(CollectionManager aCollectionManager){
        collectionManager = aCollectionManager;
        commands = new HashMap<>();
        initMap();
    }

    public Respond execute(Command newCommand){
        AbstractCommand command = commands.get(newCommand.getCommandName());
        command.setUsername(newCommand.getUsername());
        return command.execute(newCommand.getArgument());
    }


    public void initMap(){
        commands.put("add", new Add(collectionManager));
        commands.put("add_if_max", new Add_if_max(collectionManager));
        commands.put("clear", new Clear(collectionManager));
        commands.put("exit", new Exit(collectionManager));
        commands.put("group_counting_by_price", new Group_counting_by_price(collectionManager));
        commands.put("help", new Help(collectionManager));
        commands.put("info", new Info(collectionManager));
        commands.put("print_field_descending_comment", new Print_field_descending_comment(collectionManager));
        commands.put("remove_any_by_type", new Remove_any_by_type(collectionManager));
        commands.put("remove_greater", new Remove_greater(collectionManager));
        commands.put("remove_lower", new Remove_lower(collectionManager));
        commands.put("show", new Show(collectionManager));
        commands.put("remove_by_id", new Remove_by_id(collectionManager));
        commands.put("update_id", new Update_id(collectionManager));
    }
}
