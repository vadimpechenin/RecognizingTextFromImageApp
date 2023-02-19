package core.interaction.requestExtractors;

import jakarta.servlet.http.HttpServletRequest;
import core.interaction.Request;
import core.interaction.RequestExtractor;

public class BaseRequestExtractor implements RequestExtractor {
    @Override
    public Request extract(HttpServletRequest httpServletRequest) {
        String code = httpServletRequest.getParameter("cmd");
        String sessionID = (String) httpServletRequest.getSession().getAttribute("sid");

        return new Request(code, sessionID);
    }
}
