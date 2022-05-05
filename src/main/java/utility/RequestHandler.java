package utility;

import dbutility.DBWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;

public class RequestHandler implements Runnable{

    private final static Logger logger = LogManager.getLogger("Server");
    private final static CollectionManager collectionManager = new CollectionManager();
    private static final Invoker invoker = new Invoker(collectionManager);
    private final ExecutorService executorService;
    Object inputObject;
    public RequestHandler(Object inputObject, ExecutorService executorService){
        this.inputObject = inputObject;
        this.executorService = executorService;
    }

    public void run(){
        Respond respond;
        try {
            Command command = (Command) inputObject;
            logger.info("Transmitted command: " + command.getCommandName());
            respond = invoker.execute(command);
        }catch (Exception e){
            Session session = (Session) inputObject;
            System.out.println(session.getUsername() + " " + session.getPassword());
            if(DBWorker.checkUser(session)){
                if(DBWorker.checkPassword(session)){
                    logger.info("user:" + session.getUsername() + " is exists");
                    respond = new Respond("true");
                }else{
                    logger.info("wrong password for " + session.getUsername());
                    respond = new Respond("false");
                }
            }else {
                logger.info("user:" + session.getUsername() + " isn't exists");
                DBWorker.addUser(session);
                respond = new Respond("true");
            }
        }
        Deliver.setRespond(respond);

        executorService.submit(new Deliver());
        // respond to do
    }
}
