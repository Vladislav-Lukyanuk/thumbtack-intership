package net.thumbtack.school.ttschool;

import java.util.LinkedList;
import java.util.Queue;

public class TraineeQueue {
    private Queue<Trainee> traineeQueue;

    //1
    public TraineeQueue() {
        traineeQueue = new LinkedList<>();
    }

    //2
    public void addTrainee(Trainee trainee) {
        traineeQueue.add(trainee);
    }

    //3
    public Trainee removeTrainee() throws TrainingException {
        Trainee trainee = traineeQueue.poll();
        if (trainee == null) {
            throw new TrainingException(TrainingErrorCode.EMTPY_TRAINEE_QUEUE);
        }
        return trainee;
    }
}
