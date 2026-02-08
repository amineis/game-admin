package dev.amineis.gameadmin.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerResponse {

    private Long id;
    private String username;
    private String country;
    private Instant createdAt;
}
