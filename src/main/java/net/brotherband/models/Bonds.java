package net.brotherband.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bonds")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bonds {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brother_id", nullable = false)
    private Users brother;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private BBRequest originatingRequest;
    
    @Column(name = "established_date", nullable = false)
    private LocalDateTime establishedDate;



    // ---------------------- GETTERS ----------------------

    public UUID getId() {
        return id;
    }

    public Users getUser() {
        return user;
    }

    public Users getBrother() {
        return brother;
    }

    public LocalDateTime getEstablishedDate() {
        return establishedDate;
    }

    public BBRequest getOriginatingRequest() {
        return originatingRequest;
    }

    // ---------------------- SETTERS ----------------------

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setBrother(Users brother) {
        this.brother = brother;
    }

    public void setEstablishedDate(LocalDateTime establishedDate) {
        this.establishedDate = establishedDate;
    }

    public void setOriginatingRequest(BBRequest originatingRequest) {
        this.originatingRequest = originatingRequest;
    }
}