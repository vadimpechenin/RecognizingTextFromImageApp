package handlers;

import classes.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import core.SessionManager;
import core.documentManager.DocumentManager;
import core.interaction.Request;
import core.interaction.RequestHandlerContainer;
import core.interaction.Response;
import core.interaction.ResponseRecipient;
import core.interaction.requests.EditContentRequest;
import core.interaction.requests.RequestWithAttachments;
import core.interaction.responses.ObjectResponse;
import core.recognitionClient.ConverterUtils;
import core.recognitionClient.RecognizeTextClient;
import dbclasses.Document;
import dbclasses.User;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class DocumentsHandler extends RequestHandlerContainer {
    private final SessionManager sessionManager;
    private final DocumentManager documentManager;

    public DocumentsHandler(SessionManager sessionManager, DocumentManager documentManager) {
        super();
        this.sessionManager = sessionManager;
        this.documentManager = documentManager;
        register(RequestCode.RECOGNIZE_DOCUMENT.toString(), this::DocumentRecognize);
        register(RequestCode.RECOGNIZE_AUDIO_DOCUMENT.toString(), this::AudioDocumentRecognize);
        register(RequestCode.SAVE_DOCUMENT.toString(), this::DocumentSave);
    }

    private boolean DocumentRecognize(ResponseRecipient responseRecipient, Request requestBase) {
        RequestWithAttachments request = (RequestWithAttachments) requestBase;
        boolean result;

        Session session = sessionManager.getSession(request.sessionID);
        session.lastActivityTime = Clock.systemDefaultZone().instant();

        RecognizeTextClient recognizeTextClient = new RecognizeTextClient();
        RecognitionDocument calculateResult = new RecognitionDocument();

        result = recognizeTextClient.recognitionText(request.attachments, calculateResult);
        Response response = new ObjectResponse(request.code, request.sessionID, result, calculateResult);

        if (responseRecipient != null) {
            responseRecipient.ReceiveResponse(response);
        }
        return result;
    }

    private boolean AudioDocumentRecognize(ResponseRecipient responseRecipient, Request requestBase) {
        RequestWithAttachments request = (RequestWithAttachments) requestBase;
        boolean result;

        Session session = sessionManager.getSession(request.sessionID);
        session.lastActivityTime = Clock.systemDefaultZone().instant();

        RecognizeTextClient recognizeTextClient = new RecognizeTextClient();
        RecognitionDocument calculateResult = new RecognitionDocument();

        result = recognizeTextClient.recognitionText(request.attachments, calculateResult);
        Response response = new ObjectResponse(request.code, request.sessionID, result, calculateResult);

        if (responseRecipient != null) {
            responseRecipient.ReceiveResponse(response);
        }
        return result;
    }

    private boolean DocumentSave(ResponseRecipient responseRecipient, Request requestBase) {
        RequestWithAttachments request = (RequestWithAttachments) requestBase;
        boolean result = false;

        Session session = sessionManager.getSession(request.sessionID);
        session.lastActivityTime = Clock.systemDefaultZone().instant();

        byte[] filepdf = request.attachments.get(0);
        byte[] filetext = request.attachments.get(1);
        String titleOfDocument = new String(request.attachments.get(2), StandardCharsets.UTF_8);

        String[] userIDs = new String[] {session.currentUserID};

        Document document = new Document();
        if(session != null) {
            for (String userID : userIDs) {
                if (Objects.equals(userID, session.currentUserID)) {
                    document.setUserID(userID);
                    document.setTitle(titleOfDocument);
                    document.setFilepdf(filepdf);
                    document.setFiletext(filetext);
                    result = saveDocument(document, result);
                }
            }
        }
        Response response = new Response(request.code, request.sessionID, result);

        if (responseRecipient != null) {
            responseRecipient.ReceiveResponse(response);
        }

        return result;
    }

    private boolean saveDocument(Document entity, boolean result) {
        if (entity!=null){
            try{
                documentManager.save(entity);
                result = true;
            }
            catch(Exception e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }

    private boolean DocumentInfoID(ResponseRecipient responseRecipient, Request request, String sessionID, String[] userIDs, String ID) {
        boolean result;
        List<Document>  documents = documentManager.getDocumentByID(sessionID, userIDs, ID);
        DocumentsInfo view = new DocumentsInfo(documents);
        result = documents.size() != 0;

        ObjectResponse response = new ObjectResponse(request.code, request.sessionID, result, view);

        if (responseRecipient != null) {
            responseRecipient.ReceiveResponse(response);
        }
        return true;
    }
}
