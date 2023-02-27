package classes;

import dbclasses.Document;

import java.util.ArrayList;
import java.util.List;

public class DocumentsInfoWithoutFiles {
    public final List<Document> documents;

    public DocumentsInfoWithoutFiles(List<Document> documents) {
        this.documents = new ArrayList<>();
        for (Document document: documents){
            Document doc= new Document();
            doc.setId(document.getId());
            doc.setUserID(document.getUserID());
            doc.setTitle(document.getTitle());
            doc.setFilepdf(null);
            doc.setFiletext(null);
            this.documents.add(doc);
        }
    }

}
