package dev.amineis.gameadmin.dto.request;

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
public class BanUpdateRequest {

    @Size(max = 255, message = "Reason must not exceed 255 characters")
    private String reason;
}
