package net.thumbtack.school.hiring.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.thumbtack.school.hiring.Controllers.DataBase;
import net.thumbtack.school.hiring.Controllers.UserController;
import net.thumbtack.school.hiring.Controllers.VacanciesController;
import net.thumbtack.school.hiring.Controllers.WorkerController;
import net.thumbtack.school.hiring.errors.ServerError;
import net.thumbtack.school.hiring.models.*;
import net.thumbtack.school.hiring.server.Server;
import org.junit.Before;
import org.junit.Test;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

import static org.junit.Assert.assertEquals;


public class TestWorkerController {

    @Before
    public void resetDataBase() {
        Server.startServer(null);
        DataBase.getDataBase().resetDataBase();
    }

    @Test
    public void testAddSkill() {
        UserRegister user = new UserRegister("a@mail.ru",
                "Petya",
                "Vasechkin",
                "Dmitrievich",
                "Petya777",
                "1234",
                false);

        Skill skill = new Skill("Java", 4);

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user)), TokenTransfer.class);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);

        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        Skill skill2 = new Skill("C#", 4);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill2), Skill.class.getName(), null);

        String response = WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        tokenTransfer = new Gson().fromJson(WorkerController.getWorkerSkills(response), TokenTransfer.class);

        HashMap<String, Integer> skills = new Gson().fromJson(tokenTransfer.getJson(), new TypeToken<HashMap<String, Integer>>() {
        }.getType());

        assertEquals(2, skills.size());
        assertEquals(true, skills.containsKey(skill.getName()));
        assertEquals(true, skills.containsKey(skill2.getName()));
    }

    @Test
    public void testIncorrectSkill() {
        UserRegister user = new UserRegister("a@mail.ru",
                "Petya",
                "Vasechkin",
                "Dmitrievich",
                "Petya777",
                "1234",
                false);

        Skill skill = new Skill("Java", 7);

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user)), TokenTransfer.class);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);

        String response = WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        assertEquals(ServerError.SKILL_VALIDATION_IS_NOT_SUCCESSFUL.getErrorString(), tokenTransfer.getError());
    }

    @Test
    public void testEditSkill() {
        UserRegister user = new UserRegister("a@mail.ru",
                "Petya",
                "Vasechkin",
                "Dmitrievich",
                "Petya777",
                "1234",
                false);

        Skill skill = new Skill("Java", 4);

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user)), TokenTransfer.class);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);

        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        skill.setLevel(5);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);

        WorkerController.editSkill(new Gson().toJson(tokenTransfer));

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), null, null, null);

        tokenTransfer = new Gson().fromJson(WorkerController.getWorkerSkills(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        HashMap<String, Integer> skills = new Gson().fromJson(tokenTransfer.getJson(), new TypeToken<HashMap<String, Integer>>() {
        }.getType());

        assertEquals(5, skills.get(skill.getName()).intValue());
    }

    @Test
    public void testEditNotExitSkill() {
        UserRegister user = new UserRegister("a@mail.ru",
                "Petya",
                "Vasechkin",
                "Dmitrievich",
                "Petya777",
                "1234",
                false);

        Skill skill = new Skill("Java", 5);

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user)), TokenTransfer.class);


        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);

        String response = WorkerController.editSkill(new Gson().toJson(tokenTransfer));

        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        assertEquals(ServerError.SKILL_IS_NOT_EXIST.getErrorString(), tokenTransfer.getError());
    }

    @Test
    public void testRemoveSkill() {
        UserRegister user = new UserRegister("a@mail.ru",
                "Petya",
                "Vasechkin",
                "Dmitrievich",
                "Petya777",
                "1234",
                false);

        Skill skill = new Skill("Java", 4);

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user)), TokenTransfer.class);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);

        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        WorkerController.removeSkill(new Gson().toJson(tokenTransfer));

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), null, null, null);

        tokenTransfer = new Gson().fromJson(WorkerController.getWorkerSkills(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        HashMap<String, Integer> skills = new Gson().fromJson(tokenTransfer.getJson(), new TypeToken<HashMap<String, Integer>>() {
        }.getType());

        assertEquals(0, skills.size());
    }

    @Test
    public void testRemoveNotExistSkill() {
        UserRegister user = new UserRegister("a@mail.ru",
                "Petya",
                "Vasechkin",
                "Dmitrievich",
                "Petya777",
                "1234",
                false);

        Skill skill = new Skill("Java", 4);

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user)), TokenTransfer.class);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);

        String response = WorkerController.removeSkill(new Gson().toJson(tokenTransfer));

        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        assertEquals(ServerError.SKILL_IS_NOT_EXIST.getErrorString(), tokenTransfer.getError());
    }

    @Test
    public void testSetNonActiveAndActiveWorker() {
        UserRegister user = new UserRegister("a@mail.ru",
                "Petya",
                "Vasechkin",
                "Dmitrievich",
                "Petya777",
                "1234",
                false);

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user)), TokenTransfer.class);

        WorkerController.setNonActive(new Gson().toJson(tokenTransfer));


        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), null, null, null);

        String response = UserController.getUser(new Gson().toJson(tokenTransfer));

        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        Worker worker = new Gson().fromJson(tokenTransfer.getJson(), Worker.class);

        assertEquals(false, worker.isActive());


        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), null, null, null);

        WorkerController.setActive(new Gson().toJson(tokenTransfer));

        response = UserController.getUser(new Gson().toJson(tokenTransfer));

        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        worker = new Gson().fromJson(tokenTransfer.getJson(), Worker.class);

        assertEquals(true, worker.isActive());
    }

    private TokenTransfer addEmployer(String login, String company) {
        UserRegister user = new UserRegister("secret@mail.ru",
                "secret",
                "secret",
                null,
                login,
                "1234",
                true);

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user)), TokenTransfer.class);

        String response = UserController.getUser(new Gson().toJson(tokenTransfer));

        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        employer.setCompanyName(company);

        response = UserController.edit(new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(employer), employer.getClass().getName(), null)));

        response = UserController.getUser(response);

        return new Gson().fromJson(response, TokenTransfer.class);
    }

    private void registerVacancyWithRequirements() {
        TokenTransfer tokenTransferEmployer = addEmployer("petya", "rivalGame");

        Employer employer = new Gson().fromJson(tokenTransferEmployer.getJson(), Employer.class);
        Vacancy vacancy = new Vacancy("C++ developer", 75000, employer.getCompanyName());

        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);
        VacanciesController.addVacancy(new Gson().toJson(tokenTransferEmployer));

        Skill skill = new Skill("C++", 5);
        VacancyRequirementTransfer vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(),
                new Gson().toJson(vacancyRequirementTransfer),
                VacancyRequirementTransfer.class.getName(),
                null);
        VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransferEmployer));


        tokenTransferEmployer = addEmployer("oleg", "rivalGame");

        employer = new Gson().fromJson(tokenTransferEmployer.getJson(), Employer.class);
        vacancy = new Vacancy("FrontEnd developer", 75000, employer.getCompanyName());

        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);
        VacanciesController.addVacancy(new Gson().toJson(tokenTransferEmployer));

        skill = new Skill("HTML", 4);
        vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(),
                new Gson().toJson(vacancyRequirementTransfer),
                VacancyRequirementTransfer.class.getName(),
                null);
        VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransferEmployer));

        skill = new Skill("CSS", 4);
        vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(),
                new Gson().toJson(vacancyRequirementTransfer),
                VacancyRequirementTransfer.class.getName(),
                null);
        VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransferEmployer));

        skill = new Skill("JavaScript", 3);
        vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, false);
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(),
                new Gson().toJson(vacancyRequirementTransfer),
                VacancyRequirementTransfer.class.getName(),
                null);
        VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransferEmployer));


        tokenTransferEmployer = addEmployer("sanya323", "CustomServices");

        employer = new Gson().fromJson(tokenTransferEmployer.getJson(), Employer.class);
        vacancy = new Vacancy("FrontEnd developer", 75000, employer.getCompanyName());

        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);
        VacanciesController.addVacancy(new Gson().toJson(tokenTransferEmployer));

        skill = new Skill("HTML", 5);
        vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(),
                new Gson().toJson(vacancyRequirementTransfer),
                VacancyRequirementTransfer.class.getName(),
                null);
        VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransferEmployer));

        skill = new Skill("CSS", 5);
        vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(),
                new Gson().toJson(vacancyRequirementTransfer),
                VacancyRequirementTransfer.class.getName(),
                null);
        VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransferEmployer));
    }

    private TokenTransfer registreWorker(int cPlusPlus, int html, int css) {
        UserRegister user1 = new UserRegister("a@mail.ru",
                "Vladislav",
                "Korzhuk",
                "Victorovich",
                "vlad456",
                "1234",
                false);
        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user1)), TokenTransfer.class);


        Skill skill = new Skill("C++", cPlusPlus);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        skill = new Skill("HTML", html);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        skill = new Skill("CSS", css);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        return new TokenTransfer(tokenTransfer.getToken(), null, null, null);
    }

    @Test
    public void testGetVacanciesWithAllSkills() {
        registerVacancyWithRequirements();

        TokenTransfer tokenTransfer = registreWorker(3,4,4);

        String response = WorkerController.getVacanciesWithAllSkills(new Gson().toJson(tokenTransfer));
        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        HashSet<Vacancy> vacancies = new Gson().fromJson(tokenTransfer.getJson(),
                new TypeToken<HashSet<Vacancy>>() {
                }.getType());


        assertEquals(0, vacancies.size());
    }

    @Test
    public void getVacanciesWithNecessarySkills() {
        registerVacancyWithRequirements();

        TokenTransfer tokenTransfer = registreWorker(3,4,4);

        String response = WorkerController.getVacanciesWithNecessarySkills(new Gson().toJson(tokenTransfer));
        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        HashSet<Vacancy> vacancies = new Gson().fromJson(tokenTransfer.getJson(),
                new TypeToken<HashSet<Vacancy>>() {
                }.getType());

        assertEquals(1, vacancies.size());
        assertEquals(true, vacancies.stream().anyMatch(s -> s.getPosition().equals("FrontEnd developer") && s.getEmployer().equals("rivalGame")));
    }

    @Test
    public void getVacanciesWithAnyLevelSkills() {
        registerVacancyWithRequirements();

        TokenTransfer tokenTransfer = registreWorker(3,4,4);

        String response = WorkerController.getVacanciesWithAnyLevelSkills(new Gson().toJson(tokenTransfer));
        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        HashSet<Vacancy> vacancies = new Gson().fromJson(tokenTransfer.getJson(),
                new TypeToken<HashSet<Vacancy>>() {
                }.getType());

        assertEquals(2, vacancies.size());
        assertEquals(true, vacancies.stream().anyMatch(s -> s.getPosition().equals("C++ developer") && s.getEmployer().equals("rivalGame")));
        assertEquals(true, vacancies.stream().anyMatch(s -> s.getPosition().equals("FrontEnd developer") && s.getEmployer().equals("CustomServices")));
    }

    @Test
    public void getVacanciesWithOneSkills() {
        registerVacancyWithRequirements();

        TokenTransfer tokenTransfer = registreWorker(5,4,4);

        String response = WorkerController.getVacanciesWithOneSkills(new Gson().toJson(tokenTransfer));
        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        LinkedHashSet<Vacancy> vacancies = new Gson().fromJson(tokenTransfer.getJson(),
                new TypeToken<LinkedHashSet<Vacancy>>() {
                }.getType());

        assertEquals(2, vacancies.size());

        int count = 0;
        for(Vacancy vacancy: vacancies){
            if(count == 0) {
                assertEquals("FrontEnd developer", vacancy.getPosition());
                assertEquals("rivalGame", vacancy.getEmployer());
                count++;
            } else {
                assertEquals("C++ developer", vacancy.getPosition());
                assertEquals("rivalGame", vacancy.getEmployer());
            }
        }
    }
}
