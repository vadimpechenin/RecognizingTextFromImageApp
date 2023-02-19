package core.interaction.requestExtractors.entityRequestExtractor;

import com.google.gson.Gson;
import classes.GetDocumentsOfUser;

/**
 * Распаковывает строку в объект GetDocumentsOfUser (при запросе на список документов, параметры с клиента)
 */
public class GetDocumentsOfUserEntityExtractor implements EntityExtractor{
    private final Gson gson;

    public GetDocumentsOfUserEntityExtractor(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object extract(String entityGson) {
        return gson.fromJson(entityGson, GetDocumentsOfUser.class);
    }
}
