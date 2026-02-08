package dev.amineis.gameadmin.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerUpdateRequest {

    @Size(max = 50, message = "Username must not exceed 50 characters")
    private String username;

    @Size(min = 2, max = 2, message = "Country must be a 2-letter ISO code")
    private String country;
}
