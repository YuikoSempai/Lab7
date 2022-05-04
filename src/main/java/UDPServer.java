//Должен быть реализован на датаграммах

import dbutility.DBInit;
import dbutility.DBWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.*;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.file.attribute.UserPrincipalLookupService;

public class UDPServer {
    private static final Logger logger = LogManager.getLogger("Server");
    static DatagramSocket datagramSocket;
    static ByteBuffer sendingDataBuffer = ByteBuffer.allocate(10240);
    static DatagramPacket inputPacket;
    static final int PORT = 50111;

    public static void main(String[] args) {
        logger.info("The server has started working");
        DBWorker.init();
        try {
            datagramSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        Receiver receiver = new Receiver(datagramSocket);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Respond respond = receiver.receive();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                }
                inputPacket = receiver.getInputPacket();
            }
        };
        try {
            while (true) {
                Respond respond = receiver.receive();
                inputPacket = receiver.getInputPacket();
                write(respond);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        datagramSocket.close();
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

    private static void write(Respond respond) throws IOException {
        sendingDataBuffer.clear();
        sendingDataBuffer.put(serialize(respond));
        InetAddress senderAddress = inputPacket.getAddress();
        int senderPort = inputPacket.getPort();
        DatagramPacket outputPacket = new DatagramPacket(sendingDataBuffer.array(), sendingDataBuffer.limit(), senderAddress, senderPort);
        datagramSocket.send(outputPacket);
        logger.info("The package has been sent");
    }
}
