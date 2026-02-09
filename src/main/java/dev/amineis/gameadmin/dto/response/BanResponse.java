package dev.amineis.gameadmin.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BanResponse {

    private Long id;
    private Long playerId;
    private String playerUsername;
    private Long gameId;
    private String gameName;
    private String reason;
    private Long bannedByUserId;
    private String bannedByUsername;
    private Instant createdAt;
}
