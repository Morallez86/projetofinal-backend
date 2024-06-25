package aor.paj.projetofinalbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Embeddable
public class ProjectTimerChat implements Serializable {

    @ElementCollection
    @Column(name = "timestamp")
    private List<LocalDateTime> timestamps;


    public ProjectTimerChat() {
    }

    public ProjectTimerChat(Long projectId, List<LocalDateTime> timestamps, Long userId) {
        this.timestamps = timestamps;

    }

    public List<LocalDateTime> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(List<LocalDateTime> timestamps) {
        this.timestamps = timestamps;
    }
    
    public LocalDateTime getMostRecentTimestamp() {
        if (timestamps != null && !timestamps.isEmpty()) {
            return timestamps.get(timestamps.size() - 1); // Último timestamp na lista, que é o mais recente
        }
        return null; // Retornar null se não houver timestamps ou timestamps forem nulos
    }
}
