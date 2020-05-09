package net.thumbtack.school.hiring.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.thumbtack.school.hiring.Controllers.*;
import net.thumbtack.school.hiring.errors.ServerError;
import net.thumbtack.school.hiring.models.*;
import net.thumbtack.school.hiring.server.Server;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestVacanciesController {

    @Before
    public void resetDataBase() {
        Server.startServer(null);
        DataBase.getDataBase().resetDataBase();
    }

    private TokenTransfer addEmployer() {
        UserRegister user = new UserRegister("a@mail.ru",
                "Petya",
                "Vasechkin",
                "Dmitrievich",
                "Petya777",
                "1234",
                true);

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user)), TokenTransfer.class);

        String response = UserController.getUser(new Gson().toJson(tokenTransfer));

        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        employer.setCompanyName("Picachu");

        response = UserController.edit(new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(employer), employer.getClass().getName(), null)));

        response = UserController.getUser(response);

        return new Gson().fromJson(response, TokenTransfer.class);
    }

    private HashSet<Vacancy> addVacancy(TokenTransfer tokenTransfer) {
        tokenTransfer = new Gson().fromJson(VacanciesController.addVacancy(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        tokenTransfer = new Gson().fromJson(VacanciesController.getVacancies(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        return new Gson().fromJson(tokenTransfer.getJson(), new TypeToken<HashSet<Vacancy>>() {
        }.getType());
    }

    private HashSet<Vacancy> editVacancy(TokenTransfer tokenTransfer) {
        tokenTransfer = new Gson().fromJson(VacanciesController.editVacancy(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        tokenTransfer = new Gson().fromJson(VacanciesController.getVacancies(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        return new Gson().fromJson(tokenTransfer.getJson(), new TypeToken<HashSet<Vacancy>>() {
        }.getType());
    }

    private HashSet<Vacancy> removeVacancy(TokenTransfer tokenTransfer) {
        tokenTransfer = new Gson().fromJson(VacanciesController.removeVacancy(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        tokenTransfer = new Gson().fromJson(VacanciesController.getVacancies(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        return new Gson().fromJson(tokenTransfer.getJson(), new TypeToken<HashSet<Vacancy>>() {
        }.getType());
    }

    @Test
    public void testAddVacancy() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        HashSet<Vacancy> employerVacancy = addVacancy(tokenTransfer);

        assertEquals(true, employerVacancy.contains(vacancy));
    }

    @Test
    public void testAddIncorrectVacancy() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("Java developer", 75000, null);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        tokenTransfer = new Gson().fromJson(VacanciesController.addVacancy(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        assertEquals(ServerError.VACANCY_VALIDATION_IS_NOT_SUCCESSFUL.getErrorString(), tokenTransfer.getError());

        vacancy = new Vacancy(null, 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        tokenTransfer = new Gson().fromJson(VacanciesController.addVacancy(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        assertEquals(ServerError.VACANCY_VALIDATION_IS_NOT_SUCCESSFUL.getErrorString(), tokenTransfer.getError());
    }

    @Test
    public void testEditVacancy() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);

        vacancy.setPosition("new Java");

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        HashSet<Vacancy> employerVacancy = editVacancy(tokenTransfer);

        assertEquals(true, employerVacancy.contains(vacancy));

    }

    @Test
    public void testEditNotExistVacancy() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        tokenTransfer = new Gson().fromJson(VacanciesController.editVacancy(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        assertEquals(ServerError.VACANCY_IS_NOT_EXIST.getErrorString(), tokenTransfer.getError());
    }

    @Test
    public void testRemoveVacancy() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);

        HashSet<Vacancy> employerVacancy = removeVacancy(tokenTransfer);

        assertEquals(false, employerVacancy.contains(vacancy));
    }

    @Test
    public void testRemoveNotExistVacancy() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        tokenTransfer = new Gson().fromJson(VacanciesController.removeVacancy(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        assertEquals(ServerError.VACANCY_IS_NOT_EXIST.getErrorString(), tokenTransfer.getError());
    }

    @Test
    public void testSetNonActiveAndActiveVacancy() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        VacanciesController.addVacancy(new Gson().toJson(tokenTransfer));

        VacanciesController.setNonActiveVacancy(new Gson().toJson(tokenTransfer));

        tokenTransfer = new Gson().fromJson(VacanciesController.getVacancies(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        HashSet<Vacancy> vacancies = new Gson().fromJson(tokenTransfer.getJson(), new TypeToken<HashSet<Vacancy>>() {
        }.getType());

        assertEquals(false, vacancies.contains(vacancy));

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        VacanciesController.setActiveVacancy(new Gson().toJson(tokenTransfer));

        tokenTransfer = new Gson().fromJson(VacanciesController.getVacancies(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        vacancies = new Gson().fromJson(tokenTransfer.getJson(), new TypeToken<HashSet<Vacancy>>() {
        }.getType());

        assertEquals(true, vacancies.contains(vacancy));
    }

    @Test
    public void testAddVacancyRequirement() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);


        Skill skill = new Skill("Java", 5);

        VacancyRequirementTransfer vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancyRequirementTransfer), VacancyRequirementTransfer.class.getName(), null);

        VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransfer));


        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        String request = new Gson().toJson(tokenTransfer);

        tokenTransfer = new Gson().fromJson(VacanciesController.getVacancyRequirements(request), TokenTransfer.class);


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(new TypeToken<HashMap<Skill, Boolean>>() {
        }.getType(), new CustomConverterHashMapSkillBoolean());

        Gson gson = builder.create();

        HashMap<Skill, Boolean> vacancyRequirements = gson.fromJson(tokenTransfer.getJson(), new TypeToken<HashMap<Skill, Boolean>>() {
        }.getType());

        assertEquals(true, vacancyRequirements.keySet().contains(skill));
    }

    @Test
    public void testAddIncorrectVacancyRequirement() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);


        Skill skill = new Skill("Java", 7);

        VacancyRequirementTransfer vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancyRequirementTransfer), VacancyRequirementTransfer.class.getName(), null);

        String response = VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransfer));


        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        assertEquals(ServerError.SKILL_VALIDATION_IS_NOT_SUCCESSFUL.getErrorString(), tokenTransfer.getError());
    }

    @Test
    public void testEditVacancyRequirement() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);


        Skill skill = new Skill("Java", 1);

        VacancyRequirementTransfer vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancyRequirementTransfer), VacancyRequirementTransfer.class.getName(), null);

        VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransfer));


        skill.setLevel(5);


        vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancyRequirementTransfer), VacancyRequirementTransfer.class.getName(), null);

        VacanciesController.editVacancyRequirement(new Gson().toJson(tokenTransfer));


        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        String request = new Gson().toJson(tokenTransfer);

        tokenTransfer = new Gson().fromJson(VacanciesController.getVacancyRequirements(request), TokenTransfer.class);


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(new TypeToken<HashMap<Skill, Boolean>>() {
        }.getType(), new CustomConverterHashMapSkillBoolean());

        Gson gson = builder.create();

        HashMap<Skill, Boolean> vacancyRequirements = gson.fromJson(tokenTransfer.getJson(), new TypeToken<HashMap<Skill, Boolean>>() {
        }.getType());

        for (Skill vacancyRequirementSkill : vacancyRequirements.keySet()) {
            if (vacancyRequirementSkill.getName().equals(skill.getName())) {
                assertEquals(skill.getLevel(), vacancyRequirementSkill.getLevel());
                return;
            }
        }
        fail();
    }

    @Test
    public void testEditNotExistVacancyRequirement() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);


        Skill skill = new Skill("Java", 1);

        VacancyRequirementTransfer vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancyRequirementTransfer), VacancyRequirementTransfer.class.getName(), null);

        String response = VacanciesController.editVacancyRequirement(new Gson().toJson(tokenTransfer));


        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        assertEquals(ServerError.VACANCY_REQUIREMENT_IS_NOT_EXIST.getErrorString(), tokenTransfer.getError());
    }

    @Test
    public void testRemoveVacancyRequirement() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);


        Skill skill = new Skill("Java", 5);

        VacancyRequirementTransfer vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancyRequirementTransfer), VacancyRequirementTransfer.class.getName(), null);

        VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransfer));


        VacanciesController.removeVacancyRequirement(new Gson().toJson(tokenTransfer));


        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        String request = new Gson().toJson(tokenTransfer);

        tokenTransfer = new Gson().fromJson(VacanciesController.getVacancyRequirements(request), TokenTransfer.class);


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(new TypeToken<HashMap<Skill, Boolean>>() {
        }.getType(), new CustomConverterHashMapSkillBoolean());

        Gson gson = builder.create();

        HashMap<Skill, Boolean> vacancyRequirements = gson.fromJson(tokenTransfer.getJson(), new TypeToken<HashMap<Skill, Boolean>>() {
        }.getType());

        assertEquals(0, vacancyRequirements.size());
    }

    @Test
    public void testRemoveNotExistVacancyRequirement() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);


        Skill skill = new Skill("Java", 5);

        VacancyRequirementTransfer vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancyRequirementTransfer), VacancyRequirementTransfer.class.getName(), null);

        String response = VacanciesController.removeVacancyRequirement(new Gson().toJson(tokenTransfer));

        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        assertEquals(ServerError.VACANCY_REQUIREMENT_IS_NOT_EXIST.getErrorString(), tokenTransfer.getError());
    }


    @Test
    public void testGetAllEmployerVacancies() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy1 = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy1), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);

        VacanciesController.setNonActiveVacancy(new Gson().toJson(tokenTransfer));

        Vacancy vacancy2 = new Vacancy("PHP developer", 45000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy2), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);

        Vacancy vacancy3 = new Vacancy("JavaScript developer", 70000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy3), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), null, null, null);

        tokenTransfer = new Gson().fromJson(VacanciesController.getEmployerVacancies(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        HashSet<Vacancy> allVacancies = new Gson().fromJson(tokenTransfer.getJson(), new TypeToken<HashSet<Vacancy>>() {
        }.getType());

        assertEquals(3, allVacancies.size());
    }

    @Test
    public void testGetActiveEmployerVacancies() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy1 = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy1), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);

        VacanciesController.setNonActiveVacancy(new Gson().toJson(tokenTransfer));

        Vacancy vacancy2 = new Vacancy("PHP developer", 45000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy2), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);

        Vacancy vacancy3 = new Vacancy("JavaScript developer", 70000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy3), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), null, null, null);

        tokenTransfer = new Gson().fromJson(VacanciesController.getActiveEmployerVacancies(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        HashSet<Vacancy> activeVacancies = new Gson().fromJson(tokenTransfer.getJson(), new TypeToken<HashSet<Vacancy>>() {
        }.getType());

        assertEquals(2, activeVacancies.size());
        assertEquals(true, activeVacancies.contains(vacancy2));
        assertEquals(true, activeVacancies.contains(vacancy3));
    }

    @Test
    public void testGetNonActiveEmployerVacancies() {
        TokenTransfer tokenTransfer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);

        Vacancy vacancy1 = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy1), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);

        VacanciesController.setNonActiveVacancy(new Gson().toJson(tokenTransfer));

        Vacancy vacancy2 = new Vacancy("PHP developer", 45000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy2), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);

        Vacancy vacancy3 = new Vacancy("JavaScript developer", 70000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy3), Vacancy.class.getName(), null);

        addVacancy(tokenTransfer);

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), null, null, null);

        tokenTransfer = new Gson().fromJson(VacanciesController.getNonActiveEmployerVacancies(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        HashSet<Vacancy> nonActiveVacancies = new Gson().fromJson(tokenTransfer.getJson(), new TypeToken<HashSet<Vacancy>>() {
        }.getType());

        assertEquals(1, nonActiveVacancies.size());
        assertEquals(true, nonActiveVacancies.contains(vacancy1));
    }

    private Vacancy registerVacancy(TokenTransfer tokenTransferEmployer) {
        Employer employer = new Gson().fromJson(tokenTransferEmployer.getJson(), Employer.class);

        Vacancy vacancy = new Vacancy("BackEnd developer", 80000, employer.getCompanyName());
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);
        addVacancy(tokenTransferEmployer);

        Skill skill = new Skill("Java", 4);
        VacancyRequirementTransfer vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(), new Gson().toJson(vacancyRequirementTransfer), VacancyRequirementTransfer.class.getName(), null);
        VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransferEmployer));

        skill = new Skill("PHP", 3);
        vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(), new Gson().toJson(vacancyRequirementTransfer), VacancyRequirementTransfer.class.getName(), null);
        VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransferEmployer));

        skill = new Skill("JavaScript", 4);
        vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, false);
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(), new Gson().toJson(vacancyRequirementTransfer), VacancyRequirementTransfer.class.getName(), null);
        VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransferEmployer));

        return vacancy;
    }

    private void registerWorkers() {
        TokenTransfer tokenTransfer;

        UserRegister user1 = new UserRegister("a@mail.ru",
                "Vladislav",
                "Korzhuk",
                "Victorovich",
                "vlad456",
                "1234",
                false);
        tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user1)), TokenTransfer.class);


        Skill skill = new Skill("Java", 5);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        skill = new Skill("JavaScript", 4);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        UserRegister user2 = new UserRegister("a@mail.ru",
                "Andrey",
                "Yurin",
                "Nikolaevich",
                "andry",
                "1234",
                false);
        tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user2)), TokenTransfer.class);


        skill = new Skill("Java", 3);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        skill = new Skill("JavaScript", 4);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        skill = new Skill("PHP", 5);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        UserRegister user3 = new UserRegister("a@mail.ru",
                "admin",
                "secret",
                "secret",
                "admin",
                "1234",
                false);
        tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user3)), TokenTransfer.class);


        skill = new Skill("Java", 5);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        skill = new Skill("JavaScript", 5);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        skill = new Skill("PHP", 5);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));
    }

    @Test
    public void testGetWorkersWithAllSkill() {
        TokenTransfer tokenTransferEmployer = addEmployer();

        Vacancy vacancy = registerVacancy(tokenTransferEmployer);

        registerWorkers();

        HashSet<WorkerView> workerViews;
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);
        String response = VacanciesController.getWorkersWithAllSkills(new Gson().toJson(tokenTransferEmployer));
        tokenTransferEmployer = new Gson().fromJson(response, TokenTransfer.class);
        workerViews = new Gson().fromJson(tokenTransferEmployer.getJson(),
                new TypeToken<HashSet<WorkerView>>() {
                }.getType());

        assertEquals(1, workerViews.size());

        WorkerView workerView = new WorkerView("a@mail.ru",
                "admin",
                "secret",
                "secret",
                "admin");

        assertEquals(true, workerViews.contains(workerView));
    }

    @Test
    public void testGetWorkersWithNecessarySkills() {
        TokenTransfer tokenTransferEmployer = addEmployer();

        Vacancy vacancy = registerVacancy(tokenTransferEmployer);

        registerWorkers();

        HashSet<WorkerView> workerViews;
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);
        String response = VacanciesController.getWorkersWithNecessarySkills(new Gson().toJson(tokenTransferEmployer));
        tokenTransferEmployer = new Gson().fromJson(response, TokenTransfer.class);
        workerViews = new Gson().fromJson(tokenTransferEmployer.getJson(),
                new TypeToken<HashSet<WorkerView>>() {
                }.getType());

        assertEquals(1, workerViews.size());

        WorkerView workerView = new WorkerView("a@mail.ru",
                "admin",
                "secret",
                "secret",
                "admin");

        assertEquals(true, workerViews.contains(workerView));
    }

    @Test
    public void testGetWorkersWithAnyLevelSkills() {
        TokenTransfer tokenTransferEmployer = addEmployer();

        Vacancy vacancy = registerVacancy(tokenTransferEmployer);

        registerWorkers();

        HashSet<WorkerView> workerViews;
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);
        String response = VacanciesController.getWorkersWithAnyLevelSkills(new Gson().toJson(tokenTransferEmployer));
        tokenTransferEmployer = new Gson().fromJson(response, TokenTransfer.class);
        workerViews = new Gson().fromJson(tokenTransferEmployer.getJson(),
                new TypeToken<HashSet<WorkerView>>() {
                }.getType());

        assertEquals(2, workerViews.size());

        WorkerView workerView1 = new WorkerView("a@mail.ru",
                "admin",
                "secret",
                "secret",
                "admin");
        WorkerView workerView2 = new WorkerView("a@mail.ru",
                "Andrey",
                "Yurin",
                "Nikolaevich",
                "andry");

        assertEquals(true, workerViews.contains(workerView1));
        assertEquals(true, workerViews.contains(workerView2));
    }

    @Test
    public void testGetWorkersWithOneSkills() {
        TokenTransfer tokenTransferEmployer = addEmployer();

        Vacancy vacancy = registerVacancy(tokenTransferEmployer);

        registerWorkers();

        HashSet<WorkerView> workerViews;
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);
        String response = VacanciesController.getWorkersWithOneSkills(new Gson().toJson(tokenTransferEmployer));
        tokenTransferEmployer = new Gson().fromJson(response, TokenTransfer.class);
        workerViews = new Gson().fromJson(tokenTransferEmployer.getJson(),
                new TypeToken<HashSet<WorkerView>>() {
                }.getType());

        assertEquals(3, workerViews.size());

        WorkerView workerView1 = new WorkerView("a@mail.ru",
                "admin",
                "secret",
                "secret",
                "admin");
        WorkerView workerView2 = new WorkerView("a@mail.ru",
                "Andrey",
                "Yurin",
                "Nikolaevich",
                "andry");
        WorkerView workerView3 = new WorkerView("a@mail.ru",
                "Vladislav",
                "Korzhuk",
                "Victorovich",
                "vlad456");

        assertEquals(true, workerViews.contains(workerView1));
        assertEquals(true, workerViews.contains(workerView2));
        assertEquals(true, workerViews.contains(workerView3));
    }

    @Test
    public void testGetWorkerViewSkills() {
        TokenTransfer tokenTransferEmployer = addEmployer();

        UserRegister user3 = new UserRegister("a@mail.ru",
                "admin",
                "secret",
                "secret",
                "admin",
                "1234",
                false);

        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user3)), TokenTransfer.class);

        Skill skill = new Skill("Java", 5);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        skill = new Skill("JavaScript", 5);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        skill = new Skill("PHP", 5);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(skill), Skill.class.getName(), null);
        WorkerController.addSkill(new Gson().toJson(tokenTransfer));

        WorkerView workerView = new WorkerView("a@mail.ru",
                "admin",
                "secret",
                "secret",
                "admin");

        tokenTransfer = new TokenTransfer(tokenTransferEmployer.getToken(), new Gson().toJson(workerView), WorkerView.class.getName(), null);
        String response = VacanciesController.getWorkerViewSkills(new Gson().toJson(tokenTransfer));

        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);
        HashMap<String, Integer> workerViewSkills = new Gson().fromJson(tokenTransfer.getJson(),
                new TypeToken<HashMap<String, Integer>>() {
                }.getType());

        assertEquals(true, workerViewSkills.containsKey("Java"));
        assertEquals(true, workerViewSkills.containsKey("JavaScript"));
        assertEquals(true, workerViewSkills.containsKey("PHP"));
    }

    @Test
    public void testGetAllSkills() {
        TokenTransfer tokenTransfer = addEmployer();
        registerWorkers();

        String response = VacanciesController.getAllSkills(new Gson().toJson(tokenTransfer));
        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);

        HashSet<Skill> skills = new Gson().fromJson(tokenTransfer.getJson(),
                new TypeToken<HashSet<Skill>>() {
                }.getType());

        assertEquals(3, skills.size());
        assertTrue(skills.contains(new Skill("Java", 5)));
        assertTrue(skills.contains(new Skill("JavaScript", 4)));
        assertTrue(skills.contains(new Skill("PHP", 5)));
    }

    @Test
    public void testHireAnWorker() {
        TokenTransfer tokenTransferEmployer = addEmployer();

        Employer employer = new Gson().fromJson(tokenTransferEmployer.getJson(), Employer.class);
        Vacancy vacancy = new Vacancy("BackEnd developer", 80000, employer.getCompanyName());
        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);
        addVacancy(tokenTransferEmployer);

        UserRegister user1 = new UserRegister("a@mail.ru",
                "Vladislav",
                "Korzhuk",
                "Victorovich",
                "vlad456",
                "1234",
                false);
        TokenTransfer tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user1)), TokenTransfer.class);

        tokenTransfer = new Gson().fromJson(UserController.getUser(new Gson().toJson(tokenTransfer)), TokenTransfer.class);
        Worker worker = new Gson().fromJson(tokenTransfer.getJson(), Worker.class);

        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(),
                new Gson().toJson(new HireWorkerTransfer(worker, vacancy)),
                HireWorkerTransfer.class.getName(),
                null);

        VacanciesController.hireAnWorker(new Gson().toJson(tokenTransferEmployer));

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), null, null, null);
        tokenTransfer = new Gson().fromJson(UserController.getUser(new Gson().toJson(tokenTransfer)), TokenTransfer.class);
        worker = new Gson().fromJson(tokenTransfer.getJson(), Worker.class);

        assertEquals(false, worker.isActive());

        tokenTransferEmployer = new TokenTransfer(tokenTransferEmployer.getToken(), null, null, null);

        String response = VacanciesController.getVacancies(new Gson().toJson(tokenTransferEmployer));
        tokenTransferEmployer = new Gson().fromJson(response, TokenTransfer.class);

        HashSet<Vacancy> vacancies = new Gson().fromJson(tokenTransferEmployer.getJson(),
                new TypeToken<HashSet<Vacancy>>() {
                }.getType());

        assertEquals(0,vacancies.size());
    }
}
