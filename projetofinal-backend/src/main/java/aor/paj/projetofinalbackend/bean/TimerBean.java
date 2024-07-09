package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.dao.ProjectDao;
import aor.paj.projetofinalbackend.dao.ProjectHistoryDao;
import aor.paj.projetofinalbackend.dao.TaskDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.entity.ResourceEntity;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

import java.util.List;

/**
 * Singleton bean responsible for scheduling tasks at specified intervals using EJB Schedule annotations.
 * @see TokenBean
 * @see ResourceBean
 * @see NotificationBean
 * @see UserBean
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Singleton
public class TimerBean {

    @Inject
    TokenBean tokenBean;

    @Inject
    ResourceBean resourceBean;

    @Inject
    NotificationBean notificationBean;

    @Inject
    UserBean userBean;


    /**
     * Automatically runs every minute at the start of the minute. Deactivates tokens that are over the stipulated active time.
     */
    @Schedule(second = "0", minute = "*", hour = "*", persistent = false)
    public void automaticTimer() {
        tokenBean.removeExpiredTokens();
        userBean.removeEmailToken();
    }

    /**
     * Automatically runs at midnight (00:00) every day. Checks for resources expiring within the next week and sends expiration notifications.
     */
    @Schedule(second = "0", minute = "0", hour = "0", persistent = false)
    public void checkExpiringResources() {
        List<ResourceEntity> expiringResources = resourceBean.findResourcesExpiringWithinWeek();

        for (ResourceEntity resource : expiringResources) {
            notificationBean.sendExpirationNotifications(resource);
        }
    }
}
