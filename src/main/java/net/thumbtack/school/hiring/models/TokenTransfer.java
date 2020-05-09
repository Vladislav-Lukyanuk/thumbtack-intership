package net.thumbtack.school.hiring.models;

import java.lang.reflect.Type;
import java.util.UUID;

public class TokenTransfer {
    private UUID token;
    private String json;
    private String jsonClass;
    private String error;

    public TokenTransfer(UUID token, String json, String jsonClass, String error) {
        this.token = token;
        this.json = json;
        this.jsonClass = jsonClass;
        this.error = error;
    }

    public UUID getToken() {
        return token;
    }

    public String getJson() {
        return json;
    }

    public String getJsonClass() {
        return jsonClass;
    }

    public String getError() {
        return error;
    }

}
