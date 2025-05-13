package net.brotherband.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    
    @Column(name = "registered_in", nullable = false)
    private LocalDateTime registeredIn;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Bonds> brothers = new HashSet<>();

    @OneToMany(mappedBy = "brother", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Bonds> brotherOf = new HashSet<>();

    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Chats> chats = new HashSet<>();

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Messages> sentMessages = new HashSet<>();

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<BBRequest> sentBBRequests = new HashSet<>();

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<BBRequest> receivedBBRequests = new HashSet<>();


    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDateTime getRegisteredIn() {
        return registeredIn;
    }

    public Set<Bonds> getBrothers() {
        return brothers;
    }

    public Set<Bonds> getBrotherOf() {
        return brotherOf;
    }

    public Set<Chats> getChats() {
        return chats;
    }

    public Set<Messages> getSentMessages() {
        return sentMessages;
    }

    public Set<BBRequest> getSentBBRequests() {
        return sentBBRequests;
    }

    public Set<BBRequest> getReceivedBBRequests() {
        return receivedBBRequests;
    }

    // ---------------------- SETTERS ----------------------

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setRegisteredIn(LocalDateTime registeredIn) {
        this.registeredIn = registeredIn;
    }

    public void setBrothers(Set<Bonds> brothers) {
        this.brothers = brothers;
    }

    public void setBrotherOf(Set<Bonds> brotherOf) {
        this.brotherOf = brotherOf;
    }

    public void setChats(Set<Chats> chats) {
        this.chats = chats;
    }

    public void setSentMessages(Set<Messages> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public void setSentBBRequests(Set<BBRequest> sentBBRequests) {
        this.sentBBRequests = sentBBRequests;
    }

    public void setReceivedBBRequests(Set<BBRequest> receivedBBRequests) {
        this.receivedBBRequests = receivedBBRequests;
    }
}