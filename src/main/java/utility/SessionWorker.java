package utility;

import utility.interfaces.SessionWorkerInterface;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;

public class SessionWorker implements SessionWorkerInterface {

    private final Console console;

    public SessionWorker(Console aConsole) {
        console = aConsole;
    }

    @Override
    public Session createSession(String username,String password) {
        return new Session(username, password);
    }
}
