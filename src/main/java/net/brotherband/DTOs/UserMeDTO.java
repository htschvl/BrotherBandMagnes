package net.brotherband.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMeDTO {
     private UUID id;
     private String username;
     private String name;
     private int age;

     //Time0related//
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime registeredIn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM")
     private MonthDay birthday;


    // Brotherhood information
    private int brothersCount;
    private List<BrotherSummaryDTO> brothers;

    
    // BBRequests information
    private int pendingSentRequestsCount;
    private int pendingReceivedRequestsCount;
    private List<RequestSummaryDTO> sentRequests;
    private List<RequestSummaryDTO> receivedRequests;
    
    // Chat information
    private int chatCount;
    private List<ChatSummaryDTO> chats;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrotherSummaryDTO {
        private UUID id;
        private String username;
        private String name;
        private LocalDateTime bondEstablishedDate;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestSummaryDTO {
        private UUID id;
        private UUID userId;
        private String username;
        private String name;
        private LocalDateTime sentDate;
        private String status;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatSummaryDTO {
        private UUID id;
        private String chatName;
        private boolean isGroupChat;
        private LocalDateTime createdDate;
        private int participantCount;
        private int messageCount;
        private MessagePreviewDTO lastMessage;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessagePreviewDTO {
        private UUID id;
        private UUID senderId;
        private String senderUsername;
        private String content;
        private LocalDateTime sentTime;
        private boolean isRead;
    }
}