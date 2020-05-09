package net.thumbtack.school.hiring.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public class Worker extends User {
    private boolean active;

    public Worker(  String email,
                    String firstName,
                    String lastName,
                    String patronymic,
                    String login,
                    String password) {
        setEmail(email);
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setLogin(login);
        setPassword(password);
        setActive(true);
    }


    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode());
    }
}
