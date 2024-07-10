package aor.paj.projetofinalbackend.dto;

import java.util.List;

/**
 * UpdateSeenStatusDto is a Data Transfer Object (DTO) class used for updating the 'seen' status of messages or notifications.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
public class UpdateSeenStatusDto {
    private List<Long> messageOrNotificationIds;
    private boolean seen;

    /**
     * Retrieves the list of message or notification IDs to update.
     *
     * @return the list of message or notification IDs.
     */
    public List<Long> getMessageOrNotificationIds() {
        return messageOrNotificationIds;
    }

    /**
     * Sets the list of message or notification IDs to update.
     *
     * @param messageOrNotificationIds the list of message or notification IDs to set.
     */
    public void setMessageOrNotificationIds(List<Long> messageOrNotificationIds) {
        this.messageOrNotificationIds = messageOrNotificationIds;
    }

    /**
     * Retrieves the new 'seen' status.
     *
     * @return true if the messages or notifications should be marked as seen, false otherwise.
     */
    public boolean isSeen() {
        return seen;
    }

    /**
     * Sets the new 'seen' status.
     *
     * @param seen the boolean value indicating whether the messages or notifications should be marked as seen.
     */
    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    /**
     * Returns the string representation of this object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "UpdateSeenStatusDto{" +
                "messageOrNotificationIds=" + messageOrNotificationIds +
                ", seen=" + seen +
                '}';
    }
}
