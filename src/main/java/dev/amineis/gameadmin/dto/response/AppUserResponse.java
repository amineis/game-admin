package dev.amineis.gameadmin.dto.response;

import dev.amineis.gameadmin.enums.Role;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private Instant createdAt;
}
