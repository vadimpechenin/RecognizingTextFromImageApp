package core.recognitionClient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import core.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class ConverterUtils {
    public static void attachListOfBytes(Gson gson, JsonObject jsonObject, String fieldName, List<byte[]> fieldValues) {
        List<String> fieldValuesList = new ArrayList<>();
        for(byte[] fieldValue : fieldValues) {
            String str = CommonUtils.encodeHexString(fieldValue);
            fieldValuesList.add(str);
        }
        JsonArray fieldValuesArray = gson.toJsonTree(fieldValuesList).getAsJsonArray();
        jsonObject.add(fieldName, fieldValuesArray);
    }
}
