package net.thumbtack.school.hiring.controllers;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.Controllers.*;
import net.thumbtack.school.hiring.models.*;
import net.thumbtack.school.hiring.server.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import static org.junit.Assert.assertEquals;

public class TestDataBase {

    @Before
    public void resetDataBase() {
        Server.startServer(null);
        DataBase.getDataBase().resetDataBase();
    }

    @Test
    public void testSaveAndLoadDataBase() {
        File file = new File("save.db");

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

        user = new UserRegister("a@mail.ru",
                "Petya",
                "Vasechkin",
                "Dmitrievich",
                "Petya7777",
                "1234",
                true);

        tokenTransfer = new Gson().fromJson(UserController.register(new Gson().toJson(user)), TokenTransfer.class);

        String response = UserController.getUser(new Gson().toJson(tokenTransfer));
        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);
        Employer employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);
        employer.setCompanyName("Picachu");

        response = UserController.edit(new Gson().toJson(new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(employer), employer.getClass().getName(), null)));

        response = UserController.getUser(response);

        tokenTransfer = new Gson().fromJson(response, TokenTransfer.class);
        employer = new Gson().fromJson(tokenTransfer.getJson(), Employer.class);
        Vacancy vacancy = new Vacancy("Java developer", 75000, employer.getCompanyName());

        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancy), Vacancy.class.getName(), null);
        tokenTransfer = new Gson().fromJson(VacanciesController.addVacancy(new Gson().toJson(tokenTransfer)), TokenTransfer.class);

        skill = new Skill("Java", 5);
        VacancyRequirementTransfer vacancyRequirementTransfer = new VacancyRequirementTransfer(vacancy, skill, true);
        tokenTransfer = new TokenTransfer(tokenTransfer.getToken(), new Gson().toJson(vacancyRequirementTransfer), VacancyRequirementTransfer.class.getName(), null);
        VacanciesController.addVacancyRequirement(new Gson().toJson(tokenTransfer));

        Server.stopServer(file.getPath());

        DataBase.getDataBase().resetDataBase();

        Server.startServer(file.getPath());

        assertEquals(1, DataBase.getDataBase().getWorkers().size());
        assertEquals(1,DataBase.getDataBase().getEmployers().size());
        assertEquals(1, DataBase.getDataBase().getAllSkills().size());
        assertEquals(1,DataBase.getDataBase().getEmployersVacancies().size());
        assertEquals(1,DataBase.getDataBase().getWorkersSkills().size());
        assertEquals(1, DataBase.getDataBase().getVacancyRequirements().size());

        file.delete();
    }
}
