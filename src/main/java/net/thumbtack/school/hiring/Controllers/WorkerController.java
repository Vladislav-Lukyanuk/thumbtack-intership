package net.thumbtack.school.hiring.Controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.thumbtack.school.hiring.errors.ServerError;
import net.thumbtack.school.hiring.errors.ServerException;
import net.thumbtack.school.hiring.models.*;
import net.thumbtack.school.hiring.validations.SkillValidation;
import net.thumbtack.school.hiring.validations.UserValidation;

import java.lang.reflect.Type;
import java.util.*;

public class WorkerController {

    public static String addSkill(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);
            Skill skill = SkillValidation.defaultSkillValidation(tokenTransfer);

            DataBase.getDataBase().addWorkerSkill(skill, tokenTransfer.getToken());
            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), null, null, null));

        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String getWorkerSkills(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);
            UserValidation.tokenValidation(tokenTransfer);

            Type type = new TypeToken<HashMap<String, Integer>>() {
            }.getType();

            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer.getToken(),
                            new Gson().toJson(DataBase.getDataBase().getWorkerSkills(tokenTransfer.getToken())),
                            type.getTypeName(),
                            null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String removeSkill(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);
            Skill skill = SkillValidation.defaultSkillValidation(tokenTransfer);

            DataBase.getDataBase().removeWorkerSkill(skill, tokenTransfer.getToken());
            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), null, null, null));

        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String editSkill(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);
            Skill skill = SkillValidation.defaultSkillValidation(tokenTransfer);

            DataBase.getDataBase().editWorkerSkill(skill, tokenTransfer.getToken());
            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), null, null, null));

        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String setActive(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);
            UserValidation.tokenValidation(tokenTransfer);

            User user = DataBase.getDataBase().getUser(tokenTransfer.getToken());

            if (!user.getClass().getName().equals(Worker.class.getName()))
                throw new ServerException(ServerError.USER_IS_NOT_A_WORKER);

            ((Worker) user).setActive(true);
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

    public static String setNonActive(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);
            UserValidation.tokenValidation(tokenTransfer);

            User user = DataBase.getDataBase().getUser(tokenTransfer.getToken());

            if (!user.getClass().getName().equals(Worker.class.getName()))
                throw new ServerException(ServerError.USER_IS_NOT_A_WORKER);

            ((Worker) user).setActive(false);
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

    public static String getAllSkills(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            UserValidation.tokenValidation(tokenTransfer);

            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer.getToken(),
                            new Gson().toJson(DataBase.getDataBase().getAllSkills(tokenTransfer.getToken())),
                            new TypeToken<HashSet<Skill>>() {
                            }.getType().getTypeName(),
                            null));

        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String getVacanciesWithAllSkills(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            UserValidation.tokenValidation(tokenTransfer);

            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer.getToken(),
                            new Gson().toJson(DataBase.getDataBase().getVacanciesWithAllSkills(tokenTransfer.getToken())),
                            new TypeToken<HashSet<Vacancy>>() {
                            }.getType().getTypeName(),
                            null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String getVacanciesWithNecessarySkills(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            UserValidation.tokenValidation(tokenTransfer);

            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer.getToken(),
                            new Gson().toJson(DataBase.getDataBase().getVacanciesWithNecessarySkills(tokenTransfer.getToken())),
                            new TypeToken<HashSet<Vacancy>>() {
                            }.getType().getTypeName(),
                            null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String getVacanciesWithAnyLevelSkills(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            UserValidation.tokenValidation(tokenTransfer);

            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer.getToken(),
                            new Gson().toJson(DataBase.getDataBase().getVacanciesWithAnyLevelSkills(tokenTransfer.getToken())),
                            new TypeToken<HashSet<Vacancy>>() {
                            }.getType().getTypeName(),
                            null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String getVacanciesWithOneSkills(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            UserValidation.tokenValidation(tokenTransfer);

            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer.getToken(),
                            new Gson().toJson(DataBase.getDataBase().getVacanciesWithOneSkills(tokenTransfer.getToken())),
                            new TypeToken<LinkedHashSet<Vacancy>>() {
                            }.getType().getTypeName(),
                            null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }
}
