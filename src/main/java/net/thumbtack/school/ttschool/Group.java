package net.thumbtack.school.ttschool;

import java.util.*;
import java.util.stream.Collectors;

public class Group {
    private String name, room;
    private List<Trainee> trainees;

    //1
    public Group(String name, String room) throws TrainingException {
        setName(name);
        setRoom(room);
        trainees = new ArrayList<>();
    }

    //2
    public String getName() {
        return name;
    }

    //3
    public void setName(String name) throws TrainingException {
        if (name == null || name.equals("")) {
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_NAME);
        }
        this.name = name;
    }

    //4
    public String getRoom() {
        return room;
    }

    //5
    public void setRoom(String room) throws TrainingException {
        if (room == null || room.equals("")) {
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_ROOM);
        }
        this.room = room;
    }

    //6
    public List<Trainee> getTrainees() {
        return trainees;
    }

    //7
    public void addTrainee(Trainee trainee) {
//        long timeStart, timeEnd;
//        timeStart = System.nanoTime();
//        for(int index = 0; index < 10000; index++) {
//            List<Trainee> traineeList = new LinkedList<>(trainees);
//            traineeList.add(trainee);
//            trainees = new ArrayList<>(traineeList);
        trainees.add(trainee);
//        }
//        timeEnd = System.nanoTime();
//        System.out.println("NonBuffering Write Time = " + TimeUnit.NANOSECONDS.toMillis(timeEnd - timeStart));
    }

    //8
    public void removeTrainee(Trainee trainee) throws TrainingException {
        if (!trainees.remove(trainee)) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
    }

    //9
    public void removeTrainee(int index) throws TrainingException {
        if (index < 0 || index > trainees.size() - 1) {
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        trainees.remove(index);
    }

    //10
    public Trainee getTraineeByFirstName(String firstName) throws TrainingException {
        ListIterator<Trainee> listIterator = trainees.listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.next().getFirstName().equals(firstName)) {
                return listIterator.previous();
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    //11
    public Trainee getTraineeByFullName(String fullName) throws TrainingException {
//        Trainee trainee = trainees.stream().filter(s -> s.getFullName().equals(fullName)).findFirst().get();
        ListIterator<Trainee> listIterator = trainees.listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.next().getFullName().equals(fullName)) {
                return listIterator.previous();
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    //12
    public void sortTraineeListByFirstNameAscendant() {
        trainees.sort(Comparator.comparing(Trainee::getFirstName));
    }

    //13
    public void sortTraineeListByRatingDescendant() {
        trainees.sort((Trainee obj1, Trainee obj2) -> -Integer.compare(obj1.getRating(), obj2.getRating()));
    }

    //14
    public void reverseTraineeList() {
        Collections.reverse(trainees);
    }

    //15
    public void rotateTraineeList(int position) {
//        List<Trainee> outPutTrainees = new ArrayList<>(trainees.size());
//        for (int index = 0; index < trainees.size(); index++) {
//            outPutTrainees.set((index + position) % trainees.size(), trainees.get(index));
//        }
//        trainees = outPutTrainees;
        Collections.rotate(trainees, position);
    }

    //16
    public List<Trainee> getTraineesWithMaxRating() throws TrainingException {
        if(trainees.isEmpty()) throw  new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);

        int max = trainees.stream().max(Comparator.comparingInt(Trainee::getRating)).get().getRating();
        return trainees.stream().filter(s -> s.getRating() == max).collect(Collectors.toList());
    }

    //17
    public boolean hasDuplicates() {
        return !(new HashSet<Trainee>(trainees).size() == trainees.size());
    }

    //18
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name) &&
                Objects.equals(room, group.room) &&
                Objects.equals(trainees, group.trainees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, room, trainees);
    }
}
