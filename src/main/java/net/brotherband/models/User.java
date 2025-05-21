package net.brotherband.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class User {
    
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

    // SQL relationship - remain unchanged
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Bond> brothers = new HashSet<>();

    // SQL relationship - remain unchanged
    @OneToMany(mappedBy = "brother", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Bond> brotherOf = new HashSet<>();

    // MongoDB relationships - these fields are used to store IDs only, not actual references
    @Column(name = "chat_ids")
    @Builder.Default
    private Set<UUID> chatIds = new HashSet<>();
    
    @Column(name = "sent_message_ids")
    @Builder.Default
    private Set<UUID> sentMessageIds = new HashSet<>();
    
    @Column(name = "sent_bb_request_ids")
    @Builder.Default
    private Set<UUID> sentBBRequestIds = new HashSet<>();
    
    @Column(name = "received_bb_request_ids")
    @Builder.Default
    private Set<UUID> receivedBBRequestIds = new HashSet<>();

    /**
     * Adds a chat ID to the user's chat list
     * @param chatId the chat ID to add
     * @return true if the ID was added, false if it was already present
     */
    public boolean addChatId(UUID chatId) {
        return chatIds.add(chatId);
    }
    
    /**
     * Adds a message ID to the user's sent messages
     * @param messageId the message ID to add
     * @return true if the ID was added, false if it was already present
     */
    public boolean addSentMessageId(UUID messageId) {
        return sentMessageIds.add(messageId);
    }
    
    /**
     * Adds a BBRequest ID to the user's sent requests
     * @param requestId the request ID to add
     * @return true if the ID was added, false if it was already present
     */
    public boolean addSentBBRequestId(UUID requestId) {
        return sentBBRequestIds.add(requestId);
    }
    
    /**
     * Adds a BBRequest ID to the user's received requests
     * @param requestId the request ID to add
     * @return true if the ID was added, false if it was already present
     */
    public boolean addReceivedBBRequestId(UUID requestId) {
        return receivedBBRequestIds.add(requestId);
    }
    
    /**
     * Removes a BBRequest ID from the user's sent requests
     * @param requestId the request ID to remove
     * @return true if the ID was removed, false if it wasn't present
     */
    public boolean removeSentBBRequestId(UUID requestId) {
        return sentBBRequestIds.remove(requestId);
    }
    
    /**
     * Removes a BBRequest ID from the user's received requests
     * @param requestId the request ID to remove
     * @return true if the ID was removed, false if it wasn't present
     */
    public boolean removeReceivedBBRequestId(UUID requestId) {
        return receivedBBRequestIds.remove(requestId);
    }

    /**
     * Converts the user's UUID to a string ID for MongoDB references
     * @return the string representation of the user's UUID
     */
    public String getStringId() {
        return id.toString();
    }

    // Standard getters and setters

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

    public Set<Bond> getBrothers() {
        return brothers;
    }

    public Set<Bond> getBrotherOf() {
        return brotherOf;
    }

    public Set<UUID> getChatIds() {
        return chatIds;
    }

    public Set<UUID> getSentMessageIds() {
        return sentMessageIds;
    }

    public Set<UUID> getSentBBRequestIds() {
        return sentBBRequestIds;
    }

    public Set<UUID> getReceivedBBRequestIds() {
        return receivedBBRequestIds;
    }

    // Setters

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

    public void setBrothers(Set<Bond> brothers) {
        this.brothers = brothers;
    }

    public void setBrotherOf(Set<Bond> brotherOf) {
        this.brotherOf = brotherOf;
    }

    public void setChatIds(Set<UUID> chatIds) {
        this.chatIds = chatIds;
    }

    public void setSentMessageIds(Set<UUID> sentMessageIds) {
        this.sentMessageIds = sentMessageIds;
    }

    public void setSentBBRequestIds(Set<UUID> sentBBRequestIds) {
        this.sentBBRequestIds = sentBBRequestIds;
    }

    public void setReceivedBBRequestIds(Set<UUID> receivedBBRequestIds) {
        this.receivedBBRequestIds = receivedBBRequestIds;
    }
}