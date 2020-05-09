package net.thumbtack.school.colors;

public enum ColorErrorCode {
    WRONG_COLOR_STRING("This color does't exist in colors library."),
    NULL_COLOR("You didn't set a colors value.");

    private String errorString;

    ColorErrorCode(String errorString){
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
