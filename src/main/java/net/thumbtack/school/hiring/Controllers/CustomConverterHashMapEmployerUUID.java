package net.thumbtack.school.hiring.Controllers;

import com.google.gson.*;
import net.thumbtack.school.hiring.models.Employer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;

public class CustomConverterHashMapEmployerUUID implements JsonSerializer<HashMap<Employer, UUID>>, JsonDeserializer<HashMap<Employer, UUID>> {

    @Override
    public JsonElement serialize(HashMap<Employer, UUID> employerUUIDHashMap, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        for(Employer employer: employerUUIDHashMap.keySet()) {
            JsonObject jsonObject = new JsonObject();
            JsonElement employerObj = new JsonParser().parse(new Gson().toJson(employer));
            JsonElement uuid = new JsonParser().parse(new Gson().toJson(employerUUIDHashMap.get(employer)));

            jsonObject.add("employer", employerObj);
            jsonObject.add("uuid", uuid);

            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    @Override
    public HashMap<Employer, UUID> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        HashMap<Employer, UUID> employerUUIDHashMap = new HashMap<>();
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for(int index = 0; index < jsonArray.size(); index++)
        {
            JsonObject jsonObject = jsonArray.get(index).getAsJsonObject();

            Employer employer = new Gson().fromJson(jsonObject.getAsJsonObject("employer"), Employer.class);
            UUID uuid = new Gson().fromJson(jsonObject.getAsJsonPrimitive("uuid"), UUID.class);
            employerUUIDHashMap.put(employer, uuid);
        }

        return employerUUIDHashMap;
    }
}
