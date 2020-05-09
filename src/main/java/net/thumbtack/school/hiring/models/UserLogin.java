package net.thumbtack.school.hiring.models;

public class UserLogin {
    private String login;
    private String password;

    public UserLogin(String login, String password) {
        setLogin(login);
        setPassword(password);
    }
    private void setLogin(String login) {
        this.login = login;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
