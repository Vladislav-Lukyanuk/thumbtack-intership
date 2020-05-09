package net.thumbtack.school.ttschool;

public class TrainingException extends Exception {
    private TrainingErrorCode trainingErrorCode;

    public TrainingException(TrainingErrorCode trainingErrorCode) {
        setErrorCode( trainingErrorCode );
    }

    private void setErrorCode(TrainingErrorCode trainingErrorCode) {
        this.trainingErrorCode = trainingErrorCode;
    }

    public TrainingErrorCode getErrorCode() {
        return trainingErrorCode;
    }
}
