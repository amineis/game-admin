package dev.amineis.gameadmin.dto.request;

import dev.amineis.gameadmin.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameCreateRequest {

    @NotBlank(message = "Game name is required")
    @Size(max = 100, message = "Game name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Genre is required")
    private Genre genre;
}
