package net.thumbtack.school.hiring.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public class Vacancy {
    private UUID id;
    private String position;
    private int salary;
    private Boolean active;
    private String employer;


    public Vacancy(String position, int salary, String employer) {
        id = UUID.randomUUID();
        setPosition(position);
        setSalary(salary);
        setActive(true);
        setEmployer(employer);
    }

    private void setEmployer(String employer) {
        this.employer = employer;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setActive(Boolean activity) {
        this.active = activity;
    }

    public UUID getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public int getSalary() {
        return salary;
    }

    public String getEmployer() {
        return employer;
    }

    public Boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(position, vacancy.position) &&
                Objects.equals(employer, vacancy.employer);
    }

    @Override
    public int hashCode() {

        return Objects.hash(position, employer);
    }
}
