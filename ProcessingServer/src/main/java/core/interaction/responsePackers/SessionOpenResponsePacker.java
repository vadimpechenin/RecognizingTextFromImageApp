package core.interaction.responsePackers;

import core.interaction.requests.EntityRequest;
import core.interaction.requests.EntityWithViolationsRequest;
import dbclasses.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import core.ResourceManager;
import core.interaction.HttpServletResponseBuilder;
import core.interaction.Request;
import core.interaction.Response;
import core.interaction.ResponsePacker;
import core.interaction.requests.EditContentRequest;

import java.io.IOException;
import java.util.List;

public class SessionOpenResponsePacker implements ResponsePacker {
    private final ResourceManager resourceManager;
    public SessionOpenResponsePacker(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }
    @Override
    public void pack(Request request, Response response, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        int status;
        String pageText;

        if(response.result) {
            httpServletRequest.getSession().invalidate();
            HttpSession session = httpServletRequest.getSession();
            session.setMaxInactiveInterval(-1); // до закрытия браузера
            session.setAttribute("sid", response.sessionID);

            status = HttpServletResponse.SC_OK;
            pageText = resourceManager.getResource("templates/CommandLoginFound.html");
            if (request instanceof EditContentRequest) {
                pageText = pageText.replace("%USERNAME%", ((EditContentRequest)request).contentID);
            }else {
                EntityRequest entityRequest = ((EntityRequest)request);
                User newUser = (User) entityRequest.entity;
                pageText = pageText.replace("%USERNAME%", newUser.getUsername());
            }
        } else {
            status = HttpServletResponse.SC_NOT_FOUND;
            EntityWithViolationsRequest entityWithViolationsRequest = ((EntityWithViolationsRequest)request);
            if (entityWithViolationsRequest.violations==null){

                pageText = resourceManager.getResource("templates/CommandLoginNotFound.html");
            }else{
                List<String> violations = entityWithViolationsRequest.violations.get("violations");
                StringBuilder violationsAll = new StringBuilder();
                for (String violation:violations){
                    violationsAll.append(violation).append("; ");
                }
                pageText = resourceManager.getResource("templates/RegistrationError.html");
                pageText = pageText.replace("%USERNAME%", violationsAll);
            }

        }

        HttpServletResponseBuilder.onStringResponse(httpServletResponse, status, HttpServletResponseBuilder.HTMLContentType, pageText);
     }
}
