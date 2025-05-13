package net.brotherband.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chats {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "chat_name")
    private String chatName;
    
    @Column(name = "is_group_chat")
    private boolean isGroupChat;
    
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "chat_participants",
        joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
)
    @Builder.Default
    private Set<Users> participants = new HashSet<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Messages> messages = new HashSet<>();


     // ---------------------- GETTERS ----------------------

    public UUID getId() {
        return id;
    }

    public String getChatName() {
        return chatName;
    }

    public boolean isGroupChat() {
        return isGroupChat;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Set<Users> getParticipants() {
        return participants;
    }

    public Set<Messages> getMessages() {
        return messages;
    }

    // ---------------------- SETTERS ----------------------

    public void setId(UUID id) {
        this.id = id;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setGroupChat(boolean groupChat) {
        isGroupChat = groupChat;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setParticipants(Set<Users> participants) {
        this.participants = participants;
    }

    public void setMessages(Set<Messages> messages) {
        this.messages = messages;
    }
}