package core.documentManager;

import classes.Session;
import core.CommonUtils;
import core.SessionManager;
import core.securityManager.SecurityManagerConstants;
import core.securityManager.SecurityManagerData;
import core.securityManager.UserInfo;
import db.SecurityManagerService;
import dbclasses.Document;
import dbclasses.ObjectWithID;
import dbclasses.User;
import dbclasses.UserRole;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Менеджер для работы с сущностью Document
 */
public class DocumentManager {
    private final SecurityManagerService service;
    private final SessionManager sessionManager;
    private final SecurityManagerData data;

    public DocumentManager(org.hibernate.SessionFactory hibernateSessionFactory, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.service = new SecurityManagerService(hibernateSessionFactory);
        this.data = new SecurityManagerData();
    }

    public boolean init() {
        boolean result;

        service.getData(data);
        result = initFields();
        result = result && initInfos();
        return result;
    }

    private boolean initFields() {
        adminRoleID = data.roles.stream().filter(x -> Objects.equals(x.getRole(), SecurityManagerConstants.AdminRoleName)).map(x -> x.getId()).findFirst().orElse(null);
        return !CommonUtils.isNullOrEmpty(adminRoleID);
    }

    private boolean initInfos() {
        userInfoMap.clear();

        for(User user : data.users) {
            boolean isAdmin = data.userRoles.stream().anyMatch(x -> Objects.equals(x.getUserID(), user.getId()) && Objects.equals(x.getRoleID(), adminRoleID));
            UserInfo info = new UserInfo(user.getId(), isAdmin);
            userInfoMap.put(user.getId(), info);
        }
        return true;
    }

    public List<Document> getDocuments(String currentSessionID, String[] ids) {
        List<Document> result = new ArrayList<>();
        Session session = sessionManager.getSession(currentSessionID);
        List<String> userIDs = new ArrayList<>();
        if(ids == null || ids.length == 0) {
            if(isAdminUser(session.currentUserID)) {
                userIDs = data.users.stream().map(x->x.getId()).collect(Collectors.toList());
            } else {
                userIDs.add(session.currentUserID);
            }
        } else {
            Collections.addAll(userIDs, ids);
        }
        if(session != null) {
            for(String userID : userIDs) {
                if(isAdminUser(session.currentUserID) || Objects.equals(userID, session.currentUserID)) {
                    User user = data.users.stream().filter(x-> Objects.equals(x.getId(), userID)).findFirst().orElse(null);
                    if(user != null) {
                        result.add(user);
                    }
                }
            }
        }

        return result;
    }
}
