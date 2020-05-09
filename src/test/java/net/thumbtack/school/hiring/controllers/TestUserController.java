package net.thumbtack.school.hiring.controllers;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.Controllers.DataBase;
import net.thumbtack.school.hiring.Controllers.UserController;
import net.thumbtack.school.hiring.errors.ServerError;
import net.thumbtack.school.hiring.models.Employer;
import net.thumbtack.school.hiring.models.TokenTransfer;
import net.thumbtack.school.hiring.models.UserLogin;
import net.thumbtack.school.hiring.models.UserRegister;
import net.thumbtack.school.hiring.server.Server;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TestUserController {

    @Before
    public void resetDataBase() {
        Server.startServer(null);
        DataBase.getDataBase().resetDataBase();
    }


    @Test
    public void testWorkerRegister() {

        UserRegister user = new UserRegister("a@mail.ru",
                "Petya",
                "Vasechkin",
                "Dmitrievich",
                "Petya777",
                "1234",
                false);

        String response = UserController.register(new Gson().toJson(user));

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.getUser(response), TokenTransfer.class);

        UserRegister userRegister =  new Gson().fromJson(tokenTransfer.getJson(), UserRegister.class);

        assertEquals(user, userRegister);
    }

    @Test
    public void testEmployerRegister() {

        UserRegister user = new UserRegister("a@mail.ru",
                "Nikita",
                "Chernyaev",
                null,
                "Nikita123",
                "1234",
                true);

        String response = UserController.register(new Gson().toJson(user));

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.getUser(response), TokenTransfer.class);

        UserRegister userRegister = new Gson().fromJson(tokenTransfer.getJson(), UserRegister.class);

        assertEquals(user, userRegister);
    }

    @Test
    public void testRegisterAlreadyExistUser() {

        UserRegister user = new UserRegister("a@mail.ru",
                "Nikita",
                "Chernyaev",
                null,
                "Nikita123",
                "1234",
                true);

        String response = UserController.register(new Gson().toJson(user));

        response = UserController.register(new Gson().toJson(user));

        TokenTransfer tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        assertEquals(ServerError.USER_ALREADY_EXIST.getErrorString(), tokenTransfer.getError());
    }

    @Test
    public void testEditUser() {
        Server.startServer(null);

        UserRegister user = new UserRegister("a@mail.ru",
                "Petya",
                "Vasechkin",
                "Dmitrievich",
                "Petya777",
                "1234",
                true);

        TokenTransfer tokenTransfer = null;

        //проверка на некорректные данные
        //getUser всегда ищет по токену который к нему обращается, так исключается запрос конф. данных одного пользователя от другого
        tokenTransfer = new Gson().fromJson(UserController.getUser(new Gson().toJson(tokenTransfer)), TokenTransfer.class);
        assertEquals(ServerError.REQUEST_IS_NOT_CORRECT.getErrorString(), tokenTransfer.getError());

        //при успешной регистрации возвращается класс TokenTransfer c одним заполненным полем token - для дальнейшей работы от данного зарегестрированного пользователя
        tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user)), TokenTransfer.class);

        //узнаем нашего пользователя при помощи token, возвращается TokenTransfer с полями token , json, jsonClass
        tokenTransfer = new Gson().fromJson(UserController.getUser(new Gson().toJson(tokenTransfer)), TokenTransfer.class);
        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        //меняем ему имя, замечу, что заменить login or token не возможно из-за настроек доступа
        employer.setFirstName("Sanya");

        //создаем новый token, в который вкладываем token и нашего измененного пользователя
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(employer), Employer.class.getName(), null);

        //данный метод разрешит изменение только при условии , что мы меняем данные от того же пользователя
        TokenTransfer errorTokenTransfer = new Gson().fromJson(UserController.edit(new Gson().toJson(new TokenTransfer(UUID.randomUUID(), tokenTransfer.getJson(), tokenTransfer.getJsonClass(), null))), TokenTransfer.class);
        assertEquals(ServerError.REQUEST_IS_NOT_ALLOW.getErrorString(), errorTokenTransfer.getError());

        tokenTransfer = new Gson().fromJson(UserController.edit(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        tokenTransfer = new Gson().fromJson(UserController.getUser(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        Employer employer2 = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        assertEquals("Sanya", employer2.getFirstName());

    }

    @Test
    public void testRemoveUser() {
        Server.startServer(null);

        UserRegister user = new UserRegister("a@mail.ru",
                "Petya",
                "Vasechkin",
                "Dmitrievich",
                "Petya777",
                "1234",
                true);

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user)), TokenTransfer.class);

        tokenTransfer = new Gson().fromJson(UserController.removeUser(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        assertEquals(null, tokenTransfer);
    }

    @Test
    public void testIncorrectUser() {
        Server.startServer(null);

        UserRegister user = new UserRegister("a@mail.ru",
                null,
                "Vasechkin",
                "Dmitrievich",
                "Petya777",
                "1234",
                true);

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user)), TokenTransfer.class);

        assertEquals(ServerError.USER_VALIDATION_IS_NOT_SUCCESSFUL.getErrorString(), tokenTransfer.getError());
    }

    @Test
    public void testLoginAndLogOutUser() {
        Server.startServer(null);

        UserRegister user = new UserRegister("a@mail.ru",
                "Petya",
                "Vasechkin",
                "Dmitrievich",
                "Petya777",
                "1234",
                true);

        UserController.register(new Gson().toJson(user));

        UserLogin userLogin = new UserLogin(null, "1234");

        //проверка неполноты полей
        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.loginUser(new Gson().toJson(userLogin)), TokenTransfer.class);
        assertEquals(ServerError.USER_VALIDATION_IS_NOT_SUCCESSFUL.getErrorString(), tokenTransfer.getError());

        //проверка корректности данных
        userLogin = new UserLogin("Petya777", "124");
        tokenTransfer = new Gson().fromJson(UserController.loginUser(new Gson().toJson(userLogin)), TokenTransfer.class);
        assertEquals(ServerError.USER_NOT_EXIST.getErrorString(), tokenTransfer.getError());

        //проверка входа
        userLogin = new UserLogin("Petya777", "1234");
        tokenTransfer = new Gson().fromJson(UserController.loginUser(new Gson().toJson(userLogin)), TokenTransfer.class);
        assertEquals(null, tokenTransfer.getError());

        //проверка выхода
        tokenTransfer = new Gson().fromJson(UserController.logOutUser(new Gson().toJson(tokenTransfer)), TokenTransfer.class);
        assertEquals(null, tokenTransfer);
    }


}
