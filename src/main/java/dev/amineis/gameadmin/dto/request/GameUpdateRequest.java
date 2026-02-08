package dev.amineis.gameadmin.dto.request;

import dev.amineis.gameadmin.enums.Genre;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameUpdateRequest {

    @Size(max = 100, message = "Game name must not exceed 100 characters")
    private String name;

    private Genre genre;
}
