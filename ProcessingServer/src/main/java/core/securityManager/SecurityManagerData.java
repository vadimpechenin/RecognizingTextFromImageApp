package core.securityManager;

import dbclasses.*;

import java.util.ArrayList;
import java.util.List;

public class SecurityManagerData {
    public final List<User> users;
    public final List<UserRole> userRoles;
    public final List<Role> roles;

    public SecurityManagerData() {
        users = new ArrayList<>();
        userRoles = new ArrayList<>();
        roles = new ArrayList<>();
    }

    public void clear() {
        users.clear();
        userRoles.clear();
        roles.clear();
    }
}
