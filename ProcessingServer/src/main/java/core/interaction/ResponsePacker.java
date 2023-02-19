package core.interaction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ResponsePacker {
    void pack(Request request, Response response, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException;
}
