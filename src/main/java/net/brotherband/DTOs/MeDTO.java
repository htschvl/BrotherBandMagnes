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
public class MeDTO {
     private UUID id;
     private String username;
     private String name;
     private int age;
  

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime registeredIn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM")
    private MonthDay birthday;


    private int brothersCount;
    private List<BrotherSummaryDTO> brothers;

    
    private int pendingSentRequestsCount;
    private int pendingReceivedRequestsCount;
    private List<RequestSummaryDTO> sentRequests;
    private List<RequestSummaryDTO> receivedRequests;
    
    private int chatCount;
    private List<ChatSummaryDTO> chats;
    
   
}