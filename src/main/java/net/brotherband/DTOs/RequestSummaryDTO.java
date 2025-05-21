package net.brotherband.DTOs;

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
public class RequestSummaryDTO {
    private UUID id;
    private UUID userId;
    private String username;
    private String name;
    private LocalDateTime sentDate;
}
