package dev.amineis.gameadmin.dto.response;

import dev.amineis.gameadmin.enums.Genre;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResponse {

    private Long id;
    private String name;
    private Genre genre;
    private Instant createdAt;
}
