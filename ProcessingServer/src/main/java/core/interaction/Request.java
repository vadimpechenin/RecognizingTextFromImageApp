package core.interaction;

public class Request {
    public final String code;
    public final String sessionID;
    public Request(String code, String sessionID) {
        this.code = code;
        this.sessionID = sessionID;
    }
}
