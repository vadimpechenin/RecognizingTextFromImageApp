package handlers;

import classes.RequestCode;
import classes.Session;
import classes.UsersInfo;
import core.CommonUtils;
import core.SessionManager;
import core.interaction.Request;
import core.interaction.RequestHandlerContainer;
import core.interaction.Response;
import core.interaction.ResponseRecipient;
import core.interaction.requests.EditContentRequest;
import core.interaction.requests.EntityRequest;
import core.interaction.responses.ObjectResponse;
import core.securityManager.SecurityManager;
import dbclasses.User;

import java.security.Security;
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
        register(RequestCode.REGISTRATION_USER_INFO.toString(), this::RegistrationUserInfo);
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

    private boolean RegistrationUserInfo(ResponseRecipient responseRecipient, Request requestBase) {
        EntityRequest request = (EntityRequest) requestBase;
        boolean result = false;
        Session session = null;
        String sessionID;

        User registrationUser = (User) request.entity;
        result = registrationNewUser(registrationUser, result);
        if (!CommonUtils.isNullOrEmpty(registrationUser.getId())&&(result)) {
            session = sessionManager.openSession();
            session.currentUserID = registrationUser.getId();
            session.lastActivityTime = Clock.systemDefaultZone().instant();
        }

        sessionID = (session != null) ? session.getId() : null;
        Response response = new Response(request.code, sessionID, result);

        if (responseRecipient != null) {
            responseRecipient.ReceiveResponse(response);
        }

        return true;
    }

    private boolean registrationNewUser(User entity, boolean result) {
        if (entity!=null){
            try{
                securityManager.registration(entity);
                result = true;
            }
            catch(Exception e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
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
