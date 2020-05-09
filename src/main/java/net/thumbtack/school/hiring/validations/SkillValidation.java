package net.thumbtack.school.hiring.validations;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.Controllers.DataBase;
import net.thumbtack.school.hiring.errors.ServerError;
import net.thumbtack.school.hiring.errors.ServerException;
import net.thumbtack.school.hiring.models.Skill;
import net.thumbtack.school.hiring.models.TokenTransfer;

import java.lang.reflect.Type;


public class SkillValidation {
    public static Skill defaultSkillValidation(TokenTransfer tokenTransfer) throws ServerException {
        UserValidation.tokenValidation(tokenTransfer);

        Skill skill = new Gson().fromJson(tokenTransfer.getJson(), Skill.class);

        return skillValidation(skill);
    }

    public static Skill skillValidation(Skill skill) throws ServerException {
        if (skill == null || skill.getName() == null || (skill.getLevel() < 1) || (skill.getLevel() > 5))
            throw new ServerException(ServerError.SKILL_VALIDATION_IS_NOT_SUCCESSFUL);

        return skill;
    }
}
