package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.SkillEntity;
import jakarta.ejb.Stateless;

import java.util.Collections;
import java.util.List;

@Stateless
public class SkillDao extends AbstractDao<SkillEntity> {
    private static final long serialVersionUID = 1L;

    public SkillDao() {super(SkillEntity.class);}

    public List<SkillEntity> findAllSkills() {
        try {
            return em.createNamedQuery("Skill.findAllSkills", SkillEntity.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
