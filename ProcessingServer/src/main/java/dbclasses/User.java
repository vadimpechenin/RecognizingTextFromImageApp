package dbclasses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User extends ObjectWithID {
    @Column(name = "name")
    public String name;

    @Column(name = "surname")
    public String surname;

    @Column(name = "email")
    public String email;

    @Column(name = "username")
    public String username;

    @Column(name = "password")
    public String password;
}