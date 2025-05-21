package net.brotherband.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private UUID id;
    private UUID senderId;
    private String senderUsername;
    private String content;
    private LocalDateTime sentTime;
    private boolean isRead;
    private UUID replyTo;
}
