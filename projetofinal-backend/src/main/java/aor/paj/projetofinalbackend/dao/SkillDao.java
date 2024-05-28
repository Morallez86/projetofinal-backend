package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.SkillEntity;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class SkillDao extends TagDao<SkillEntity> {

    private static final long serialVersionUID = 1L;

    public SkillDao() {
        super(SkillEntity.class);
    }

    public List<SkillEntity> findAllSkills() {
        return super.findAllAttributes("Skill.findAllSkills");
    }

    public SkillEntity findByName(String name) {
        return super.findByName("Skill.findByName", name);
    }

    public SkillEntity findById(Long id) {
        return super.findById("Skill.findSkillById", id);
    }
}
