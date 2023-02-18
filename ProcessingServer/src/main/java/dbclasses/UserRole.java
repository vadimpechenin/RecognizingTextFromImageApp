package dbclasses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserRoles")
public class UserRole extends ObjectWithID {
    @Column(name = "userid")
    public String userID;

    @Column(name = "roleid")
    public String roleID;
}