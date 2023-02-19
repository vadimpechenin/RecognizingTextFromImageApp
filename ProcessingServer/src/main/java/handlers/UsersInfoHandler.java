package handlers;

import classes.RequestCode;
import classes.Session;
import classes.UsersInfo;
import core.CommonUtils;
import core.SessionManager;
import core.interaction.Request;
import core.interaction.RequestHandlerContainer;
import core.interaction.ResponseRecipient;
import core.interaction.requests.EditContentRequest;
import core.interaction.responses.ObjectResponse;
import core.securityManager.SecurityManager;
import dbclasses.User;

import java.time.Clock;
import java.util.List;

public class UsersInfoHandler extends RequestHandlerContainer {
    private final SessionManager sessionManager;
    private final SecurityManager securityManager;

    public UsersInfoHandler(SessionManager sessionManager, SecurityManager securityManager) {
        super();
        this.sessionManager = sessionManager;
        this.securityManager = securityManager;
        register(RequestCode.USERS_INFO.toString(), this::UsersInfo);
        register(RequestCode.CURRENT_USER_INFO.toString(), this::CurrentUserInfo);
    }

    private boolean UsersInfo(ResponseRecipient responseRecipient, Request requestBase) {
        EditContentRequest request = (EditContentRequest) requestBase;
        Session session = sessionManager.getSession(request.sessionID);
        session.lastActivityTime = Clock.systemDefaultZone().instant();

        String[] userIDs = CommonUtils.isNullOrEmpty(request.contentID) ? null : request.contentID.split("\\|");
        return HandleInfo(responseRecipient, request, session.getId(), userIDs);
    }

    private boolean CurrentUserInfo(ResponseRecipient responseRecipient, Request request) {
        Session session = sessionManager.getSession(request.sessionID);
        session.lastActivityTime = Clock.systemDefaultZone().instant();

        String[] userIDs = new String[] {session.currentUserID};
        return HandleInfo(responseRecipient, request, session.getId(), userIDs);
    }

    private boolean HandleInfo(ResponseRecipient responseRecipient, Request request, String sessionID, String[] userIDs) {
        boolean result;
        List<User> users = securityManager.getUsers(sessionID, userIDs);
        UsersInfo view = new UsersInfo(users);
        result = users.size() != 0;

        ObjectResponse response = new ObjectResponse(request.code, request.sessionID, result, view);

        if (responseRecipient != null) {
            responseRecipient.ReceiveResponse(response);
        }
        return true;
    }
}
