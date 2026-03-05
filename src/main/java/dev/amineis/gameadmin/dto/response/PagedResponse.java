package dev.amineis.gameadmin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(
    description =
        "Paginated response wrapper; content holds the list of items for the current page")
public class PagedResponse<T> {

  @Schema(description = "List of items for the current page")
  private List<T> content;

  @Schema(description = "Zero-based page index", example = "0")
  private int page;

  @Schema(description = "Page size", example = "20")
  private int size;

  @Schema(description = "Total number of elements across all pages")
  private long totalElements;

  @Schema(description = "Total number of pages")
  private int totalPages;

  @Schema(description = "Whether this is the last page")
  private boolean last;
}
