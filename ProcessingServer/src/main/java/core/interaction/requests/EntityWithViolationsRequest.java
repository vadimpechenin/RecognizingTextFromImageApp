package core.interaction.requests;

import core.interaction.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityWithViolationsRequest extends EntityRequest {

    public Map<String, List<String>> violations;

    public EntityWithViolationsRequest(String code, String sessionID, String entityType, Object entity) {
        super(code, sessionID, entityType, entity);
    }

    public void setAttribute(String violations, List<String> violationsList) {
        this.violations = new HashMap<>();
        this.violations.put(violations, violationsList);
    }
}
