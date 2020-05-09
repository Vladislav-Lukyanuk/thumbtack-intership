package net.thumbtack.school.hiring.Controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.thumbtack.school.hiring.models.Worker;

import java.lang.reflect.Type;
import java.util.HashMap;

public class CustomConverterHashMapWorkerHashMapStringInteger implements JsonSerializer<HashMap<Worker, HashMap<String, Integer>>>,
        JsonDeserializer<HashMap<Worker, HashMap<String, Integer>>> {

    @Override
    public JsonElement serialize(HashMap<Worker, HashMap<String, Integer>> workerHashMapHashMap, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        for (Worker worker : workerHashMapHashMap.keySet()) {
            JsonObject jsonObject = new JsonObject();
            JsonElement workerObj = new JsonParser().parse(new Gson().toJson(worker));

            JsonElement hashMap = new JsonParser().parse(new Gson().toJson(workerHashMapHashMap.get(worker)));

            jsonObject.add("worker", workerObj);
            jsonObject.add("hashMap", hashMap);

            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public HashMap<Worker, HashMap<String, Integer>> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        HashMap<Worker, HashMap<String, Integer>> workerHashMapHashMap = new HashMap<>();
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (int index = 0; index < jsonArray.size(); index++) {
            JsonObject jsonObject = jsonArray.get(index).getAsJsonObject();

            Worker worker = new Gson().fromJson(jsonObject.getAsJsonObject("worker"), Worker.class);

            HashMap<String, Integer> hashMap = new Gson().fromJson(jsonObject.getAsJsonObject("hashMap"),
                    new TypeToken<HashMap<String, Integer>>() {
                    }.getType());

            workerHashMapHashMap.put(worker, hashMap);
        }

        return workerHashMapHashMap;
    }
}
