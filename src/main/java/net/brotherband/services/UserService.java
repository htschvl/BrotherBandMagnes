package net.brotherband.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.brotherband.DTOs.*;
import net.brotherband.exceptions.ResourceNotFoundException;
import net.brotherband.exceptions.UsernameAlreadyExistsException;
import net.brotherband.models.BBRequest;
import net.brotherband.models.Chat;
import net.brotherband.models.Message;
import net.brotherband.models.User;
import net.brotherband.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MeDTO getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = findUserByUsername(username);

        // Handle DOB, birthday and age
        LocalDate dob = user.getDateOfBirth();
        MonthDay birthday = dob != null ? MonthDay.from(dob) : null;
        int age = dob != null ? Period.between(dob, LocalDate.now()).getYears() : 0;

        // Build up to 5 brothers only
        List<BrotherSummaryDTO> brothersList = user.getBrothers().stream()
        .limit(5)
        .map(bond -> BrotherSummaryDTO.builder()
                .id(bond.getBrother().getId())
                .username(bond.getBrother().getUsername())
                .name(bond.getBrother().getName())
                .bondEstablishedDate(bond.getEstablishedDate())
                .build())
        .collect(Collectors.toList());


        // Need to inject BBRequestService and use it here
        List<RequestSummaryDTO> sentRequestsList = user.getSentBBRequestIds().stream()
                .<RequestSummaryDTO>map(requestId -> {
                    try {
                        // This should be replaced with actual BBRequestService call
                        BBRequest request = getBBRequestById(requestId);
                        return RequestSummaryDTO.builder()
                                .id(request.getId())
                                .userId(request.getReceiverId()) // Fixed: was request.getReceiver().getId()
                                .username(request.getReceiverUsername()) // Fixed: was request.getReceiver().getUsername()
                                .name(request.getReceiverName()) // Fixed: was request.getReceiver().getName()
                                .sentDate(request.getSentDate())
                                .build();
                    } catch (Exception e) {
                        // Log error and return null, which will be filtered out below
                        System.err.println("Error fetching BBRequest with ID: " + requestId);
                        return null;
                    }
                })
                .filter(dto -> dto != null) // Remove any nulls from failed lookups
                .collect(Collectors.toList());

        // Need to inject BBRequestService and use it here
        List<RequestSummaryDTO> receivedRequestsList = user.getReceivedBBRequestIds().stream()
                .<RequestSummaryDTO>map(requestId -> {
                    try {
                        // This should be replaced with actual BBRequestService call
                        BBRequest request = getBBRequestById(requestId);
                        return RequestSummaryDTO.builder()
                                .id(request.getId())
                                .userId(request.getSenderId()) // Fixed: was request.getSender().getId()
                                .username(request.getSenderUsername()) // Fixed: was request.getSender().getUsername()
                                .name(request.getSenderName()) // Fixed: was request.getSender().getName()
                                .sentDate(request.getSentDate())
                                .build();
                    } catch (Exception e) {
                        // Log error and return null, which will be filtered out below
                        System.err.println("Error fetching BBRequest with ID: " + requestId);
                        return null;
                    }
                })
                .filter(dto -> dto != null) // Remove any nulls from failed lookups
                .collect(Collectors.toList());

        // Assuming we'll filter by status elsewhere, since the User model doesn't have direct access to BBRequests
        int pendingSentRequestsCount = sentRequestsList.size();
        int pendingReceivedRequestsCount = receivedRequestsList.size();

        List<ChatSummaryDTO> chatsList = user.getChatIds().stream()
                .<ChatSummaryDTO>map(chatId -> {
                    try {
                        // This should be replaced with actual ChatService call
                        Chat chat = getChatById(chatId);
                        
                        // Fixed: Use recentMessages instead of getMessages()
                        Message lastMessage = null;
                        if (chat.getRecentMessages() != null && !chat.getRecentMessages().isEmpty()) {
                            // Find the most recent message from the list
                            Chat.EmbeddedMessage lastEmbeddedMsg = chat.getRecentMessages().stream()
                                    .max(Comparator.comparing(Chat.EmbeddedMessage::getSentTime))
                                    .orElse(null);
                                    
                            if (lastEmbeddedMsg != null) {
                                // Convert the embedded message to a full message for DTO mapping
                                lastMessage = new Message();
                                lastMessage.setId(lastEmbeddedMsg.getMessageId());
                                lastMessage.setSenderId(lastEmbeddedMsg.getSenderId());
                                lastMessage.setContent(lastEmbeddedMsg.getContent());
                                lastMessage.setSentTime(lastEmbeddedMsg.getSentTime());
                                lastMessage.setRead(lastEmbeddedMsg.isRead());
                            }
                        }

                        MessagePreviewDTO lastMsg = null;
                        if (lastMessage != null) {
                            // Fixed: Direct field access or use proper getters
                            UUID senderId = lastMessage.getSenderId();
                            String senderUsername = getUsernameById(senderId);  // Need to implement this method
                            
                            lastMsg = MessagePreviewDTO.builder()
                                    .id(lastMessage.getId())
                                    .senderId(senderId)
                                    .senderUsername(senderUsername)
                                    .content(lastMessage.getContent().length() > 50
                                            ? lastMessage.getContent().substring(0, 50) + "..."
                                            : lastMessage.getContent())
                                    .sentTime(lastMessage.getSentTime())
                                    .isRead(lastMessage.isRead())
                                    .build();
                        }

                        return ChatSummaryDTO.builder()
                                .id(chat.getId())
                                .chatName(chat.getChatName())
                                .isGroupChat(chat.isGroupChat())
                                .createdDate(chat.getCreatedDate())
                                .participantCount(chat.getParticipants().size())
                                .messageCount(chat.getMessageCount()) // Fixed: Using messageCount property
                                .lastMessage(lastMsg)
                                .build();
                    } catch (Exception e) {
                        // Log error and return null, which will be filtered out below
                        System.err.println("Error fetching Chat with ID: " + chatId);
                        return null;
                    }
                })
                .filter(dto -> dto != null) // Remove any nulls from failed lookups
                .collect(Collectors.toList());

        return MeDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .dateOfBirth(dob)
                .birthday(birthday)
                .age(age)
                .registeredIn(user.getRegisteredIn())
                .brothersCount(user.getBrothers().size())
                .brothers(brothersList)
                .pendingSentRequestsCount(pendingSentRequestsCount)
                .pendingReceivedRequestsCount(pendingReceivedRequestsCount)
                .sentRequests(sentRequestsList)
                .receivedRequests(receivedRequestsList)
                .chatCount(chatsList.size())
                .chats(chatsList)
                .build();
    }
    
    public GenericUserDTO register(UserRegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        
        // Create and save the new user
        User user = User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .name(registerDTO.getName())
                .dateOfBirth(registerDTO.getDateOfBirth())
                .registeredIn(LocalDateTime.now())
                .build();
        
        User savedUser = userRepository.save(user);
        
        return mapToUserDTO(savedUser);
    }
    
        public GenericUserDTO findGenericById(UUID id) {
        User user = getUserOrThrow(id);
        return mapToUserDTO(user);
        }

        public ProfileDTO findProfileById(UUID id) {
        User user = getUserOrThrow(id);
        return mapToProfileDTO(user);
        }

        public StrangerDTO findStrangerById(UUID id) {
        User user = getUserOrThrow(id);
        return mapToStrangerDTO(user);
        }

        private User getUserOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        }
    
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }
    


    
    public ProfileDTO getProfileByUsername(String username) {
        User user = findUserByUsername(username);

        
        
        // Get the brothers (established bonds)
        List<BrotherSummaryDTO> brothersList = user.getBrothers().stream()
        .<BrotherSummaryDTO>map(bond -> BrotherSummaryDTO.builder()
                .id(bond.getBrother().getId())
                .username(bond.getBrother().getUsername())
                .name(bond.getBrother().getName())
                .bondEstablishedDate(bond.getEstablishedDate())
                .build())
        .collect(Collectors.toList());

        
        // Note: We can't directly count pending requests as in the original code
        // since we only have IDs, not the actual BBRequest objects
        int brothersCount = user.getBrothers().size();
        
        return ProfileDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .age(calculateAge(user.getDateOfBirth()))
                .birthdayMonthYear(formatMonthYear(user.getDateOfBirth()))
                .registeredIn(user.getRegisteredIn())
                .numberOfBrothers(brothersCount)
                .brothers(brothersList)
                .build();
    }


    public StrangerDTO getStrangerByUsername(String username) {
    User user = findUserByUsername(username);
    return mapToStrangerDTO(user);
}
    /**
     * Helper method to get a username by user ID.
     * @param userId The ID of the user
     * @return The username
     */
    private String getUsernameById(UUID userId) {
        return userRepository.findById(userId)
            .map(User::getUsername)
            .orElse("Unknown User");
    }
    
    private GenericUserDTO mapToUserDTO(User user) {
        return GenericUserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .dateOfBirth(user.getDateOfBirth())
                .registeredIn(user.getRegisteredIn())
                .build();
    }

    private ProfileDTO mapToProfileDTO(User user) {
        return ProfileDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .age(calculateAge(user.getDateOfBirth()))
                .birthdayMonthYear(formatMonthYear(user.getDateOfBirth()))
                .registeredIn(user.getRegisteredIn())
                .numberOfBrothers(user.getBrothers().size())
                .build();
    }

    private StrangerDTO mapToStrangerDTO(User user) {
    return StrangerDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .registrationDate(user.getRegisteredIn())
            .build();
}

    
    // Helper methods
    
    private int calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return 0;
        }
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
    
    private String formatMonthYear(LocalDate date) {
        if (date == null) {
            return "";
        }
        // Format as "Month Year", e.g., "January 1990"
        return date.getMonth().toString() + " " + date.getYear();
    }
    
    /**
     * Fetches a BBRequest by its ID.
     * This method should be replaced by a proper implementation, likely by injecting a BBRequestService.
     * 
     * @param requestId the UUID of the BBRequest to fetch
     * @return the BBRequest object
     * @throws ResourceNotFoundException if the BBRequest is not found
     */
    private BBRequest getBBRequestById(UUID requestId) {
        // This should be implemented by injecting and using a BBRequestService
        throw new UnsupportedOperationException("Method not implemented yet - should use BBRequestService");
    }
    
    /**
     * Fetches a Chat by its ID.
     * This method should be replaced by a proper implementation, likely by injecting a ChatService.
     * 
     * @param chatId the UUID of the Chat to fetch
     * @return the Chat object
     * @throws ResourceNotFoundException if the Chat is not found
     */
    private Chat getChatById(UUID chatId) {
        // This should be implemented by injecting and using a ChatService
        throw new UnsupportedOperationException("Method not implemented yet - should use ChatService");
    }
}