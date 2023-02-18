package dbclasses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User extends ObjectWithID {
    @Column(name = "Name")
    public String name;

    @Column(name = "Surname")
    public String surname;

    @Column(name = "Emal")
    public String emal;

    @Column(name = "Username")
    public String username;

    @Column(name = "Password")
    public String password;
}