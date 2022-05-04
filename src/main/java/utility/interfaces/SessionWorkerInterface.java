package utility.interfaces;

import utility.Session;

public interface SessionWorkerInterface {

    public Session createSession(String username,String password);
}
