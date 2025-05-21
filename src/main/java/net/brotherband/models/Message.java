package net.brotherband.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    
    @Id
    private UUID id;
    
    @Field("chat_id")
    @Indexed // Indexar para consultas rápidas por chatId
    private UUID chatId;
    
    @Field("sender_id")
    private UUID senderId;
    
    @Field("content")
    private String content;
    
    @Field("sent_time")
    @Indexed // Indexar para ordenação e consultas por data
    private LocalDateTime sentTime;
    
    @Field("is_read")
    private boolean isRead;
    
    @Field("reply_to")
    private UUID replyTo;  // ID da mensagem à qual esta responde (null se não for resposta)
    
    // Campos opcionais para resposta embutida (caso necessário referenciar diretamente)
    @Field("reply_data")
    private ReplyData replyData;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ReplyData {
        private UUID repliedMessageId;
        private String repliedContent;
        private UUID repliedSenderId;
        private LocalDateTime repliedSentTime;
        
        // ---------------------- GETTERS ----------------------
        
        public UUID getRepliedMessageId() {
            return repliedMessageId;
        }
        
        public String getRepliedContent() {
            return repliedContent;
        }
        
        public UUID getRepliedSenderId() {
            return repliedSenderId;
        }
        
        public LocalDateTime getRepliedSentTime() {
            return repliedSentTime;
        }
        
        // ---------------------- SETTERS ----------------------
        
        public void setRepliedMessageId(UUID repliedMessageId) {
            this.repliedMessageId = repliedMessageId;
        }
        
        public void setRepliedContent(String repliedContent) {
            this.repliedContent = repliedContent;
        }
        
        public void setRepliedSenderId(UUID repliedSenderId) {
            this.repliedSenderId = repliedSenderId;
        }
        
        public void setRepliedSentTime(LocalDateTime repliedSentTime) {
            this.repliedSentTime = repliedSentTime;
        }
    }
    
    /**
     * Cria os dados da resposta baseado em uma mensagem existente
     * @param repliedMessage A mensagem que está sendo respondida
     */
    public void setReplyDataFromMessage(Message repliedMessage) {
        if (repliedMessage != null) {
            this.replyTo = repliedMessage.getId();
            this.replyData = ReplyData.builder()
                    .repliedMessageId(repliedMessage.getId())
                    .repliedContent(repliedMessage.getContent())
                    .repliedSenderId(repliedMessage.getSenderId())
                    .repliedSentTime(repliedMessage.getSentTime())
                    .build();
        }
    }
    
    /**
     * Converte esta mensagem para um formato embutido para armazenamento no documento Chat
     * @return Uma versão embutida desta mensagem
     */
    public Chat.EmbeddedMessage toEmbeddedMessage() {
        return Chat.EmbeddedMessage.builder()
                .messageId(this.id)
                .senderId(this.senderId)
                .content(this.content)
                .sentTime(this.sentTime)
                .isRead(this.isRead)
                .replyTo(this.replyTo)
                .build();
    }
    
    // ---------------------- GETTERS ----------------------
    
    public UUID getId() {
        return id;
    }
    
    public UUID getChatId() {
        return chatId;
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
    
    public ReplyData getReplyData() {
        return replyData;
    }
    
    // ---------------------- SETTERS ----------------------
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public void setChatId(UUID chatId) {
        this.chatId = chatId;
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
    
    public void setReplyData(ReplyData replyData) {
        this.replyData = replyData;
    }
}