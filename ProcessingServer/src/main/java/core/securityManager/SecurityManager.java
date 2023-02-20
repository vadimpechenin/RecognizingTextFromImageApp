package core.securityManager;

import classes.Session;
import core.CommonUtils;
import core.SessionManager;
import db.SecurityManagerService;
import dbclasses.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Менеджер для работы с сущностями User, Role, UserRole
 */
public class SecurityManager {
    private final SecurityManagerService service;
    private final SessionManager sessionManager;
    private final SecurityManagerData data;
    private final Map<String, UserInfo> userInfoMap;
    private String adminRoleID;

    public SecurityManager(org.hibernate.SessionFactory hibernateSessionFactory, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.service = new SecurityManagerService(hibernateSessionFactory);
        this.data = new SecurityManagerData();
        this.userInfoMap = new HashMap<>();
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

    public boolean isAdminUser(String userID) {
        UserInfo info = userInfoMap.getOrDefault(userID, null);
        return info != null && info.isAdmin;
    }

    public String logOn(String login, String password) {
        String finalLogin = login.trim().toLowerCase();
        String finalPassword = password.trim();
        return data.users.stream().filter(x -> x.getUsername().toLowerCase().equals(finalLogin) && Objects.equals(x.getPassword(), finalPassword)).map(ObjectWithID::getId).findFirst().orElse(null);
    }

    public List<User> getUsers(String currentSessionID, String[] ids) {
        List<User> result = new ArrayList<>();
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
