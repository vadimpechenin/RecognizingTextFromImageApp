package core.documentManager;

import dbclasses.Document;


import java.util.ArrayList;
import java.util.List;


/**
 * Класс для хранения списка документов
 */
public class DocumentManagerData {
    public final List<Document> documents;

    public DocumentManagerData() {
        documents = new ArrayList<>();
    }

    public void clear() {
        documents.clear();
    }
}
