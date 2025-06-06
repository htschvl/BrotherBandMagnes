package net.brotherband.DTOs;

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
    private int age;
    private String birthdayMonthYear; 
    private LocalDateTime registeredIn;
    
    private int numberOfBrothers;
    private List<BrotherSummaryDTO> brothers;

}