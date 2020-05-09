package net.thumbtack.school.cars;

import net.thumbtack.school.colors.Color;
import net.thumbtack.school.colors.ColorErrorCode;
import net.thumbtack.school.colors.ColorException;
import net.thumbtack.school.colors.Colored;

public class Car implements Colored{

    private String model;
    private int weight, maxSpeed;
    private Color color;

    private void setCar(String model, int weight, int maxSpeed) {
        this.model = model;
        this.weight = weight;
        this.maxSpeed = maxSpeed;
    }

    public Car(String model, int weight, int maxSpeed, Color color) throws ColorException {
        setCar(model, weight, maxSpeed);
        setColor(color);
    }

    public Car(String model, int weight, int maxSpeed, String color) throws ColorException {
        setCar(model, weight, maxSpeed);
        this.color = Color.colorFromString(color);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void setColor(Color color) throws ColorException {
        if(color != null) {
            this.color = color;
        } else {
            throw new ColorException(ColorErrorCode.NULL_COLOR);
        }
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(String colorString) throws ColorException {
        color = Color.colorFromString(colorString);
    }
}
