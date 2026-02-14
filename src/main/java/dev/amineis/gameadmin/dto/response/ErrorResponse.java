package dev.amineis.gameadmin.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Error response body returned on 4xx/5xx")
public class ErrorResponse {

    @Schema(description = "HTTP status code", example = "400")
    private int status;
    @Schema(description = "Error type", example = "Bad Request")
    private String error;
    @Schema(description = "Human-readable message")
    private String message;
    @Schema(description = "Validation field errors (for 400 validation failures)")
    private Map<String, String> fieldErrors;

    @Builder.Default
    @Schema(description = "Error timestamp")
    private Instant timestamp = Instant.now();
}
