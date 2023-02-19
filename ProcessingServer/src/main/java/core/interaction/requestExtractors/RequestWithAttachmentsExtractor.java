package core.interaction.requestExtractors;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import core.CommonUtils;
import core.interaction.Request;
import core.interaction.RequestExtractor;
import core.interaction.requests.RequestWithAttachments;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RequestWithAttachmentsExtractor implements RequestExtractor {
    @Override
    public Request extract(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        String code = httpServletRequest.getParameter("cmd");
        String sessionID = (String) httpServletRequest.getSession().getAttribute("sid");

        int attachmentCount = 0;
        String tmp = httpServletRequest.getParameter("fileCount");
        if (!CommonUtils.isNullOrEmpty(tmp)) {
            attachmentCount = Integer.parseInt(tmp);
        }

        List<byte[]> attachments = new ArrayList<>();
        for (int attachmentIndex = 0; attachmentIndex < attachmentCount; ++attachmentIndex) {
            String key = String.format("file%d", attachmentIndex + 1);
            Part attachment = httpServletRequest.getPart(key);
            InputStream stream = attachment.getInputStream();
            byte[] attachmentData = stream.readAllBytes();
            attachments.add(attachmentData);
        }

        return new RequestWithAttachments(code, sessionID, attachments);
    }
}
