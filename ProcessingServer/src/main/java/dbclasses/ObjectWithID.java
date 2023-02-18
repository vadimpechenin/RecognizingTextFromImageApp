package dbclasses;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
public abstract class ObjectWithID implements Serializable {
    @Id
    @Column(name = "ID")
    public String id;
}
