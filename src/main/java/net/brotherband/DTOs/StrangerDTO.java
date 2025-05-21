package net.brotherband.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrangerDTO {
    private UUID id;
    private String username;
    private LocalDateTime registrationDate;
}
