package core.documentManager;

import classes.Session;
import core.SessionManager;
import db.DocumentManagerService;
import dbclasses.Document;
import org.apache.commons.lang3.SerializationUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Менеджер для работы с сущностью Document
 */
public class DocumentManager {
    private final DocumentManagerService service;
    private final SessionManager sessionManager;
    private final DocumentManagerData data;

    public DocumentManager(org.hibernate.SessionFactory hibernateSessionFactory, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.service = new DocumentManagerService(hibernateSessionFactory);
        this.data = new DocumentManagerData();
    }

    public boolean init() {
        boolean result = true;

        service.getData(data);
        if (data==null){
            result = false;
        }
        return result;
    }


    public List<Document> getDocuments(String currentSessionID, String[] ids) {
        List<Document> result = new ArrayList<>();
        Session session = sessionManager.getSession(currentSessionID);
        List<String> userIDs = new ArrayList<>();
        if(ids == null || ids.length == 0) {
            userIDs.add(session.currentUserID);
        } else {
            Collections.addAll(userIDs, ids);
        }
        if(session != null) {
            for(String userID : userIDs) {
                if(Objects.equals(userID, session.currentUserID)) {
                    service.getDataByUserID(data,userID);
                    result.addAll(data.documents);
                }
            }
        }
        return result;
    }

    public List<Document> getDocumentByID(String currentSessionID, String[] ids, String id) {
        List<Document> result = new ArrayList<>();
        Session session = sessionManager.getSession(currentSessionID);
        List<String> userIDs = new ArrayList<>();
        if(ids == null || ids.length == 0) {
            userIDs.add(session.currentUserID);
        } else {
            Collections.addAll(userIDs, ids);
        }
        if(session != null) {
            for(String userID : userIDs) {
                if(Objects.equals(userID, session.currentUserID)) {
                    service.getDataByID(data,id);
                    result.addAll(data.documents);
                }
            }
        }
        return result;
    }
}
