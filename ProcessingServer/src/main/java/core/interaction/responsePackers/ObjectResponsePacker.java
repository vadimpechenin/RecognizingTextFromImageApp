package core.interaction.responsePackers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import core.ResourceManager;
import core.interaction.HttpServletResponseBuilder;
import core.interaction.Request;
import core.interaction.Response;
import core.interaction.ResponsePacker;
import core.interaction.responses.ObjectResponse;

import java.io.IOException;

public class ObjectResponsePacker implements ResponsePacker {
    private final ResourceManager resourceManager;
    private final Gson gson;
    public ObjectResponsePacker(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.gson = new GsonBuilder().create();
    }
    @Override
    public void pack(Request request, Response responseBase, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        int pageStatus;
        String pageContentType;
        String pageText;

        if(responseBase.result && responseBase instanceof ObjectResponse objectResponse) {
            String json = gson.toJson(objectResponse.entry);

            pageStatus = HttpServletResponse.SC_OK;
            pageContentType = HttpServletResponseBuilder.JSONContentType;
            pageText = json;
        } else {
            pageStatus = HttpServletResponse.SC_BAD_REQUEST;
            pageContentType = HttpServletResponseBuilder.HTMLContentType;
            pageText = resourceManager.getResource("templates/CommandInvalidParameters.html");
        }

        HttpServletResponseBuilder.onStringResponse(httpServletResponse, pageStatus, pageContentType, pageText);
     }
}
