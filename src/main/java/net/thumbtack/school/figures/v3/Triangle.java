package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorException;

import java.util.Objects;

public class Triangle extends Figure {
    private Point2D point1 = new Point2D(),point2 = new Point2D(),point3 = new Point2D();

    private void setTriangle(Point2D point1, Point2D point2, Point2D point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public Triangle(Point2D point1, Point2D point2, Point2D point3, Color color) throws ColorException {
        setTriangle(point1, point2, point3);
        setColor(color);
    }

    public Triangle(Point2D point1, Point2D point2, Point2D point3, String color) throws ColorException {
        setTriangle(point1, point2, point3);
        setColor(color);
    }

    public Point2D getPoint1() {
        return point1;
    }

    public Point2D getPoint2() {
        return point2;
    }

    public Point2D getPoint3() {
        return point3;
    }

    public void setPoint1(Point2D point1) {
        this.point1 = point1;
    }

    public void setPoint2(Point2D point2) {
        this.point2 = point2;
    }

    public void setPoint3(Point2D point3) {
        this.point3 = point3;
    }

    public double getSide12() {
        return Math.sqrt(Math.pow((point1.getX() - point2.getX())/10.0,2) + Math.pow((point1.getY() - point2.getY())/10.0,2)) * 10;
    }

    public double getSide13() {
        return Math.sqrt(Math.pow((point1.getX() - point3.getX())/10.0,2) + Math.pow((point1.getY() - point3.getY())/10.0,2)) * 10;
    }

    public double getSide23() {
        return Math.sqrt(Math.pow((point2.getX() - point3.getX())/10.0,2) + Math.pow((point2.getY() - point3.getY())/10.0,2)) * 10;
    }

    public void moveRel(int dx, int dy) {
        point1.moveRel(dx, dy);
        point2.moveRel(dx, dy);
        point3.moveRel(dx,dy);
    }

    public double getArea() {
        double pp = (double)(getSide12() + getSide13() + getSide23())/2;
        return Math.sqrt(pp * (pp - getSide12()) * (pp - getSide13()) * (pp - getSide23()));
    }

    public double getPerimeter() {
        return getSide12() + getSide23() + getSide13();
    }

    public boolean isInside(int x, int y) {
        int a = ((point1.getX() - x)*(point2.getY() - point1.getY())) - ((point2.getX() - point1.getX())*(point1.getY() - y));
        int b = ((point2.getX() - x)*(point3.getY() - point2.getY())) - ((point3.getX() - point2.getX())*(point2.getY() - y));
        int c = ((point3.getX() - x)*(point1.getY() - point3.getY())) - ((point1.getX() - point3.getX())*(point3.getY() - y));
        return (((a >= 0)&&(b >= 0)&&(c >= 0))||((a <= 0)&&(b <= 0)&&(c <= 0)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return color == triangle.color &&
                Objects.equals(point1, triangle.point1) &&
                Objects.equals(point2, triangle.point2) &&
                Objects.equals(point3, triangle.point3);
    }

    @Override
    public int hashCode() {

        return Objects.hash(point1, point2, point3, color);
    }
}
