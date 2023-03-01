package core.recognitionClient.handlers;

import classes.RecognitionDocument;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import core.CommonUtils;
import core.recognitionClient.ConverterUtils;

import java.util.List;

public class TextRecognitionHandler {
    private static class ResponseData{
        public boolean result;
        public List<RecognitionDocument> items;
    }

    private final Gson gson;
    private ResponseData responseData;
    public TextRecognitionHandler() {
        this.gson = new GsonBuilder().create();
    }

    public String getRequestParameters(List<byte[]> inputDocument) {
        JsonObject jsonObject = new JsonObject();
        ConverterUtils.attachListOfBytes(gson, jsonObject, "InputDocument", inputDocument);
        return jsonObject.toString();
    }

    public boolean parseResponse(String response) {
        if(CommonUtils.isNullOrEmpty(response)) return false;

        responseData = gson.fromJson(response, ResponseData.class);
        return responseData != null;
    }

    public boolean getResult() {
        return responseData.result;
    }

    public List<RecognitionDocument> getInfos() {
        return responseData.items;
    }
}
