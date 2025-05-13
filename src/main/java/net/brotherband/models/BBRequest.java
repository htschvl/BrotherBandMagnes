package net.brotherband.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BB_requests")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BBRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Users sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Users receiver;

    
    @Column(name = "sent_date", nullable = false)
    private LocalDateTime sentDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Builder.Default
    private RequestStatus status = RequestStatus.PENDING;
    
    // Reference to resulting bond if accepted
    @OneToOne(mappedBy = "originatingRequest", fetch = FetchType.LAZY)
    private Bonds resultingBond;
    
    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }


    // ---------------------- GETTERS ----------------------

    public UUID getId() {
        return id;
    }

    public Users getSender() {
        return sender;
    }

    public Users getReceiver() {
        return receiver;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public Bonds getResultingBond() {
        return resultingBond;
    }

    // ---------------------- SETTERS ----------------------

    public void setId(UUID id) {
        this.id = id;
    }

    public void setSender(Users sender) {
        this.sender = sender;
    }

    public void setReceiver(Users receiver) {
        this.receiver = receiver;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public void setResultingBond(Bonds resultingBond) {
        this.resultingBond = resultingBond;
    }
}