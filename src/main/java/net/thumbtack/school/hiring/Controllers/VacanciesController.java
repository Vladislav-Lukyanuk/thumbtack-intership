package net.thumbtack.school.hiring.Controllers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import net.thumbtack.school.hiring.errors.ServerError;
import net.thumbtack.school.hiring.errors.ServerException;
import net.thumbtack.school.hiring.models.*;
import net.thumbtack.school.hiring.validations.UserValidation;
import net.thumbtack.school.hiring.validations.VacanciesValidation;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class VacanciesController {

    public static String getWorkerViewSkills(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            WorkerView workerView = VacanciesValidation.workerViewValidation(tokenTransfer);

            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(),
                    new Gson().toJson(DataBase.getDataBase().getWorkerViewSkills(workerView.getLogin(), tokenTransfer.getToken())),
                    new TypeToken<HashMap<String, Integer>>() {
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

    public static String addVacancy(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            Vacancy vacancy = VacanciesValidation.defaultVacancyValidation(tokenTransfer);

            DataBase.getDataBase().addVacancy(vacancy, tokenTransfer.getToken());

            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), null, null, null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String getVacancies(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            UserValidation.tokenValidation(tokenTransfer);

            DataBase.getDataBase().getUser(tokenTransfer.getToken());

            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(),
                    new Gson().toJson(DataBase.getDataBase().getVacancies()),
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

    public static String getVacancyRequirements(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            Vacancy vacancy = VacanciesValidation.defaultVacancyValidation(tokenTransfer);

            DataBase.getDataBase().getUser(tokenTransfer.getToken());

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(new TypeToken<HashMap<Skill, Boolean>>(){}.getType(), new CustomConverterHashMapSkillBoolean());

            Gson gson = builder.create();

            String json = gson.toJson(DataBase.getDataBase().getVacancyRequirements(vacancy), new TypeToken<HashMap<Skill, Boolean>>(){}.getType());

            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(),
                    json,
                    new TypeToken<HashMap<Skill, Boolean>>() {
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

//    public static String getVacancyId(String requestJsonString) {
//        try {
//            Vacancy vacancy = VacanciesValidation.defaultVacancyValidation(requestJsonString);
//
//            TokenTransfer tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);
//
//            User user = DataBase.getUser(tokenTransfer.getToken());
//            if (!user.getClass().getName().equals(Employer.class.getName()))
//                throw new ServerException(ServerError.USER_IS_NOT_A_EMPLOYER);
//
//            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(DataBase.getVacancyId(vacancy, tokenTransfer.getToken())), UUID.class.getName(), null));
//
//        } catch (ServerException serverException) {
//            return new Gson().toJson(new TokenTransfer(null, null, null, serverException.getServerError().getErrorString()));
//        }
//
//    }

    public static String removeVacancy(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            Vacancy vacancy = VacanciesValidation.defaultVacancyValidation(tokenTransfer);

            DataBase.getDataBase().removeVacancy(vacancy, tokenTransfer.getToken());

            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), null, null, null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String editVacancy(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            Vacancy vacancy = VacanciesValidation.defaultVacancyValidation(tokenTransfer);

            DataBase.getDataBase().editVacancy(vacancy, tokenTransfer.getToken());

            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), null, null, null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String setActiveVacancy(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            Vacancy vacancy = VacanciesValidation.defaultVacancyValidation(tokenTransfer);

            vacancy.setActive(true);

            DataBase.getDataBase().editVacancy(vacancy, tokenTransfer.getToken());

            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), null, null, null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String setNonActiveVacancy(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            Vacancy vacancy = VacanciesValidation.defaultVacancyValidation(tokenTransfer);

            vacancy.setActive(false);

            DataBase.getDataBase().editVacancy(vacancy, tokenTransfer.getToken());

            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), null, null, null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    //принимает tokenTransfer = token | vacancyRequirementTransfer | class | null
    public static String addVacancyRequirement(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            VacancyRequirementTransfer vacancyRequirementTransfer = VacanciesValidation.vacancyRequirementValidation(tokenTransfer);

            DataBase.getDataBase().addVacancyRequirement(vacancyRequirementTransfer.getVacancy(),
                    vacancyRequirementTransfer.getSkill(),
                    vacancyRequirementTransfer.getNecessary(),
                    tokenTransfer.getToken());

            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), null, null, null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String removeVacancyRequirement(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            VacancyRequirementTransfer vacancyRequirementTransfer = VacanciesValidation.vacancyRequirementValidation(tokenTransfer);

            DataBase.getDataBase().removeVacancyRequirement(vacancyRequirementTransfer.getVacancy(),
                    vacancyRequirementTransfer.getSkill(),
                    tokenTransfer.getToken());

            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), null, null, null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

    public static String editVacancyRequirement(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            VacancyRequirementTransfer vacancyRequirementTransfer = VacanciesValidation.vacancyRequirementValidation(tokenTransfer);

            DataBase.getDataBase().replaceVacancyRequirement(vacancyRequirementTransfer.getVacancy(),
                    vacancyRequirementTransfer.getSkill(),
                    vacancyRequirementTransfer.getNecessary(),
                    tokenTransfer.getToken());

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
        return WorkerController.getAllSkills(requestJsonString);
    }

    public static String getEmployerVacancies(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            UserValidation.tokenValidation(tokenTransfer);

            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer.getToken(),
                            new Gson().toJson(
                                    DataBase.getDataBase().getEmployerVacancies(tokenTransfer.getToken())),
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

    public static String getActiveEmployerVacancies(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            UserValidation.tokenValidation(tokenTransfer);

            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer.getToken(),
                            new Gson().toJson(
                                    DataBase.getDataBase().getActiveEmployerVacancies(tokenTransfer.getToken())),
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

    public static String getNonActiveEmployerVacancies(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            UserValidation.tokenValidation(tokenTransfer);

            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer.getToken(),
                            new Gson().toJson(
                                    DataBase.getDataBase().getNonActiveEmployerVacancies(tokenTransfer.getToken())),
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

    // tokenTransfer  = token | Vacancy| class | null
    public static String getWorkersWithNecessarySkills(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            Vacancy vacancy = VacanciesValidation.defaultVacancyValidation(tokenTransfer);

            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer.getToken(),
                            new Gson().toJson(DataBase.getDataBase().getWorkersWithNecessarySkills(vacancy, tokenTransfer.getToken())),
                            new TypeToken<HashSet<WorkerView>>() {
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

    public static String getWorkersWithAllSkills(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            Vacancy vacancy = VacanciesValidation.defaultVacancyValidation(tokenTransfer);

            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer.getToken(),
                            new Gson().toJson(DataBase.getDataBase().getWorkersWithAllSkills(vacancy, tokenTransfer.getToken())),
                            new TypeToken<HashSet<WorkerView>>() {
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

    public static String getWorkersWithAnyLevelSkills(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            Vacancy vacancy = VacanciesValidation.defaultVacancyValidation(tokenTransfer);

            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer.getToken(),
                            new Gson().toJson(DataBase.getDataBase().getWorkersWithAnyLevelSkills(vacancy, tokenTransfer.getToken())),
                            new TypeToken<HashSet<WorkerView>>() {
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

    public static String getWorkersWithOneSkills(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);

            Vacancy vacancy = VacanciesValidation.defaultVacancyValidation(tokenTransfer);

            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer.getToken(),
                            new Gson().toJson(DataBase.getDataBase().getWorkersWithOneSkills(vacancy, tokenTransfer.getToken())),
                            new TypeToken<HashSet<WorkerView>>() {
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

    // TokenTransfer = token | hireWorkerTransfer
    public static String hireAnWorker(String requestJsonString) {
        TokenTransfer tokenTransfer = null;
        try {
            tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);
            HireWorkerTransfer hireWorkerTransfer = VacanciesValidation.hireWorkerValidation(tokenTransfer);

            Vacancy vacancy = hireWorkerTransfer.getVacancy();
            vacancy.setActive(false);
            DataBase.getDataBase().editVacancy(vacancy, tokenTransfer.getToken());

            Worker worker = hireWorkerTransfer.getWorker();
            worker.setActive(false);
            UUID token = DataBase.getDataBase().getWorkerToken(worker);
            DataBase.getDataBase().replaceUser(worker, token);

            return new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), null, null, null));
        } catch (ServerException serverException) {
            return new Gson().toJson(
                    new TokenTransfer(tokenTransfer == null ? null : tokenTransfer.getToken(),
                            null,
                            null,
                            serverException.getServerError().getErrorString()));
        }
    }

}
