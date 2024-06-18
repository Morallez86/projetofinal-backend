package aor.paj.projetofinalbackend.dto;

import java.util.List;

public class UpdateSeenStatusDto {
    private List<Long> messageOrNotificationIds;
    private boolean seen;

    // Getters and Setters
    public List<Long> getMessageOrNotificationIds() {
        return messageOrNotificationIds;
    }

    public void setMessageOrNotificationIds(List<Long> messageIds) {
        this.messageOrNotificationIds = messageIds;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}

