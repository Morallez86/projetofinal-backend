package aor.paj.projetofinalbackend.dto;

import java.time.LocalDateTime;

public class ChatMessageDto {
    private Long id;

    private LocalDateTime timestamp;

    private String content;

    private UserDto sender;

    public ChatMessageDto() {
    }



    public Long getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    public UserDto getSender() {
        return sender;
    }
}
