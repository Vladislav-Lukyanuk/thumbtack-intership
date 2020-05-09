package net.thumbtack.school.colors;

import net.thumbtack.school.colors.Color;

public interface Colored {
    void setColor(Color color) throws ColorException;
    Color getColor();
    void setColor(String colorString) throws ColorException;
}
