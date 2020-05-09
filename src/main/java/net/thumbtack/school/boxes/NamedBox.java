package net.thumbtack.school.boxes;

import net.thumbtack.school.figures.v3.Figure;

public class NamedBox<T extends Figure> extends Box<T>{
    private String Name;

    public NamedBox(T obj, String Name) {
        super(obj);
        setName(Name);
    }

    public String getName() {
        return Name;
    }

    private void setName(String Name) {
        this.Name = Name;
    }
}
