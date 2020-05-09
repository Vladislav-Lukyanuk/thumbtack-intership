package net.thumbtack.school.hiring.Controllers;

import com.google.gson.*;
import net.thumbtack.school.hiring.models.Skill;

import java.lang.reflect.Type;
import java.util.HashMap;

public class CustomConverterHashMapSkillBoolean implements JsonSerializer<HashMap<Skill, Boolean>>, JsonDeserializer<HashMap<Skill, Boolean>> {
    @Override
    public JsonElement serialize(HashMap<Skill, Boolean> skillBooleanHashMap, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();
        for(Skill skill: skillBooleanHashMap.keySet()) {
            JsonObject jsonObject = new JsonObject();
            JsonObject skillObj = new JsonObject();
            skillObj.addProperty("skillName", skill.getName());
            skillObj.addProperty("level", skill.getLevel());
            jsonObject.add("skill", skillObj);
            jsonObject.addProperty("necessary", skillBooleanHashMap.get(skill));

            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public HashMap<Skill, Boolean> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        HashMap<Skill, Boolean> skillBooleanHashMap = new HashMap<>();
        for(int index = 0; index < jsonArray.size(); index++) {
            JsonObject jsonObject = jsonArray.get(index).getAsJsonObject();
            JsonObject skillObj = jsonObject.getAsJsonObject("skill");

            Skill skill = new Skill(skillObj.getAsJsonPrimitive("skillName").getAsString(), skillObj.getAsJsonPrimitive("level").getAsInt());
            Boolean necassary = jsonObject.getAsJsonPrimitive("necessary").getAsBoolean();

            skillBooleanHashMap.put(skill,necassary);
        }

        return skillBooleanHashMap;
    }
}
