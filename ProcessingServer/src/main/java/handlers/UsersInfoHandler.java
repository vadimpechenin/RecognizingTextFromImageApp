package handlers;

import classes.RequestCode;
import classes.Session;
import classes.UsersInfo;
import core.CommonUtils;
import core.SessionManager;
import core.documentManager.DocumentManager;
import core.interaction.Request;
import core.interaction.RequestHandlerContainer;
import core.interaction.Response;
import core.interaction.ResponseRecipient;
import core.interaction.requests.EditContentRequest;
import core.interaction.requests.EntityWithViolationsRequest;
import core.interaction.responses.ObjectResponse;
import core.securityManager.SecurityManager;
import dbclasses.Document;
import dbclasses.User;
import validators.EmailValidator;
import validators.StringValidator;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

public class UsersInfoHandler extends RequestHandlerContainer {
    private final SessionManager sessionManager;
    private final SecurityManager securityManager;
    private final DocumentManager documentManager;

    public UsersInfoHandler(SessionManager sessionManager, SecurityManager securityManager, DocumentManager documentManager) {
        super();
        this.sessionManager = sessionManager;
        this.securityManager = securityManager;
        this.documentManager = documentManager;
        register(RequestCode.USERS_INFO.toString(), this::UsersInfo);
        register(RequestCode.CURRENT_USER_INFO.toString(), this::CurrentUserInfo);
        register(RequestCode.REGISTRATION_USER_INFO.toString(), this::RegistrationUserInfo);
        register(RequestCode.GET_DOCUMENTS_HISTORY.toString(), this::DocumentsInfo);
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

    private boolean DocumentsInfo(ResponseRecipient responseRecipient, Request request) {
        Session session = sessionManager.getSession(request.sessionID);
        session.lastActivityTime = Clock.systemDefaultZone().instant();

        String[] userIDs = new String[] {session.currentUserID};
        return HandleInfo(responseRecipient, request, session.getId(), userIDs);
    }

    private boolean RegistrationUserInfo(ResponseRecipient responseRecipient, Request requestBase) {
        EntityWithViolationsRequest request = (EntityWithViolationsRequest) requestBase;
        boolean result = false;
        Session session = null;
        String sessionID;

        User registrationUser = (User) request.entity;

        List<String> violations = validate(registrationUser);

        if (violations.isEmpty()) {
            //request.setAttribute("violations", violations);
            result = registrationNewUser(registrationUser, result);
        }else{
            request.setAttribute("violations", violations);
        }


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

        return result;
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

    private boolean DocumentInfo(ResponseRecipient responseRecipient, Request request, String sessionID, String[] userIDs) {
        boolean result;
        List<Document> documents = documentManager.getDocuments(sessionID, userIDs);
        UsersInfo view = new UsersInfo(users);
        result = users.size() != 0;

        ObjectResponse response = new ObjectResponse(request.code, request.sessionID, result, view);

        if (responseRecipient != null) {
            responseRecipient.ReceiveResponse(response);
        }
        return true;
    }

    public List<String> validate(User user) {
        List<String> violations = new ArrayList<>();
        if (!StringValidator.validate(user.getName())){
            violations.add("Имя не задана");
        }
        if (!StringValidator.validate(user.getSurname())) {
            violations.add("Фамилия не задана");
        }
        if (!StringValidator.validate(user.getUsername())) {
            violations.add("Логин не задан");
        }
        if (!StringValidator.validate(user.getPassword())) {
            violations.add("Пароль не задан");
        }
        if (!EmailValidator.validate(user.getEmail())) {
            violations.add("Email задан некорректно");
        }
        return violations;
    }
}
