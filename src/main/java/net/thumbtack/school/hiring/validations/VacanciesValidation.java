package net.thumbtack.school.hiring.validations;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.errors.ServerError;
import net.thumbtack.school.hiring.errors.ServerException;
import net.thumbtack.school.hiring.models.*;

import java.util.UUID;

public class VacanciesValidation {

//    public static UUID idVacancyValidation(String requestJsonString) throws ServerException {
//        if (requestJsonString == null || requestJsonString.equals(""))
//            throw new ServerException(ServerError.REQUEST_IS_EMPTY);
//
//        TokenTransfer tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);
//
//        if (tokenTransfer == null
//                || tokenTransfer.getToken() == null
//                || tokenTransfer.getJson() == null
//                || !tokenTransfer.getJsonClass().equals(UUID.class.getName()))
//            throw new ServerException(ServerError.REQUEST_IS_NOT_CORRECT);
//
//        return new Gson().fromJson(tokenTransfer.getJson(), UUID.class);
//    }

    public static Vacancy defaultVacancyValidation(TokenTransfer tokenTransfer) throws ServerException {
        UserValidation.tokenValidation(tokenTransfer);

        Vacancy vacancy = new Gson().fromJson(tokenTransfer.getJson(), Vacancy.class);

        return vacancyValidation(vacancy);
    }

    public static Vacancy vacancyValidation(Vacancy vacancy) throws ServerException {
        if (vacancy == null
                || vacancy.getPosition() == null
                || vacancy.getSalary() < 0
                || vacancy.getId() == null
                || vacancy.getEmployer() == null)
            throw new ServerException(ServerError.VACANCY_VALIDATION_IS_NOT_SUCCESSFUL);

        return vacancy;
    }

    public static VacancyRequirementTransfer vacancyRequirementValidation(TokenTransfer tokenTransfer) throws ServerException {
        UserValidation.tokenValidation(tokenTransfer);

        VacancyRequirementTransfer vacancyRequirementTransfer = new Gson().fromJson(tokenTransfer.getJson(), VacancyRequirementTransfer.class);

        if(vacancyRequirementTransfer == null)
            throw new ServerException(ServerError.REQUEST_IS_NOT_CORRECT);

        vacancyValidation(vacancyRequirementTransfer.getVacancy());
        SkillValidation.skillValidation(vacancyRequirementTransfer.getSkill());

        return vacancyRequirementTransfer;
    }

    public static HireWorkerTransfer hireWorkerValidation(TokenTransfer tokenTransfer) throws ServerException {
        UserValidation.tokenValidation(tokenTransfer);

        HireWorkerTransfer hireWorkerTransfer = new Gson().fromJson(tokenTransfer.getJson(), HireWorkerTransfer.class);

        if(hireWorkerTransfer == null)
            throw new ServerException(ServerError.REQUEST_IS_NOT_CORRECT);

        UserValidation.userValidation(hireWorkerTransfer.getWorker());
        vacancyValidation(hireWorkerTransfer.getVacancy());

        return hireWorkerTransfer;
    }

    public static WorkerView workerViewValidation(TokenTransfer tokenTransfer) throws ServerException {
        UserValidation.tokenValidation(tokenTransfer);

        WorkerView workerView = new Gson().fromJson(tokenTransfer.getJson(), WorkerView.class);

        if(workerView == null)
            throw new ServerException(ServerError.REQUEST_IS_NOT_CORRECT);

        if(workerView.getEmail() == null
                || workerView.getFirstName() == null
                || workerView.getLastName() == null
                || workerView.getLogin() == null)
            throw new ServerException(ServerError.WORKERVIEW_VALIDATION_IS_NOT_SUCCESSFUL);

        return workerView;
    }


//    public static VacancyRequirementTransfer vacancyRequirementTransferValidation(String requestJsonString) throws ServerException {
//        if (requestJsonString == null || requestJsonString.equals(""))
//            throw new ServerException(ServerError.REQUEST_IS_EMPTY);
//
//        TokenTransfer tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);
//
//        if (tokenTransfer == null
//                || tokenTransfer.getToken() == null
//                || tokenTransfer.getJson() == null
//                || !tokenTransfer.getJsonClass().equals(VacancyRequirementTransfer.class.getName()))
//            throw new ServerException(ServerError.REQUEST_IS_NOT_CORRECT);
//
//        VacancyRequirementTransfer vacancyRequirementTransfer = new Gson().fromJson(tokenTransfer.getJson(), VacancyRequirementTransfer.class);
//
//        if (vacancyRequirementTransfer.getId() == null || vacancyRequirementTransfer.getVacancyRequirement() == null)
//            throw new ServerException(ServerError.VACANCY_REQUIREMENT_TRANSFER_VALIDATION_IS_NOT_SUCCESSFUL);
//
//        VacancyRequirement vacancyRequirement = vacancyRequirementTransfer.getVacancyRequirement();
//
//        if (vacancyRequirement.getName() == null
//                || vacancyRequirement.getLevel() < 1
//                || vacancyRequirement.getLevel() > 5)
//            throw new ServerException(ServerError.SKILL_VALIDATION_IS_NOT_SUCCESSFUL);
//
//        return vacancyRequirementTransfer;
//    }
//
//    // tokenTransfer token | idVacancy| UUID | null
//    public static UUID vacancyFindWorkersValidation(String requestJsonString) throws ServerException {
//        if (requestJsonString == null || requestJsonString.equals(""))
//            throw new ServerException(ServerError.REQUEST_IS_EMPTY);
//
//        TokenTransfer tokenTransfer = new Gson().fromJson(requestJsonString, TokenTransfer.class);
//
//        if (tokenTransfer == null
//                || tokenTransfer.getToken() == null
//                || tokenTransfer.getJson() == null
//                || !tokenTransfer.getJsonClass().equals(UUID.class.getName()))
//            throw new ServerException(ServerError.REQUEST_IS_NOT_CORRECT);
//
//        UUID idVacancy = new Gson().fromJson(tokenTransfer.getJson(), UUID.class);
//
//        if(idVacancy == null)
//            throw new ServerException(ServerError.VACANCY_VALIDATION_IS_NOT_SUCCESSFUL);
//
//        return idVacancy;
//    }

}
