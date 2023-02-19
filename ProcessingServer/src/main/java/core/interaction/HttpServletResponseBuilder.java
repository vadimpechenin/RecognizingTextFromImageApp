package core.interaction;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpServletResponseBuilder {
    public static final String HTMLContentType = "text/html";
    public static final String JSONContentType = "text/html";

    public static void onDataResponse(HttpServletResponse response, int status, String contentType, byte[] content) throws IOException {
        response.setStatus(status);
        response.setContentType(contentType);
        response.setContentLength(content.length);
        OutputStream outStream = response.getOutputStream();
        outStream.write(content);
        outStream.flush();
        outStream.close();
    }

    public static void onStringResponse(HttpServletResponse response, int status, String contentType, String content) throws IOException {
        response.setCharacterEncoding("UTF-8");
        byte[] contentAsBytes = content.getBytes(StandardCharsets.UTF_8);
        onDataResponse(response, status, contentType, contentAsBytes);
    }
}
