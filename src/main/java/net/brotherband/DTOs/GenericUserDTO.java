package net.brotherband.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericUserDTO {
    
    private UUID id;
    private String username;
    private String name;
    private LocalDate dateOfBirth;
    private LocalDateTime registeredIn;
}