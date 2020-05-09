package net.thumbtack.school.ttschool;

public enum TrainingErrorCode {
    TRAINEE_WRONG_FIRSTNAME("Wrong first name."),
    TRAINEE_WRONG_LASTNAME("Wrong last name."),
    TRAINEE_WRONG_RATING("Wrong rating."),
    TRAINEE_NOT_FOUND("Trainee wasn't found"),
    GROUP_WRONG_NAME("Wrong group name."),
    GROUP_WRONG_ROOM("Wrong room name."),
    GROUP_NOT_FOUND("Group wasn't found."),
    DUPLICATE_GROUP_NAME("This group is already exist."),
    DUPLICATE_TRAINEE("This trainee is already exist."),
    EMTPY_TRAINEE_QUEUE("The queue is empty."),
    SCHOOL_WRONG_NAME("Wrong school name.");

    private String errorString;

    TrainingErrorCode(String errorString) {
        setErrorString(errorString);
    }

    private void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
