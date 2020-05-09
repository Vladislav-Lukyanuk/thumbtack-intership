package net.thumbtack.school.hiring.errors;

public enum ServerError {
    USER_NOT_EXIST("The user wasn't found."),
    USER_ALREADY_EXIST("The user exist in the data base"),
    USER_VALIDATION_IS_NOT_SUCCESSFUL("The user validation is not successful."),
    USER_IS_NOT_A_WORKER("Current user is not a worker"),
    USER_IS_NOT_A_EMPLOYER("Current user is not a employer."),
    REQUEST_IS_EMPTY("The request is empty."),
    REQUEST_IS_NOT_CORRECT("The request wasn't correct"),
    REQUEST_IS_NOT_ALLOW("The request was reject."),
    SKILL_VALIDATION_IS_NOT_SUCCESSFUL("The skill wasn't correct.7"),
    SKILL_ALREADY_EXIST("Your skill already exist in data base."),
    SKILL_IS_NOT_EXIST("The skill is not exist in data base"),
    VACANCY_VALIDATION_IS_NOT_SUCCESSFUL("The vacancy validation wasn't successful."),
    VACANCY_ALREADY_EXIST("The vacancy already exist in data base."),
    VACANCY_IS_NOT_EXIST("The vacancy is not exist in data base."),
    VACANCY_REQUIREMENT_TRANSFER_VALIDATION_IS_NOT_SUCCESSFUL("The vacancy requirement transfer validation wasn't successful."),
    VACANCY_REQUIREMENT_ALREADY_EXIST("The vacancy requirement already exist."),
    VACANCY_REQUIREMENT_IS_NOT_EXIST("The vacancy requirement is not exist in data base."),
    WORKERVIEW_VALIDATION_IS_NOT_SUCCESSFUL("The worker view validation wasn't successful.");

    private String errorString;

    ServerError(String errorString){
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }
}
