package dev.amineis.gameadmin.dto.request;

import dev.amineis.gameadmin.enums.Role;
import jakarta.validation.constraints.Email;
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
public class AppUserUpdateRequest {

  @Size(max = 50, message = "Username must not exceed 50 characters")
  private String username;

  @Email(message = "Email must be valid")
  private String email;

  private String passwordHash;

  private Role role;
}
