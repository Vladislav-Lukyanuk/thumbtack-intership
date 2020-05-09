package net.thumbtack.school.hiring.Controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.thumbtack.school.hiring.models.Skill;
import net.thumbtack.school.hiring.models.Vacancy;

import java.lang.reflect.Type;
import java.util.HashMap;

public class CustomConverterHashMapVacancyHashMapSkillBoolean implements JsonSerializer<HashMap<Vacancy, HashMap<Skill, Boolean>>>, JsonDeserializer<HashMap<Vacancy, HashMap<Skill, Boolean>>> {

    private static Gson gson = registerTypes();

    private static Gson registerTypes() {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(new TypeToken<HashMap<Skill, Boolean>>() {
        }.getType(), new CustomConverterHashMapSkillBoolean());

        return builder.create();
    }


    @Override
    public JsonElement serialize(HashMap<Vacancy, HashMap<Skill, Boolean>> vacancyHashMapHashMap, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        for (Vacancy vacancy : vacancyHashMapHashMap.keySet()) {
            JsonObject jsonObject = new JsonObject();
            JsonElement vacancyObj = new JsonParser().parse(gson.toJson(vacancy));

            JsonElement hashMap = new JsonParser().parse(gson.toJson(vacancyHashMapHashMap.get(vacancy),
                    new TypeToken<HashMap<Skill, Boolean>>() {
                    }.getType()));

            jsonObject.add("vacancy", vacancyObj);
            jsonObject.add("hashMap", hashMap);

            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    @Override
    public HashMap<Vacancy, HashMap<Skill, Boolean>> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        HashMap<Vacancy, HashMap<Skill, Boolean>> vacancyHashMapHashMap = new HashMap<>();
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (int index = 0; index < jsonArray.size(); index++) {
            JsonObject jsonObject = jsonArray.get(index).getAsJsonObject();

            Vacancy vacancy = gson.fromJson(jsonObject.getAsJsonObject("vacancy"), Vacancy.class);

            HashMap<Skill, Boolean> hashMap = gson.fromJson(jsonObject.getAsJsonArray("hashMap"),
                    new TypeToken<HashMap<Skill, Boolean>>() {
                    }.getType());
            vacancyHashMapHashMap.put(vacancy, hashMap);
        }

        return vacancyHashMapHashMap;
    }
}
