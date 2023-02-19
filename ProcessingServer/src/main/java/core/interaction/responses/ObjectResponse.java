package core.interaction.responses;

import core.interaction.Response;

public class ObjectResponse extends Response {
    public final Object entry;
    public ObjectResponse(String code, String sessionID, boolean result, Object entry) {
        super(code, sessionID, result);
        this.entry = entry;
    }
}
