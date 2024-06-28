package aor.paj.projetofinalbackend.dao;

import aor.paj.projetofinalbackend.entity.UserNotificationEntity;
import jakarta.ejb.Stateless;

@Stateless
public class UserNotificationDao extends AbstractDao<UserNotificationEntity> {

    private static final long serialVersionUID = 1L;

    public UserNotificationDao() {
        super(UserNotificationEntity.class);
    }
}
