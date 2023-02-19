package core.interaction.responsePackers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import core.ResourceManager;
import core.interaction.HttpServletResponseBuilder;
import core.interaction.Request;
import core.interaction.Response;
import core.interaction.ResponsePacker;

import java.io.IOException;

public class SessionCloseResponsePacker implements ResponsePacker {
    private final ResourceManager resourceManager;
    public SessionCloseResponsePacker(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }
    @Override
    public void pack(Request request, Response response, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        int status;
        String pageText;

        if(response.result) {
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute("sid", null);
            session.invalidate();

            status = HttpServletResponse.SC_OK;
            pageText = resourceManager.getResource("templates/CommandSuccess.html");
        } else {
            status = HttpServletResponse.SC_BAD_REQUEST;
            pageText = resourceManager.getResource("templates/CommandInvalidParameters.html");
        }

        HttpServletResponseBuilder.onStringResponse(httpServletResponse, status, HttpServletResponseBuilder.HTMLContentType, pageText);
     }
}
