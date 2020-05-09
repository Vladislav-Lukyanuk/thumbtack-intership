package net.thumbtack.school.hiring.Controllers;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.errors.ServerError;
import net.thumbtack.school.hiring.errors.ServerException;
import net.thumbtack.school.hiring.models.*;
import net.thumbtack.school.hiring.validations.UserValidation;

import java.lang.reflect.Type;
import java.util.UUID;

public class UserController {


    public static String register(String requestJsonString) {
        try {
            UserRegister userRegister = new Gson().fromJson(requestJsonString, UserRegister.class);

            UserValidation.registerUserValidation(userRegister);

            UUID token = UUID.randomUUID();

            if (userRegister.isEmployer()) {
                DataBase.getDataBase().addUser(new Employer(userRegister.getEmail(),
                        userRegister.getFirstName(),
                        userRegister.getLastName(),
                        userRegister.getPatronymic(),
                        userRegister.getLogin(),
                        userRegister.getPassword()), token);
            } else {
                DataBase.getDataBase().addUser(new Worker(userRegister.getEmail(),
                        userRegister.getFirstName(),
                        userRegister.getLastName(),
                        userRegister.getPatronymic(),
                        userRegister.getLogin(),
                        userRegister.getPassword()), token);
            }
            return new Gson().toJson(new TokenTransfer(token, null, null, null));

        } catch (ServerException serverException) {
            return new Gson().toJson(new TokenTransfer(null, null, null, serverException.getServerError().getErrorString()));
        }
    }


    public static String edit(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            User user = UserValidation.editUserValidation(tokenTransfer);

            DataBase.getDataBase().replaceUser(user, tokenTransfer.getToken());

            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), null, null, null));

        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String getUser(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            UserValidation.tokenValidation(tokenTransfer);

            User user = DataBase.getDataBase().getUser(tokenTransfer.getToken());

            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(user), user.getClass().getName(), null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String removeUser(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);
            UserValidation.tokenValidation(tokenTransfer);

            DataBase.getDataBase().removeUser(tokenTransfer.getToken());
            return null;
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String loginUser(String requestJsonString) {
        try {
            UserLogin userLogin = new Gson().fromJson(requestJsonString, UserLogin.class);

            UserValidation.loginUserValidation(userLogin);

            return new Gson().toJson(new TokenTransfer(DataBase.getDataBase().loginUser(userLogin), null, null, null));

        } catch (ServerException serverException) {
            return new Gson().toJson(new TokenTransfer(null, null, null, serverException.getServerError().getErrorString()));
        }
    }

    public static String logOutUser(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            UserValidation.tokenValidation(tokenTransfer);

            DataBase.getDataBase().logOutUser(tokenTransfer.getToken());
            return null;
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }
}
