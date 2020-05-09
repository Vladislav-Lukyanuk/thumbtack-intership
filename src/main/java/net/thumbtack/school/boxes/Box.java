package net.thumbtack.school.boxes;

import net.thumbtack.school.area.HasArea;
import net.thumbtack.school.figures.v3.Figure;

public class Box<T extends Figure> implements HasArea{
    private static final double EPS = 1E-6;
    private T obj;

    public Box(T obj) {
        setContent(obj);
    }

    public T getContent() {
        return obj;
    }

    private void setContent(T obj) {
        this.obj = obj;
    }

    public boolean isAreaEqual(Box box) {
        return Math.abs(getArea() - box.getArea()) < EPS;
    }

    public static boolean isAreaEqual(Box box1, Box box2) {
        return Math.abs(box1.getArea() - box2.getArea()) < EPS;
    }

    @Override
    public double getArea() {
        return obj.getArea();
    }
}
