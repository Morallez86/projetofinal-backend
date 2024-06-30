package aor.paj.projetofinalbackend.bean;

import aor.paj.projetofinalbackend.entity.ResourceEntity;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;


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


    // Runs every minute at the start of the minute. Deactivates tokens that are over the stipulated active time.
    @Schedule(second = "0", minute = "*", hour = "*", persistent = false)
    public void automaticTimer() {
        tokenBean.removeExpiredTokens();
        userBean.removeEmailToken();
    }

    @Schedule(second = "0", minute = "0", hour = "0", persistent = false)
    public void checkExpiringResources() {
        List<ResourceEntity> expiringResources = resourceBean.findResourcesExpiringWithinWeek();

        for (ResourceEntity resource : expiringResources) {
            notificationBean.sendExpirationNotifications(resource);
        }
    }
}
