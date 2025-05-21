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
public class MessagePreviewDTO {
    private UUID id;
    private UUID senderId;
    private String senderUsername;
    private String content;
    private LocalDateTime sentTime;
    private boolean isRead;
    
    // ---------------------- GETTERS ----------------------
    
    public UUID getId() {
        return id;
    }
    
    public UUID getSenderId() {
        return senderId;
    }
    
    public String getSenderUsername() {
        return senderUsername;
    }
    
    public String getContent() {
        return content;
    }
    
    public LocalDateTime getSentTime() {
        return sentTime;
    }
    
    public boolean isRead() {
        return isRead;
    }
    
    // ---------------------- SETTERS ----------------------
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }
    
    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }
    
    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}