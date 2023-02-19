package core;

import classes.Session;

import java.util.HashMap;

public class SessionManager {
    private final HashMap<String, Session> sessions;
    private final Object sessionsLock = new Object();

    public SessionManager() {
        sessions = new HashMap<>();
    }

    public Session openSession() {
        Session result = new Session();
        result.setId(CommonUtils.createID());
        synchronized (sessionsLock) {
            sessions.put(result.getId(), result);
        }
        return result;
    }

    public Session getSession(String sessionID){
        Session result;
        synchronized (sessionsLock) {
            result = sessions.get(sessionID);
        }
        return result;
    }

    public void closeSession(String sessionID){
        synchronized (sessionsLock) {
            sessions.remove(sessionID);
        }
    }
}
