package dev.amineis.gameadmin.dto.request;

import dev.amineis.gameadmin.enums.MatchStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchUpdateRequest {

  @Size(max = 30, message = "Region must not exceed 30 characters")
  private String region;

  private MatchStatus status;
}
