package net.thumbtack.school.hiring.Controllers;

import com.google.gson.*;
import net.thumbtack.school.hiring.models.Worker;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;

public class CustomConverterHashMapWorkerUUID implements JsonSerializer<HashMap<Worker, UUID>>, JsonDeserializer<HashMap<Worker, UUID>> {
    @Override
    public JsonElement serialize(HashMap<Worker, UUID> workerUUIDHashMap, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        for(Worker worker: workerUUIDHashMap.keySet()) {
            JsonObject jsonObject = new JsonObject();
            JsonElement workerObj = new JsonParser().parse(new Gson().toJson(worker));
            JsonElement uuid = new JsonParser().parse(new Gson().toJson(workerUUIDHashMap.get(worker)));

            jsonObject.add("worker", workerObj);
            jsonObject.add("uuid", uuid);

            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    @Override
    public HashMap<Worker, UUID> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        HashMap<Worker, UUID> workerUUIDHashMap = new HashMap<>();
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for(int index = 0; index < jsonArray.size(); index++)
        {
            JsonObject jsonObject = jsonArray.get(index).getAsJsonObject();

            Worker worker = new Gson().fromJson(jsonObject.getAsJsonObject("worker"), Worker.class);
            UUID uuid = new Gson().fromJson(jsonObject.getAsJsonPrimitive("uuid"), UUID.class);
            workerUUIDHashMap.put(worker, uuid);
        }

        return workerUUIDHashMap;
    }
}
