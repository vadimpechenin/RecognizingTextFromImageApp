package core.interaction.responsePackers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import core.interaction.HttpServletResponseBuilder;
import core.interaction.Request;
import core.interaction.Response;
import core.interaction.ResponsePacker;

import java.io.IOException;

public class BaseResponsePacker implements ResponsePacker {
    public BaseResponsePacker() {
    }

    @Override
    public void pack(Request request, Response responseBase, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        int pageStatus = responseBase.result ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST;
        String pageContentType = HttpServletResponseBuilder.HTMLContentType;
        String pageText = "";
        HttpServletResponseBuilder.onStringResponse(httpServletResponse, pageStatus, pageContentType, pageText);
    }
}
