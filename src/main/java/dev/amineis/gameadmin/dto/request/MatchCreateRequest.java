package dev.amineis.gameadmin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchCreateRequest {

    @NotNull(message = "Game ID is required")
    private Long gameId;

    @NotBlank(message = "Region is required")
    @Size(max = 30, message = "Region must not exceed 30 characters")
    private String region;
}
