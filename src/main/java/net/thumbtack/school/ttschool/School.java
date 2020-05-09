package net.thumbtack.school.ttschool;

import java.util.*;

public class School {
    private String name;
    private int scienceYear;
    private Set<Group> groups;

    //1
    public School(String name, int year) throws TrainingException {
        setName(name);
        setYear(year);
        groups = new HashSet<>();
    }

    //2
    public String getName() {
        return name;
    }

    //3
    public void setName(String name) throws TrainingException {
        if (name == null || name.equals("")) {
            throw new TrainingException(TrainingErrorCode.SCHOOL_WRONG_NAME);
        }
        this.name = name;
    }

    //4
    public int getYear() {
        return scienceYear;
    }

    //5
    public void setYear(int year) {
        this.scienceYear = year;
    }

    //6
    public Set<Group> getGroups() {
        return groups;
    }

    //7
    public void addGroup(Group group) throws TrainingException {
        if (containsGroup(group)) {
            throw new TrainingException(TrainingErrorCode.DUPLICATE_GROUP_NAME);
        }
        groups.add(group);
    }

    //8
    public void removeGroup(Group group) throws TrainingException {
        if (!groups.remove(group)) {
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }
    }

    //9
    public void removeGroup(String name) throws TrainingException {
        for (Iterator<Group> iterator = groups.iterator(); iterator.hasNext(); ) {
            Group group = iterator.next();
            if (group.getName().equals(name)) {
                groups.remove(group);
                return;
            }
        }
        throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);

    }

    //10
    public boolean containsGroup(Group group) {
        return groups.stream().anyMatch(s -> s.getName().equals(group.getName()));
    }

    //11
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return scienceYear == school.scienceYear &&
                Objects.equals(name, school.name) &&
                Objects.equals(groups, school.groups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, scienceYear, groups);
    }
}
