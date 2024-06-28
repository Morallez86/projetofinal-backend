package aor.paj.projetofinalbackend.bean;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

import java.time.LocalDateTime;


@Singleton
public class TimerBean {

    @Inject
    TokenBean tokenBean;

    @Inject
    UserBean userBean;


    // Runs every minute at the start of the minute
    @Schedule(second = "0", minute = "*", hour = "*", persistent = false)
    public void automaticTimer() {
        tokenBean.removeExpiredTokens();
        userBean.removeEmailToken();
    }
}
