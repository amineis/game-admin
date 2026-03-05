package dev.amineis.gameadmin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body to create a new report")
public class ReportCreateRequest {

  @Schema(
      description = "ID of the reported player",
      example = "1",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "Reported player ID is required")
  private Long reportedPlayerId;

  @Schema(
      description = "ID of the reporter player",
      example = "2",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "Reporter player ID is required")
  private Long reporterPlayerId;

  @Schema(
      description = "Reason for the report",
      example = "Cheating in ranked match",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "Reason is required")
  private String reason;
}
