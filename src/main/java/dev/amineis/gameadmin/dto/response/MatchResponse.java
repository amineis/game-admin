package dev.amineis.gameadmin.dto.response;

import dev.amineis.gameadmin.enums.MatchStatus;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResponse {

    private Long id;
    private Long gameId;
    private String gameName;
    private String region;
    private MatchStatus status;
    private List<MatchPlayerResponse> players;
    private Instant createdAt;
}
