package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.InterestEntity;
import aor.paj.projetofinalbackend.entity.SkillEntity;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class InterestDao extends TagDao<InterestEntity> {

    private static final long serialVersionUID = 1L;

    public InterestDao() {
        super(InterestEntity.class);
    }

    public List<InterestEntity> findAllInterests() {
        return super.findAllAttributes("Interest.findAllInterests");
    }

    public InterestEntity findByName(String name) {
        return super.findByName("Interest.findByName", name);
    }

    public InterestEntity findById(Long id) {
        return super.findById("Interest.findInterestById", id);
    }

    public List<InterestEntity> findAllById(List<Long> ids) {
        return em.createNamedQuery("Interest.findAllById", InterestEntity.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}
