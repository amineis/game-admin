package dev.amineis.gameadmin.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchPlayerRequest {

    @NotNull(message = "Player ID is required")
    private Long playerId;

    @Size(max = 20, message = "Team name must not exceed 20 characters")
    private String team;
}
