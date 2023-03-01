package handlers;

import classes.*;
import core.SessionManager;
import core.documentManager.DocumentManager;
import core.interaction.Request;
import core.interaction.RequestHandlerContainer;
import core.interaction.Response;
import core.interaction.ResponseRecipient;
import core.interaction.requests.EditContentRequest;
import core.interaction.requests.RequestWithAttachments;
import core.interaction.responses.ObjectResponse;
import core.recognitionClient.RecognizeTextClient;
import dbclasses.Document;

import java.time.Clock;
import java.util.List;

public class DocumentsHandler extends RequestHandlerContainer {
    private final SessionManager sessionManager;
    private final DocumentManager documentManager;

    public DocumentsHandler(SessionManager sessionManager, DocumentManager documentManager) {
        super();
        this.sessionManager = sessionManager;
        this.documentManager = documentManager;
        register(RequestCode.RECOGNIZE_DOCUMENT.toString(), this::DocumentRecognize);
    }

    private boolean DocumentRecognize(ResponseRecipient responseRecipient, Request requestBase) {
        RequestWithAttachments request = (RequestWithAttachments) requestBase;
        boolean result;

        Session session = sessionManager.getSession(request.sessionID);
        session.lastActivityTime = Clock.systemDefaultZone().instant();

        RecognizeTextClient recognizeTextClient = new RecognizeTextClient();
        RecognitionDocument calculateResult = new RecognitionDocument();

        result = recognizeTextClient.recognitionText(request.attachments, calculateResult);
        Response response = new Response(request.code, request.sessionID, result);

        if (responseRecipient != null) {
            responseRecipient.ReceiveResponse(response);
        }
        return true;
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
