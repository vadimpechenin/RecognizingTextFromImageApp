package core.interaction.requestExtractors.entityRequestExtractor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import classes.EntityType;
import core.instantGson.InstantGsonDeserializer;
import core.instantGson.InstantGsonSerializer;
import core.interaction.Request;
import core.interaction.RequestExtractor;
import core.interaction.requests.EntityRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class EntityRequestExtractor implements RequestExtractor {
    private final Map<String, EntityExtractor> extractors;

    public EntityRequestExtractor() {
        InstantGsonSerializer instantSerializer = new InstantGsonSerializer(new GsonBuilder().create());
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantGsonDeserializer())
                .registerTypeAdapter(Instant.class, instantSerializer)
                .create();
        extractors = new HashMap<>();
        extractors.put(EntityType.GetDocumentsOfUser.toString(), new GetDocumentsOfUserEntityExtractor(gson));
        extractors.put(EntityType.GetNewUserEntity.toString(), new GetNewUserEntityExtractor(gson));
    }

    @Override
    public Request extract(HttpServletRequest httpServletRequest) {
        String code = httpServletRequest.getParameter("cmd");
        String sessionID = (String) httpServletRequest.getSession().getAttribute("sid");

        String entityType = httpServletRequest.getParameter("type");
        String entityGson = httpServletRequest.getParameter("entity");
        Object entity = null;
        EntityExtractor entityExtractor = extractors.getOrDefault(entityType, null);
        if (entityExtractor != null) {
            entity = entityExtractor.extract(entityGson);
        }

        return new EntityRequest(code, sessionID, entityType, entity);
    }
}
