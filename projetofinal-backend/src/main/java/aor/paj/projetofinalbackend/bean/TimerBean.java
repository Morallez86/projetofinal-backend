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


    @Schedule(second="*/60", minute="*", hour="*")
    public void automaticTimer(){
        tokenBean.removeExpiredTokens();
    }
}
