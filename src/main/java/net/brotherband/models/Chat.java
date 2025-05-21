package net.brotherband.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "chats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    
    @Id
    private UUID id;
    
    @Field("chat_name")
    private String chatName;
    
    @Field("is_group_chat")
    private boolean isGroupChat;
    
    @Field("created_date")
    private LocalDateTime createdDate;
    
    @Field("participants")
    @Builder.Default
    private List<ChatParticipant> participants = new ArrayList<>();
    
    @Field("recent_messages")
    @Builder.Default
    private List<EmbeddedMessage> recentMessages = new ArrayList<>();
    
    @Field("message_count")
    private long messageCount;
    
    @Field("last_activity")
    private LocalDateTime lastActivity;
    
    @Field("unread_count")
    @Builder.Default
    private Map<String, Integer> unreadCount = new HashMap<>();
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChatParticipant {
        private UUID userId;
        private String username;
        private String name;
        
        // ---------------------- GETTERS ----------------------
        
        public UUID getUserId() {
            return userId;
        }
        
        public String getUsername() {
            return username;
        }
        
        public String getName() {
            return name;
        }
        
        // ---------------------- SETTERS ----------------------
        
        public void setUserId(UUID userId) {
            this.userId = userId;
        }
        
        public void setUsername(String username) {
            this.username = username;
        }
        
        public void setName(String name) {
            this.name = name;
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EmbeddedMessage {
        private UUID messageId;
        private UUID senderId;
        private String content;
        private LocalDateTime sentTime;
        private boolean isRead;
        private UUID replyTo;  // ID da mensagem à qual esta responde (null se não for resposta)
        
        // ---------------------- GETTERS ----------------------
        
        public UUID getMessageId() {
            return messageId;
        }
        
        public UUID getSenderId() {
            return senderId;
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
        
        public UUID getReplyTo() {
            return replyTo;
        }
        
        // ---------------------- SETTERS ----------------------
        
        public void setMessageId(UUID messageId) {
            this.messageId = messageId;
        }
        
        public void setSenderId(UUID senderId) {
            this.senderId = senderId;
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
        
        public void setReplyTo(UUID replyTo) {
            this.replyTo = replyTo;
        }
    }
    
    public void addParticipant(ChatParticipant participant) {
        if (participants == null) {
            participants = new ArrayList<>();
        }
        participants.add(participant);

        if (unreadCount == null) {
            unreadCount = new HashMap<>();
        }

        // Convert UUID to String for map key compatibility
        String userIdStr = participant.getUserId().toString();

        // Insert only if not already present
        if (!unreadCount.containsKey(userIdStr)) {
            unreadCount.put(userIdStr, 0);
        }
    }

    public void addRecentMessage(EmbeddedMessage message) {
        if (recentMessages == null) {
            recentMessages = new ArrayList<>();
        }

        // Limita a lista de mensagens a 20
        if (recentMessages.size() >= 10) {
            recentMessages.remove(0);
        }

        recentMessages.add(message);
        lastActivity = message.getSentTime();
        messageCount++;

        // Incrementa contador de não lidas para todos, exceto o remetente
        UUID senderId = message.getSenderId();
        for (ChatParticipant participant : participants) {
            UUID participantId = participant.getUserId();

            if (!participantId.equals(senderId)) {
                // Convert UUID to string to match map key type
                String participantIdKey = participantId.toString();
                int currentCount = unreadCount.getOrDefault(participantIdKey, 0);
                unreadCount.put(participantIdKey, currentCount + 1);
            }
        }
    }

    public void markAsReadForUser(UUID userId) {
        String key = userId.toString(); // Mongo-safe map key
        unreadCount.put(key, 0);

        if (recentMessages != null) {
            for (EmbeddedMessage message : recentMessages) {
                if (!message.getSenderId().equals(userId)) {
                    message.setRead(true);
                }
            }
        }
    }
    
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
    
    public List<ChatParticipant> getParticipants() {
        return participants;
    }
    
    public List<EmbeddedMessage> getRecentMessages() {
        return recentMessages;
    }
    
    public long getMessageCount() {
        return messageCount;
    }
    
    public LocalDateTime getLastActivity() {
        return lastActivity;
    }
    
    public Map<String, Integer> getUnreadCount() {
        return unreadCount;
    }
    
    // ---------------------- SETTERS ----------------------
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
    
    public void setGroupChat(boolean isGroupChat) {
        this.isGroupChat = isGroupChat;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    public void setParticipants(List<ChatParticipant> participants) {
        this.participants = participants;
    }
    
    public void setRecentMessages(List<EmbeddedMessage> recentMessages) {
        this.recentMessages = recentMessages;
    }
    
    public void setMessageCount(long messageCount) {
        this.messageCount = messageCount;
    }
    
    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }
    
    public void setUnreadCount(Map<String, Integer> unreadCount) {
        this.unreadCount = unreadCount;
    }
}