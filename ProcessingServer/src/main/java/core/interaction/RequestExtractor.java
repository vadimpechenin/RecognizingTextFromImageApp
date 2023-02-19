package core.interaction;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface RequestExtractor {
     Request extract(HttpServletRequest request) throws ServletException, IOException;
}
