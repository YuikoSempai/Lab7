package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class Deliver implements Runnable{
    private static final Logger logger = LogManager.getLogger("Server");
    static DatagramSocket datagramSocket;
    static ByteBuffer sendingDataBuffer = ByteBuffer.allocate(10240);
    static DatagramPacket inputPacket;
    static Respond respond;

    public static void setDatagramSocket(DatagramSocket datagramSocket) {
        Deliver.datagramSocket = datagramSocket;
    }

    public static void setRespond(Respond respond) {
        Deliver.respond = respond;
    }

    public static void setInputPacket(DatagramPacket inputPacket) {
        Deliver.inputPacket = inputPacket;
    }

    private static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            } catch (Exception e) {
                logger.error("Serialization error");
            }
            return b.toByteArray();
        }
    }

    public void run() {
        sendingDataBuffer.clear();
        try {
            sendingDataBuffer.put(serialize(respond));
            InetAddress senderAddress = inputPacket.getAddress();
            int senderPort = inputPacket.getPort();
            DatagramPacket outputPacket = new DatagramPacket(sendingDataBuffer.array(), sendingDataBuffer.limit(), senderAddress, senderPort);
            datagramSocket.send(outputPacket);
            logger.info("The package has been sent");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
