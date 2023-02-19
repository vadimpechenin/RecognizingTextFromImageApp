package core.interaction.responsePackers;

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
            pageText = pageText.replace("%USERNAME%", ((EditContentRequest)request).contentID);
        } else {
            status = HttpServletResponse.SC_NOT_FOUND;
            pageText = resourceManager.getResource("templates/CommandLoginNotFound.html");
        }

        HttpServletResponseBuilder.onStringResponse(httpServletResponse, status, HttpServletResponseBuilder.HTMLContentType, pageText);
     }
}
