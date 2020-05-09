package net.thumbtack.school.hiring.Controllers;

import com.google.gson.*;
import net.thumbtack.school.hiring.models.Vacancy;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;

public class CustomConverterHashMapVacancyUUID implements JsonSerializer<HashMap<Vacancy, UUID>>, JsonDeserializer<HashMap<Vacancy, UUID>> {
    @Override
    public JsonElement serialize(HashMap<Vacancy, UUID> vacancyUUIDHashMap, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        for(Vacancy vacancy: vacancyUUIDHashMap.keySet()) {
            JsonObject jsonObject = new JsonObject();
            JsonElement vacancyObj = new JsonParser().parse(new Gson().toJson(vacancy));
            JsonElement uuid = new JsonParser().parse(new Gson().toJson(vacancyUUIDHashMap.get(vacancy)));

            jsonObject.add("vacancy", vacancyObj);
            jsonObject.add("uuid", uuid);

            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    @Override
    public HashMap<Vacancy, UUID> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        HashMap<Vacancy, UUID> vacancyUUIDHashMap = new HashMap<>();
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for(int index = 0; index < jsonArray.size(); index++)
        {
            JsonObject jsonObject = jsonArray.get(index).getAsJsonObject();

            Vacancy vacancy = new Gson().fromJson(jsonObject.getAsJsonObject("vacancy"), Vacancy.class);
            UUID uuid = new Gson().fromJson(jsonObject.getAsJsonPrimitive("uuid"), UUID.class);
            vacancyUUIDHashMap.put(vacancy, uuid);
        }

        return vacancyUUIDHashMap;
    }
}
