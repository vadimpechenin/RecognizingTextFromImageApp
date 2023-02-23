package dbclasses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Documents")
public class Document extends ObjectWithID {

    @Column(name = "userid")
    private String userID;

    @Column(name = "title")
    private String title;

    @Column(name = "filepdf")
    private byte[] filepdf;

    @Column(name = "filetext")
    private byte[] filetext;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getFilepdf() {
        return filepdf;
    }

    public void setFilepdf(byte[] filepdf) {
        this.filepdf = filepdf;
    }

    public byte[] getFiletext() {
        return filetext;
    }

    public void setFiletext(byte[] filetxt) {
        this.filetext = filetxt;
    }
}
