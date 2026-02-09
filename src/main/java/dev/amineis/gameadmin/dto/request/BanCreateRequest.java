package dev.amineis.gameadmin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BanCreateRequest {

    @NotNull(message = "Game ID is required")
    private Long gameId;

    @NotBlank(message = "Ban reason is required")
    private String reason;
}
