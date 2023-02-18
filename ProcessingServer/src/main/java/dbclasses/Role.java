package dbclasses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Roles")
public class Role extends ObjectWithID {
    @Column(name = "Role")
    public String role;
}