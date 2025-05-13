package net.brotherband.DTOs;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    
    private UUID id;
    private String username;
    private String name;
    private LocalDate dateOfBirth;
    private LocalDateTime registeredIn;
    
    // Extended profile information
    private int brothersCount;
    private List<FriendSummaryDTO> brothers;
    private int pendingRequestsCount;
    private int chatCount;
    
    // Nested DTO for compact friend/brother info
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FriendSummaryDTO {
        private UUID id;
        private String username;
        private String name;
        private LocalDateTime bondEstablishedDate;
    }
}