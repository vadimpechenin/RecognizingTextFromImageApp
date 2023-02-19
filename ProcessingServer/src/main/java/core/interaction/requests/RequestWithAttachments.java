package core.interaction.requests;

import core.interaction.Request;

import java.util.List;

public class RequestWithAttachments extends Request {
    public final List<byte[]> attachments;

    public RequestWithAttachments(String code, String sessionID, List<byte[]> attachments) {
        super(code, sessionID);
        this.attachments = attachments;
    }
}
