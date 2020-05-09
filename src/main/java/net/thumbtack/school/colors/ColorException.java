package net.thumbtack.school.colors;

public class ColorException extends Exception{

    private ColorErrorCode colorErrorCode;

    public ColorException(ColorErrorCode colorErrorCode) {
        this.colorErrorCode = colorErrorCode;
    }

    public ColorErrorCode getErrorCode() {
        return colorErrorCode;
    }
}
