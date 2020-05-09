package net.thumbtack.school.boxes;

import net.thumbtack.school.area.HasArea;
import net.thumbtack.school.figures.v3.Figure;

public class PairBox<T extends Figure, V extends Figure> implements HasArea{
    private static final double EPS = 1E-6;
    private T obj1;
    private V obj2;

    public PairBox (T obj1, V obj2) {
        setContentFirst(obj1);
        setContentSecond(obj2);
    }

    public T getContentFirst() {
        return obj1;
    }

    public V getContentSecond() {
        return obj2;
    }

    private void setContentFirst(T obj1) {
        this.obj1 = obj1;
    }

    private void setContentSecond(V obj2) {
        this.obj2 = obj2;
    }

    public boolean isAreaEqual(PairBox pairbox) {
        return Math.abs(getArea() - pairbox.getArea()) < EPS;
    }

    public static boolean isAreaEqual(PairBox pairBox1, PairBox pairBox2) {
        return Math.abs(pairBox1.getArea() - pairBox2.getArea()) < EPS;
    }

    @Override
    public double getArea() {
        return obj1.getArea() + obj2.getArea();
    }
}
