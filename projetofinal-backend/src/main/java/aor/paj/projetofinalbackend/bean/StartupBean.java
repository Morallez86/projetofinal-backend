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
 * @author João Morais
 * @author Ricardo Elias
 */
@Singleton
@Startup
public class StartupBean {
    private static final Logger logger = LogManager.getLogger(StartupBean.class);

    @Inject
    private UserDao userdao;

    @Inject
    private WorkplaceDao workplaceDao;

    /**
     * Initializes the application during startup.
     * Creates a default admin user if no users exist in the database.
     * Creates predefined workplace entities.
     */
    @PostConstruct
    public void init() {
        try {
            if (userdao.countTotalUsers() == 0) {
                // Retrieve the default password from environment variable
                String defaultPassword = System.getenv("DEFAULT_ADMIN_PASSWORD");
                if (defaultPassword == null) {
                    throw new IllegalStateException("DEFAULT_ADMIN_PASSWORD environment variable not set");
                }

                // Create default admin user
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

                userdao.persist(admin);
                logger.info("Default admin user created.");

            }
            WorkplaceEntity lisbon = new WorkplaceEntity();
            lisbon.setName("Lisbon");
            workplaceDao.persist(lisbon);
            WorkplaceEntity porto = new WorkplaceEntity();
            porto.setName("Porto");
            workplaceDao.persist(porto);
            WorkplaceEntity coimbra = new WorkplaceEntity();
            coimbra.setName("Coimbra");
            workplaceDao.persist(coimbra);
            WorkplaceEntity tomar = new WorkplaceEntity();
            tomar.setName("Tomar");
            workplaceDao.persist(tomar);
            WorkplaceEntity viseu = new WorkplaceEntity();
            viseu.setName("Viseu");
            workplaceDao.persist(viseu);
            WorkplaceEntity vilaReal = new WorkplaceEntity();
            vilaReal.setName("Vila Real");
            workplaceDao.persist(vilaReal);
        } catch (Exception e) {
            logger.error("Failed to create default admin user: " + e.getMessage());
        }
    }
}
