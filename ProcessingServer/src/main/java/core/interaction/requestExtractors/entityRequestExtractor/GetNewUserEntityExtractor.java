package core.interaction.requestExtractors.entityRequestExtractor;

import com.google.gson.Gson;
import dbclasses.User;

/**
 * Распаковывает строку в объект GetNewUser при запросе на регистрацию нового пользователя
 */
public class GetNewUserEntityExtractor implements EntityExtractor{
    private final Gson gson;

    public GetNewUserEntityExtractor(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object extract(String entityGson) {
        return gson.fromJson(entityGson, User.class);
    }
}
