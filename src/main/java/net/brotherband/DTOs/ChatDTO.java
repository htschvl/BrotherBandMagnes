package net.brotherband.DTOs;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {
    private UUID id;
    private String chatName;
    private boolean isGroupChat;
    private LocalDateTime createdDate;
    private LocalDateTime lastActivity;
    private long messageCount;
    private int participantCount;
    private List<ChatParticipantDTO> participants;
    private List<MessageDTO> recentMessages;

}
