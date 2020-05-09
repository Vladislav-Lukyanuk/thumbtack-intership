package net.thumbtack.school.hiring.validations;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.thumbtack.school.hiring.errors.ServerError;
import net.thumbtack.school.hiring.errors.ServerException;
import net.thumbtack.school.hiring.models.*;

import java.lang.reflect.Type;
import java.util.UUID;

public class UserValidation {

    public static void registerUserValidation(UserRegister user) throws ServerException {
        if (user == null)
            throw new ServerException(ServerError.REQUEST_IS_NOT_CORRECT);

        if (user.getEmail() == null || user.getFirstName() == null || user.getLastName() == null || user.getLogin() == null || user.getPassword() == null)
            throw new ServerException(ServerError.USER_VALIDATION_IS_NOT_SUCCESSFUL);
    }

    public static void userValidation(User user) throws ServerException {
        registerUserValidation(new UserRegister(user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPatronymic(),
                user.getLogin(),
                user.getPassword(),
                false));
    }

    public static void loginUserValidation(UserLogin user) throws ServerException {
        if (user == null || user.getLogin() == null || user.getPassword() == null)
            throw new ServerException(ServerError.USER_VALIDATION_IS_NOT_SUCCESSFUL);
    }

    public static void tokenValidation(TokenTransfer tokenTransfer) throws ServerException {
        if (tokenTransfer == null || tokenTransfer.getToken() == null)
            throw new ServerException(ServerError.REQUEST_IS_NOT_CORRECT);
    }

    public static User editUserValidation(TokenTransfer tokenTransfer) throws ServerException {
        tokenValidation(tokenTransfer);

        Type type = null;

        if (tokenTransfer.getJsonClass().equals(Worker.class.getName()))
          type = new TypeToken<Worker>(){}.getType();

        if (tokenTransfer.getJsonClass().equals(Employer.class.getName()))
            type = new TypeToken<Employer>(){}.getType();

        if(type == null)
            throw new ServerException(ServerError.REQUEST_IS_NOT_CORRECT);

        User user = new Gson().fromJson(tokenTransfer.getJson(), type);

        userValidation(user);

        return user;
    }
}
