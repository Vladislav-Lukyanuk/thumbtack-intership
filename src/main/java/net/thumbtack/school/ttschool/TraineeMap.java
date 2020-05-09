package net.thumbtack.school.ttschool;

import java.util.*;

public class TraineeMap {
    private Map<Trainee, String> traineesInfo;

    //1
    public TraineeMap() {
//        traineesInfo = new TreeMap<Trainee, String>(Comparator.comparing(Trainee::getFirstName).thenComparing(Trainee::getLastName));
        traineesInfo = new HashMap<>();
    }

    //2
    public void addTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if (traineesInfo.containsKey(trainee)) {
            throw new TrainingException(TrainingErrorCode.DUPLICATE_TRAINEE);
        }
        traineesInfo.put(trainee, institute);
    }

    //3
    public void replaceTraineeInfo(Trainee trainee, String institute) throws TrainingException {
        if (!traineesInfo.containsKey(trainee)) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        traineesInfo.replace(trainee, institute);
    }

    //4
    public void removeTraineeInfo(Trainee trainee) throws TrainingException {
        if (traineesInfo.remove(trainee) == null) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    //5
    public int getTraineesCount() {
        return traineesInfo.size();
    }

    //6
    public String getInstituteByTrainee(Trainee trainee) throws TrainingException {
        String institute;
        if ((institute = traineesInfo.get(trainee)) == null) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        return institute;
    }

    //7
    public Set<Trainee> getAllTrainees() {
        return traineesInfo.keySet();
    }

    //8
    public Set<String> getAllInstitutes() {
        return new HashSet<>(traineesInfo.values());
    }

    //9
    public boolean isAnyFromInstitute(String institute) {
        return traineesInfo.containsValue(institute);
    }

}
