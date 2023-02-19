package core.interaction.requests;

import core.interaction.Request;

public class EditContentRequest extends Request {
    public final String contentID;
    public final String stringValue;
    public final Double doubleValue;

    private EditContentRequest(String code, String sessionID, String contentID, String stringValue, Double doubleValue) {
        super(code, sessionID);
        this.contentID = contentID;
        this.stringValue = stringValue;
        this.doubleValue = doubleValue;
    }

    public static Builder newBuilder(String code, String sessionID, String contentID) {
        return new Builder(code, sessionID, contentID);
    }
    public static class Builder {
        private final String code;
        private final String sessionID;
        private final String contentID;
        private String stringValue;
        private Double doubleValue;
        private Builder(String code, String sessionID, String contentID) {
            this.code = code;
            this.sessionID = sessionID;
            this.contentID = contentID;
        }
        public Builder setStringValue(String stringValue) {
            this.stringValue = stringValue;
            return this;
        }
        public Builder setDoubleValue(Double doubleValue) {
            this.doubleValue = doubleValue;
            return this;
        }
        public EditContentRequest build() {
            return new EditContentRequest(code, sessionID, contentID, stringValue, doubleValue);
        }
    }
}
