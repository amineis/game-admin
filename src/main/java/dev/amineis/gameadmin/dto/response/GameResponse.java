package dev.amineis.gameadmin.dto.response;

import dev.amineis.gameadmin.enums.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Game response")
public class GameResponse {

    @Schema(description = "Game ID")
    private Long id;
    @Schema(description = "Game name", example = "League of Legends")
    private String name;
    @Schema(description = "Genre", example = "MOBA")
    private Genre genre;
    @Schema(description = "Creation timestamp")
    private Instant createdAt;
}
