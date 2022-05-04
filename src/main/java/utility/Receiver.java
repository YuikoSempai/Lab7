package utility;

import dbutility.DBWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

public class Receiver {
    private static final Logger logger = LogManager.getLogger("Server");
    static DatagramSocket datagramSocket;
    static ByteBuffer receivingDataBuffer = ByteBuffer.allocate(10240);;
    static DatagramPacket inputPacket;
    private final static CollectionManager collectionManager = new CollectionManager();
    private static final Invoker invoker = new Invoker(collectionManager);

    public Receiver(DatagramSocket aDatagramSocket){
        datagramSocket = aDatagramSocket;
    }

    public Respond receive() throws IOException, ClassNotFoundException {
        Object inputObject = read();
        try {
            Command command = (Command) inputObject;
            logger.info("Transmitted command: " + command.getCommandName());
            return invoker.execute(command);
        }catch (Exception e){
            Session session = (Session) inputObject;
            System.out.println(session.getUsername() + " " + session.getPassword());
            if(DBWorker.checkUser(session)){
                if(DBWorker.checkPassword(session)){
                    logger.info("user:" + session.getUsername() + " is exists");
                    return new Respond("true");
                }else{
                    logger.info("wrong password for " + session.getUsername());
                    return new Respond("false");
                }
            }else {
                logger.info("user:" + session.getUsername() + " isn't exists");
                DBWorker.addUser(session);
                return new Respond("true");
            }
        }
    }

    public DatagramPacket getInputPacket() {
        return inputPacket;
    }

    private static Object read() throws IOException, ClassNotFoundException {
        receivingDataBuffer.clear();
        inputPacket = new DatagramPacket(receivingDataBuffer.array(), receivingDataBuffer.capacity());
        datagramSocket.receive(inputPacket);
        logger.info("Package received");
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData()));
        return objectInputStream.readObject();
    }
}
