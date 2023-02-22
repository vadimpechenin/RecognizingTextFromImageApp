package core.interaction.requests;


import core.interaction.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityRequest extends Request {
    public final String entityType;
    public final Object entity;

    public EntityRequest(String code, String sessionID, String entityType, Object entity) {
        super(code, sessionID);
        this.entityType = entityType;
        this.entity = entity;

    }
}
