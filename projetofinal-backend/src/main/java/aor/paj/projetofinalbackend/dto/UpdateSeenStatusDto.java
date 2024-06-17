package aor.paj.projetofinalbackend.dto;

import java.util.List;

public class UpdateSeenStatusDto {
    private List<Long> messageIds;
    private boolean seen;

    // Getters and Setters
    public List<Long> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(List<Long> messageIds) {
        this.messageIds = messageIds;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}

