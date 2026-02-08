package dev.amineis.gameadmin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportCreateRequest {

    @NotNull(message = "Reported player ID is required")
    private Long reportedPlayerId;

    @NotNull(message = "Reporter player ID is required")
    private Long reporterPlayerId;

    @NotBlank(message = "Reason is required")
    private String reason;
}
