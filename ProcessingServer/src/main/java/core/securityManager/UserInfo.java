package core.securityManager;

import java.util.Set;

public class UserInfo {
    public final String userID;
    public final boolean isAdmin;

    public UserInfo(String userID, boolean isAdmin) {
        this.userID = userID;
        this.isAdmin = isAdmin;
    }
}
