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
public class ChatSummaryDTO {
    private UUID id;
    private String chatName;
    private boolean isGroupChat;
    private LocalDateTime createdDate;
    private long participantCount;
    private long messageCount;
    private MessagePreviewDTO lastMessage;

}
