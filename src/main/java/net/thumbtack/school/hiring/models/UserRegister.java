package net.thumbtack.school.hiring.models;

import java.util.Objects;

public class UserRegister {
    private String email;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String login;
    private String password;
    private boolean employer;

    public UserRegister(String email,
                        String firstName,
                        String lastName,
                        String patronymic,
                        String login,
                        String password,
                        boolean employer) {
        setEmail(email);
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setLogin(login);
        setPassword(password);
        setEmployer(employer);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmployer(boolean employer) {
        this.employer = employer;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEmployer() {
        return employer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegister that = (UserRegister) o;
        return  Objects.equals(email, that.email) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(patronymic, that.patronymic) &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, firstName, lastName, patronymic, login, password);
    }
}
