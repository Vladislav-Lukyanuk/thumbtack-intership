package net.thumbtack.school.hiring.models;

import java.util.HashMap;

public class VacancyRequirementTransfer {
    private Vacancy vacancy;
    private Skill skill;
    private boolean necessary;

    public VacancyRequirementTransfer(Vacancy vacancy, Skill skill, boolean necessary) {
        this.vacancy = vacancy;
        this.skill = skill;
        this.necessary = necessary;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public Skill getSkill() {
        return skill;
    }

    public boolean getNecessary() {
        return necessary;
    }
}
