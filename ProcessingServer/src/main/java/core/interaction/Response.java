package core.interaction;

public class Response {
    public final String code;
    public final String sessionID;
    public boolean result;

    public Response(String code, String sessionID, boolean result) {
        this.code = code;
        this.sessionID = sessionID;
        this.result = result;
    }
}
