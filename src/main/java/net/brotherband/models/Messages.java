package net.brotherband.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Messages {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chats chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Users sender;

    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "sent_time", nullable = false)
    private LocalDateTime sentTime;
    
    @Column(name = "is_read")
    @Builder.Default
    private boolean isRead = false;

     // ---------------------- GETTERS ----------------------

    public UUID getId() {
        return id;
    }

    public Chats getChat() {
        return chat;
    }

    public Users getSender() {
        return sender;
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

    public void setChat(Chats chat) {
        this.chat = chat;
    }

    public void setSender(Users sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}