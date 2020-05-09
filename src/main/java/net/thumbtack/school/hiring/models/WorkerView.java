package net.thumbtack.school.hiring.models;

import java.util.Objects;

public class WorkerView {
    private String email;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String login;

    public WorkerView(String email,
                      String firstName,
                      String lastName,
                      String patronymic,
                      String login)
    {
        setEmail(email);
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setLogin(login);
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    private void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getLogin() {
        return login;
    }

    private void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkerView that = (WorkerView) o;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {

        return Objects.hash(login);
    }
}
