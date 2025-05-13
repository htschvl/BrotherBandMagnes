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
import net.brotherband.DTOs.ProfileDTO;
import net.brotherband.DTOs.UserDTO;
import net.brotherband.DTOs.UserMeDTO;
import net.brotherband.DTOs.UserRegisterDTO;
import net.brotherband.DTOs.UserMeDTO.BrotherSummaryDTO;
import net.brotherband.DTOs.UserMeDTO.ChatSummaryDTO;
import net.brotherband.DTOs.UserMeDTO.MessagePreviewDTO;
import net.brotherband.DTOs.UserMeDTO.RequestSummaryDTO;
import net.brotherband.DTOs.ProfileDTO.FriendSummaryDTO;
import net.brotherband.exceptions.ResourceNotFoundException;
import net.brotherband.exceptions.UsernameAlreadyExistsException;
import net.brotherband.models.BBRequest;
import net.brotherband.models.Messages;
import net.brotherband.models.Users;
import net.brotherband.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserMeDTO getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users user = findUserByUsername(username);

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


        List<RequestSummaryDTO> sentRequestsList = user.getSentBBRequests().stream()
                .map(request -> RequestSummaryDTO.builder()
                        .id(request.getId())
                        .userId(request.getReceiver().getId())
                        .username(request.getReceiver().getUsername())
                        .name(request.getReceiver().getName())
                        .sentDate(request.getSentDate())
                        .status(request.getStatus().name())
                        .build())
                .collect(Collectors.toList());

        List<RequestSummaryDTO> receivedRequestsList = user.getReceivedBBRequests().stream()
                .map(request -> RequestSummaryDTO.builder()
                        .id(request.getId())
                        .userId(request.getSender().getId())
                        .username(request.getSender().getUsername())
                        .name(request.getSender().getName())
                        .sentDate(request.getSentDate())
                        .status(request.getStatus().name())
                        .build())
                .collect(Collectors.toList());

        long pendingSentRequestsCount = sentRequestsList.stream()
                .filter(r -> "PENDING".equals(r.getStatus()))
                .count();
        long pendingReceivedRequestsCount = receivedRequestsList.stream()
                .filter(r -> "PENDING".equals(r.getStatus()))
                .count();

        List<ChatSummaryDTO> chatsList = user.getChats().stream()
                .map(chat -> {
                    Messages lastMessage = chat.getMessages().stream()
                            .max(Comparator.comparing(Messages::getSentTime))
                            .orElse(null);

                    MessagePreviewDTO lastMsg = null;
                    if (lastMessage != null) {
                        lastMsg = MessagePreviewDTO.builder()
                                .id(lastMessage.getId())
                                .senderId(lastMessage.getSender().getId())
                                .senderUsername(lastMessage.getSender().getUsername())
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
                            .messageCount(chat.getMessages().size())
                            .lastMessage(lastMsg)
                            .build();
                })
                .collect(Collectors.toList());

        return UserMeDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .dateOfBirth(dob)
                .birthday(birthday)
                .age(age)
                .registeredIn(user.getRegisteredIn())
                .brothersCount(brothersList.size())
                .brothers(brothersList)
                .pendingSentRequestsCount((int) pendingSentRequestsCount)
                .pendingReceivedRequestsCount((int) pendingReceivedRequestsCount)
                .sentRequests(sentRequestsList)
                .receivedRequests(receivedRequestsList)
                .chatCount(chatsList.size())
                .chats(chatsList)
                .build();
    }
    
    public UserDTO register(UserRegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }
        
        // Create and save the new user
        Users user = Users.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .name(registerDTO.getName())
                .dateOfBirth(registerDTO.getDateOfBirth())
                .registeredIn(LocalDateTime.now())
                .build();
        
        Users savedUser = userRepository.save(user);
        
        return mapToUserDTO(savedUser);
    }
    
    public UserDTO findById(UUID id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        return mapToUserDTO(user);
    }
    
    public UserDTO findDTOByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::mapToUserDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }
    
    public Users findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }
    
    public ProfileDTO getProfileByUsername(String username) {
        Users user = findUserByUsername(username);
        
        // Get the brothers (established bonds)
        List<FriendSummaryDTO> brothersList = user.getBrothers().stream()
        .map(bond -> FriendSummaryDTO.builder()
                .id(bond.getBrother().getId())
                .username(bond.getBrother().getUsername())
                .name(bond.getBrother().getName())
                .bondEstablishedDate(bond.getEstablishedDate())
                .build())
        .collect(Collectors.toList());

        
        // Count pending requests
        long pendingRequestsCount = user.getReceivedBBRequests().stream()
                .filter(request -> request.getStatus() == BBRequest.RequestStatus.PENDING)
                .count();
        
        return ProfileDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .dateOfBirth(user.getDateOfBirth())
                .registeredIn(user.getRegisteredIn())
                .brothersCount(brothersList.size())
                .brothers(brothersList)
                .pendingRequestsCount((int) pendingRequestsCount)
                .chatCount(user.getChats().size())
                .build();
    }
    
    private UserDTO mapToUserDTO(Users user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .dateOfBirth(user.getDateOfBirth())
                .registeredIn(user.getRegisteredIn())
                .build();
    }
    
}