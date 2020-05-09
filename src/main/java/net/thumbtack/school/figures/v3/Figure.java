package net.thumbtack.school.figures.v3;

import net.thumbtack.school.area.HasArea;
import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorErrorCode;
import net.thumbtack.school.colors.ColorException;
import net.thumbtack.school.colors.Colored;

abstract public class Figure implements Colored, HasArea {

    Color color;

    abstract public void moveRel(int dx, int dy);
    abstract public double getArea();
    abstract public double getPerimeter();
    abstract public boolean isInside(int x, int y);
    abstract public boolean equals(Object o);
    abstract public int hashCode();

    public boolean isInside(Point2D point) {
        return isInside(point.getX(), point.getY());
    }

    public void setColor(Color color) throws ColorException {
        if(color != null) {
            this.color = color;
        } else {
            throw new ColorException(ColorErrorCode.NULL_COLOR);
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(String colorString) throws ColorException {
        color = Color.colorFromString(colorString);
    }
}
