package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestReceiver implements Runnable {
    private static final Logger logger = LogManager.getLogger("Server");
    static DatagramSocket datagramSocket;
    static ByteBuffer receivingDataBuffer = ByteBuffer.allocate(10240);
    static DatagramPacket inputPacket;
    static ExecutorService executor;
    public RequestReceiver(DatagramSocket aDatagramSocket){
        datagramSocket = aDatagramSocket;
    }

    public static void setExecutor(ExecutorService executor) {
        RequestReceiver.executor = executor;
    }

    public void run() {
        while (true) {
            receivingDataBuffer.clear();
            inputPacket = new DatagramPacket(receivingDataBuffer.array(), receivingDataBuffer.capacity());
            try {
                datagramSocket.receive(inputPacket);
                logger.info("Package received");
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(inputPacket.getData()));
                Object receivedObject = objectInputStream.readObject();
                Deliver.setInputPacket(inputPacket);
                Deliver.setDatagramSocket(datagramSocket);

                RequestHandler requestHandler = new RequestHandler(receivedObject, executor);
                executor.submit(requestHandler);
            } catch (IOException | ClassNotFoundException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
