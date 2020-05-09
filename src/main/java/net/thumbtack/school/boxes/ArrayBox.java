package net.thumbtack.school.boxes;

import net.thumbtack.school.figures.v3.Figure;

import java.lang.reflect.Array;

public class ArrayBox<T extends Figure> {
    private T[] objArray;

    public ArrayBox(T[] objArray) {
        setContent(objArray);
    }

    public T[] getContent() {
        return objArray;
    }

    public T getElement(int index) {
        return objArray[index];
    }

    private void setContent(T[] objArray) {
        this.objArray = objArray;
    }

    public void setElement(int index, T obj) {
        objArray[index] = obj;
    }

    public boolean isSameSize(ArrayBox objArray) {
        return this.objArray.length == objArray.getContent().length;
    }
}
