package dbclasses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Documents")
public class Document extends ObjectWithID {

    @Column(name = "userid")
    public String userID;

    @Column(name = "title")
    public String title;

    @Column(name = "filepdf")
    public byte[] filepdf;

    @Column(name = "filetxt")
    public byte[] filetxt;
}
