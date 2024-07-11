package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.*;
import aor.paj.projetofinalbackend.entity.WorkplaceEntity;
import aor.paj.projetofinalbackend.utils.Role;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import aor.paj.projetofinalbackend.utils.EncryptHelper;
import aor.paj.projetofinalbackend.entity.UserEntity;
import org.apache.logging.log4j.*;

import java.time.LocalDateTime;

/**
 * Singleton bean responsible for initializing essential data during application startup.
 * This includes creating a default admin user and predefined workplace entities.
 * @see UserDao
 * @see WorkplaceDao
 *
 * Authors: João Morais, Ricardo Elias
 */
@Singleton
@Startup
public class StartupBean {
    private static final Logger logger = LogManager.getLogger(StartupBean.class);

    @Inject
    private UserDao userDao;

    @Inject
    private WorkplaceDao workplaceDao;

    /**
     * Initializes the application during startup.
     * Creates a default admin user if no users exist in the database.
     * Creates predefined workplace entities if no workplaces exist in the database.
     */
    @PostConstruct
    public void init() {
        try {
            if (userDao.countTotalUsers() == 0) {
                createDefaultAdminUser();
            }

            if (workplaceDao.countTotalWorkplaces() == 0) {
                createDefaultWorkplaces();
            }

        } catch (Exception e) {
            logger.error("Initialization failed: " + e.getMessage(), e);
        }
    }

    /**
     * Creates a default admin user with predefined properties.
     * @throws IllegalStateException if the DEFAULT_ADMIN_PASSWORD environment variable is not set.
     */
    private void createDefaultAdminUser() {
        String defaultPassword = System.getenv("DEFAULT_ADMIN_PASSWORD");
        if (defaultPassword == null) {
            throw new IllegalStateException("DEFAULT_ADMIN_PASSWORD environment variable not set");
        }

        UserEntity admin = new UserEntity();
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setUsername("admin");
        admin.setPassword(EncryptHelper.encryptPassword(defaultPassword));
        admin.setEmail("admin@example.com");
        admin.setRole(Role.ADMIN);
        admin.setActive(true);
        admin.setPending(false);
        admin.setRegistTime(LocalDateTime.now());
        admin.setVisibility(true);
        admin.setActiveProject(false);
        admin.setBiography("The GOAT");

        userDao.persist(admin);
        logger.info("Default admin user created.");
    }

    /**
     * Creates predefined workplace entities.
     */
    private void createDefaultWorkplaces() {
        persistWorkplace("Lisbon");
        persistWorkplace("Porto");
        persistWorkplace("Coimbra");
        persistWorkplace("Tomar");
        persistWorkplace("Viseu");
        persistWorkplace("Vila Real");

        logger.info("Default workplaces created.");
    }

    /**
     * Persists a workplace entity with the given name.
     * @param name The name of the workplace.
     */
    private void persistWorkplace(String name) {
        WorkplaceEntity workplace = new WorkplaceEntity();
        workplace.setName(name);
        workplaceDao.persist(workplace);
    }
}
