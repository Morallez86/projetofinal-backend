package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.SkillDao;
import aor.paj.projetofinalbackend.dto.SkillDto;
import aor.paj.projetofinalbackend.entity.SkillEntity;
import aor.paj.projetofinalbackend.mapper.SkillMapper;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SkillBean {

    @EJB
    SkillDao skillDao;

    public List<SkillDto> getAllSkills() {
        try {
            List<SkillEntity> skillEntities = skillDao.findAllSkills();
            return skillEntities.stream()
                    .map(SkillMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

}
