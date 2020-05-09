package net.thumbtack.school.hiring.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Objects;

public class Skill {
    private String name;
    private int level;

    public Skill(String name, int level) {
        setName(name);
        setLevel(level);
    }

    private void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(name, skill.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
