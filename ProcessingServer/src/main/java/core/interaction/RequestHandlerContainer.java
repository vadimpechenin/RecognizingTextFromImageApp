package core.interaction;

import java.util.HashMap;
import java.util.Map;

public class RequestHandlerContainer implements RequestHandler {
    private final Map<String, RequestHandler> handlers;

    public RequestHandlerContainer() {
        handlers = new HashMap<>();
    }

    public Map<String, RequestHandler> getHandlers() {
        return handlers;
    }

    @Override
    public boolean executeRequest(ResponseRecipient responseRecipient, Request request) {
        RequestHandler handler = handlers.get(request.code);
        return handler.executeRequest(responseRecipient, request);
    }

    protected void register(String code, RequestHandler handler) {
        handlers.put(code, handler);
    }
}
