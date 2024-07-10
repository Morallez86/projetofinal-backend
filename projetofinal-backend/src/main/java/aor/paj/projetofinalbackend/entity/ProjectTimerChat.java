package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Embeddable class representing timestamps associated with a project's chat.
 *
 * @author João Morais
 * @author Ricardo Elias
 */
@Embeddable
public class ProjectTimerChat implements Serializable {

    @ElementCollection
    @Column(name = "timestamp")
    private List<LocalDateTime> timestamps;

    /**
     * Default constructor for ProjectTimerChat.
     */
    public ProjectTimerChat() {
    }

    /**
     * Constructor for ProjectTimerChat with parameters.
     *
     * @param projectId   The ID of the project associated with the timestamps.
     * @param timestamps  The list of timestamps related to the project's chat.
     * @param userId      The ID of the user associated with the timestamps.
     */
    public ProjectTimerChat(Long projectId, List<LocalDateTime> timestamps, Long userId) {
        this.timestamps = timestamps;
    }

    /**
     * Get the list of timestamps associated with the project's chat.
     *
     * @return The list of timestamps.
     */
    public List<LocalDateTime> getTimestamps() {
        return timestamps;
    }

    /**
     * Set the list of timestamps associated with the project's chat.
     *
     * @param timestamps The list of timestamps to set.
     */
    public void setTimestamps(List<LocalDateTime> timestamps) {
        this.timestamps = timestamps;
    }

    /**
     * Retrieve the most recent timestamp in the list.
     *
     * @return The most recent timestamp, or null if the list is empty.
     */
    public LocalDateTime getMostRecentTimestamp() {
        if (timestamps != null && !timestamps.isEmpty()) {
            return timestamps.get(timestamps.size() - 1); // Last timestamp in the list, which is the most recent
        }
        return null; // Return null if there are no timestamps or timestamps are null
    }
}
