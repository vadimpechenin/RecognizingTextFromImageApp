package core.interaction.requestExtractors;

import jakarta.servlet.http.HttpServletRequest;
import core.CommonUtils;
import core.interaction.Request;
import core.interaction.RequestExtractor;
import core.interaction.requests.EditContentRequest;

public class EditContentRequestExtractor implements RequestExtractor {
    @Override
    public Request extract(HttpServletRequest httpServletRequest) {
        String code = httpServletRequest.getParameter("cmd");
        String sessionID = (String) httpServletRequest.getSession().getAttribute("sid");
        String contentID = httpServletRequest.getParameter("id");
        EditContentRequest.Builder requestBuilder = EditContentRequest.newBuilder(code, sessionID, contentID);

        String tmp = httpServletRequest.getParameter("string");
        if(!CommonUtils.isNullOrEmpty(tmp)) {
            requestBuilder.setStringValue(tmp);
        }

        tmp = httpServletRequest.getParameter("double");
        if(!CommonUtils.isNullOrEmpty(tmp)) {
            requestBuilder.setDoubleValue(Double.parseDouble(tmp.replace(',', '.')));
        }

        return requestBuilder.build();
    }
}
