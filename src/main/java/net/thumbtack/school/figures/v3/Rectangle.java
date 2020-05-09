package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorException;

import java.util.Objects;

public class Rectangle extends Figure {

    private Point2D leftTop = new Point2D(), rightBottom = new Point2D();

    private void setRectangle(Point2D leftTop, Point2D rightBottom) {
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
    }

    public Rectangle(Point2D leftTop, Point2D rightBottom, Color color) throws ColorException {
        setRectangle(leftTop, rightBottom);
        setColor(color);
    }

    public Rectangle(Point2D leftTop, Point2D rightBottom, String color) throws ColorException {
        setRectangle(leftTop, rightBottom);
        setColor(color);
    }

    public Rectangle(int xLeft, int yTop, int xRight, int yBottom, Color color) throws ColorException {
        this(new Point2D(xLeft,yTop), new Point2D(xRight,yBottom), color);
    }

    public Rectangle(int xLeft, int yTop, int xRight, int yBottom, String color) throws ColorException {
        this(new Point2D(xLeft,yTop), new Point2D(xRight,yBottom), color);
    }

    public Rectangle(int length, int width, Color color) throws ColorException {
        this(0,- width,length,0, color);
    }

    public Rectangle(int length, int width, String color) throws ColorException {
        this(0,- width,length,0, color);
    }

    public Rectangle(Color color) throws ColorException {
        this(0,-1,1,0, color);
    }

    public Rectangle(String color) throws ColorException {
        this(0,-1,1,0, color);
    }

    public Point2D getTopLeft() {
        return leftTop;
    }

    public Point2D getBottomRight() {
        return rightBottom;
    }

    public void setTopLeft(Point2D topLeft) {
        leftTop = topLeft;
    }

    public void setBottomRight(Point2D bottomRight) {
        rightBottom = bottomRight;
    }

    public int getLength() {
        return (rightBottom.getX() - leftTop.getX());
    }

    public int getWidth() {
        return (rightBottom.getY() - leftTop.getY());
    }

    public void moveRel(int dx, int dy) {
        leftTop.moveRel(dx,dy);
        rightBottom.moveRel(dx,dy);
    }

    public void enlarge(int nx, int ny) {
        rightBottom.setX(((rightBottom.getX() - leftTop.getX()) * nx) + leftTop.getX());
        rightBottom.setY(((rightBottom.getY() - leftTop.getY()) * ny) + leftTop.getY());
    }

    public double getArea() {
        return (double)((rightBottom.getY() - leftTop.getY())*(rightBottom.getX() - leftTop.getX()));
    }

    public double getPerimeter() {
        return (double)(((rightBottom.getY() - leftTop.getY())+(rightBottom.getX() - leftTop.getX())) * 2);
    }

    public boolean isInside(int x, int y) {
        return (((x >= leftTop.getX())&&(x <= rightBottom.getX()))&&((y >= leftTop.getY())&&(y <= rightBottom.getY())));
    }

    public boolean isIntersects(Rectangle rectangle) {
        if((leftTop.getY() > rectangle.rightBottom.getY())||(rightBottom.getY() < rectangle.leftTop.getY())||(leftTop.getX() > rectangle.rightBottom.getX())||(rightBottom.getX() < rectangle.leftTop.getX())) {
                return false;
        }
        return true;
    }

    public boolean isInside(Rectangle rectangle) {
        if((leftTop.getX() <= rectangle.leftTop.getX())&&(leftTop.getY() <= rectangle.leftTop.getY())) {
            if((rightBottom.getX() >= rectangle.rightBottom.getX())&&(rightBottom.getY() >= rectangle.rightBottom.getY())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return color == rectangle.color &&
                Objects.equals(leftTop, rectangle.leftTop) &&
                Objects.equals(rightBottom, rectangle.rightBottom);
    }

    @Override
    public int hashCode() {

        return Objects.hash(leftTop, rightBottom, color);
    }
}
