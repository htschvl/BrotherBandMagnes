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

@Document(collection = "bb_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BBRequest {

    @Id
    private UUID id;

    @Field("sender_id")
    @Indexed // Indexado para consultas por remetente
    private UUID senderId;

    @Field("sender_name")
    private String senderName;

    @Field("sender_username")
    private String senderUsername;

    @Field("receiver_id")
    @Indexed // Indexado para consultas por destinatário
    private UUID receiverId;

    @Field("receiver_name")
    private String receiverName;

    @Field("receiver_username")
    private String receiverUsername;

    @Field("sent_date")
    private LocalDateTime sentDate;

    @Field("status")
    @Builder.Default
    private RequestStatus status = RequestStatus.PENDING;

    @Field("response_date")
    private LocalDateTime responseDate;

    @Field("resulting_bond_id")
    private UUID resultingBondId;

    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    /**
     * Aceita esta solicitação
     * 
     * @param bondId ID do vínculo resultante
     * @return O status atualizado
     */
    public RequestStatus accept(UUID bondId) {
        this.status = RequestStatus.ACCEPTED;
        this.responseDate = LocalDateTime.now();
        this.resultingBondId = bondId;
        return this.status;
    }

    /**
     * Rejeita esta solicitação
     * 
     * @return O status atualizado
     */
    public RequestStatus reject() {
        this.status = RequestStatus.REJECTED;
        this.responseDate = LocalDateTime.now();
        return this.status;
    }

    public boolean isPending() {
        return status == RequestStatus.PENDING;
    }

    // ---------------------- GETTERS ----------------------
    
    public UUID getId() {
        return id;
    }
    
    public UUID getSenderId() {
        return senderId;
    }
    
    public String getSenderName() {
        return senderName;
    }
    
    public String getSenderUsername() {
        return senderUsername;
    }
    
    public UUID getReceiverId() {
        return receiverId;
    }
    
    public String getReceiverName() {
        return receiverName;
    }
    
    public String getReceiverUsername() {
        return receiverUsername;
    }
    
    public LocalDateTime getSentDate() {
        return sentDate;
    }
    
    public RequestStatus getStatus() {
        return status;
    }
    
    public LocalDateTime getResponseDate() {
        return responseDate;
    }
    
    public UUID getResultingBondId() {
        return resultingBondId;
    }
    
    // ---------------------- SETTERS ----------------------
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }
    
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    
    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }
    
    public void setReceiverId(UUID receiverId) {
        this.receiverId = receiverId;
    }
    
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    
    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }
    
    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }
    
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
    
    public void setResponseDate(LocalDateTime responseDate) {
        this.responseDate = responseDate;
    }
    
    public void setResultingBondId(UUID resultingBondId) {
        this.resultingBondId = resultingBondId;
    }
}