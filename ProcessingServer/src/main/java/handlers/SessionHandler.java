package handlers;

import classes.RequestCode;
import classes.Session;
import core.CommonUtils;
import core.SessionManager;
import core.interaction.Request;
import core.interaction.RequestHandlerContainer;
import core.interaction.Response;
import core.interaction.ResponseRecipient;
import core.interaction.requests.EditContentRequest;
import core.securityManager.SecurityManager;

import java.time.Clock;

public class SessionHandler extends RequestHandlerContainer {
    private final SessionManager sessionManager;
    private final SecurityManager securityManager;

    public SessionHandler(SessionManager sessionManager, SecurityManager securityManager) {
        super();
        this.sessionManager = sessionManager;
        this.securityManager = securityManager;
        register(RequestCode.SESSION_OPEN.toString(), this::sessionOpen);
        register(RequestCode.SESSION_CLOSE.toString(), this::sessionClose);
    }

    private boolean sessionOpen(ResponseRecipient responseRecipient, Request requestBase) {
        EditContentRequest request = (EditContentRequest) requestBase;
        boolean result = false;
        Session session = null;
        String sessionID;

        if (!CommonUtils.isNullOrEmpty(request.contentID)) {
            String username = request.contentID;
            String password = request.stringValue;
            String userID = securityManager.logOn(username, password);
            if (!CommonUtils.isNullOrEmpty(userID)) {
                session = sessionManager.openSession();
                session.currentUserID = userID;
                session.lastActivityTime = Clock.systemDefaultZone().instant();
                result = true;
            }
        }

        sessionID = (session != null) ? session.getId() : null;
        Response response = new Response(request.code, sessionID, result);

        if (responseRecipient != null) {
            responseRecipient.ReceiveResponse(response);
        }
        return true;
    }
    private boolean sessionClose(ResponseRecipient responseRecipient, Request request) {
        boolean result = false;
        Session session = sessionManager.getSession(request.sessionID);
        if (session != null) {
            sessionManager.closeSession(request.sessionID);
            result = true;
        }

        Response response = new Response(request.code, null, result);

        if (responseRecipient != null) {
            responseRecipient.ReceiveResponse(response);
        }
        return true;
    }
}
