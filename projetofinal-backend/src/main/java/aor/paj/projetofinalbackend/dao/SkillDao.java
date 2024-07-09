package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.SkillEntity;
import jakarta.ejb.Stateless;

import java.util.List;

/**
 * Data Access Object (DAO) for managing SkillEntity entities.
 * Provides methods to perform CRUD operations and retrieve skills from the database.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Stateless
public class SkillDao extends TagDao<SkillEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the SkillDao initializing with SkillEntity class.
     */
    public SkillDao() {
        super(SkillEntity.class);
    }

    /**
     * Retrieves all skills stored in the database.
     *
     * @return A list of all SkillEntity objects representing skills.
     */
    public List<SkillEntity> findAllSkills() {
        return super.findAllAttributes("Skill.findAllSkills");
    }

    /**
     * Retrieves a skill entity by its name.
     *
     * @param name The name of the skill to retrieve.
     * @return The SkillEntity object representing the skill with the specified name, or null if not found.
     */
    public SkillEntity findByName(String name) {
        return super.findByName("Skill.findByName", name);
    }

    /**
     * Retrieves a skill entity by its ID.
     *
     * @param id The ID of the skill to retrieve.
     * @return The SkillEntity object representing the skill with the specified ID, or null if not found.
     */
    public SkillEntity findById(Long id) {
        return super.findById("Skill.findSkillById", id);
    }

    /**
     * Retrieves multiple skill entities by their IDs.
     *
     * @param ids The list of IDs of the skills to retrieve.
     * @return A list of SkillEntity objects representing the skills with the specified IDs.
     */
    public List<SkillEntity> findAllById(List<Long> ids) {
        return em.createNamedQuery("Skill.findAllById", SkillEntity.class)
                .setParameter("ids", ids)
                .getResultList();
    }
}
