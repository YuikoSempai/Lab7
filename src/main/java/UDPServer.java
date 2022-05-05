//Должен быть реализован на датаграммах

import dbutility.DBWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utility.RequestReceiver;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.*;

public class UDPServer {
    static final int PORT = 50111;
    private static final Logger logger = LogManager.getLogger("Server");
    static DatagramSocket datagramSocket;

    public static void main(String[] args) {
        logger.info("The server has started working");
        DBWorker.init();
        try {
            datagramSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        // Поток для получения запроса
        // Поток для обработки запроса
        // Поток для отправки ответа
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        RequestReceiver.setExecutor(executor);
        executorService.submit(new RequestReceiver(datagramSocket));
    }
}
