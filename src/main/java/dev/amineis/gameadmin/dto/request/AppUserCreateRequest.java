package dev.amineis.gameadmin.dto.request;

import dev.amineis.gameadmin.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Request body to create a new app user (admin/moderator/support)")
public class AppUserCreateRequest {

  @Schema(
      description = "Username",
      example = "moderator1",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "Username is required")
  @Size(max = 50, message = "Username must not exceed 50 characters")
  private String username;

  @Schema(
      description = "Email address",
      example = "mod@example.com",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "Email is required")
  @Email(message = "Email must be valid")
  private String email;

  @Schema(
      description = "Password hash (from Keycloak or internal)",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "Password hash is required")
  private String passwordHash;

  @Schema(
      description = "Role: ADMIN, MODERATOR, SUPPORT",
      example = "MODERATOR",
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "Role is required")
  private Role role;
}
