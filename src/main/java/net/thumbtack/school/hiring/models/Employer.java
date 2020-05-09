package net.thumbtack.school.hiring.models;

import java.util.Objects;
import java.util.UUID;

public class Employer extends User {
    private String companyName;
    private String companyAddress;

    public Employer(String email,
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
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyAddress(String companyAaddress) {
        this.companyAddress = companyAaddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
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
