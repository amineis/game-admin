package dev.amineis.gameadmin.controller;

import dev.amineis.gameadmin.dto.request.ReportCreateRequest;
import dev.amineis.gameadmin.dto.request.ReportUpdateRequest;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.dto.response.ReportResponse;
import dev.amineis.gameadmin.enums.ReportStatus;
import dev.amineis.gameadmin.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
@Tag(name = "Report Controller", description = "API docs for reports management")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @Operation(summary = "List all reports", description = "Returns a paginated list of player reports.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<ReportResponse>> getAllReports(Pageable pageable) {
        return ResponseEntity.ok(reportService.getAllReports(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get report by ID", description = "Returns a single report by id.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "Report not found") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<ReportResponse> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @GetMapping("/by-status")
    @Operation(summary = "List reports by status", description = "Returns reports filtered by status (PENDING, REVIEWED, REJECTED, BANNED).")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<ReportResponse>> getReportsByStatus(@RequestParam ReportStatus status, Pageable pageable) {
        return ResponseEntity.ok(reportService.getReportsByStatus(status, pageable));
    }

    @GetMapping("/by-reported-player")
    @Operation(summary = "List reports by reported player", description = "Returns reports for the given reported player id (paginated).")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<ReportResponse>> getReportsByReportedPlayer(@RequestParam Long playerId, Pageable pageable) {
        return ResponseEntity.ok(reportService.getReportsByReportedPlayer(playerId, pageable));
    }

    @GetMapping("/by-reporter-player")
    @Operation(summary = "List reports by reporter player", description = "Returns reports submitted by the given player id (paginated).")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<PagedResponse<ReportResponse>> getReportsByReporterPlayer(@RequestParam Long playerId, Pageable pageable) {
        return ResponseEntity.ok(reportService.getReportsByReporterPlayer(playerId, pageable));
    }

    @PostMapping
    @Operation(summary = "Create report", description = "Creates a new player report. Any authenticated user with MODERATOR/SUPPORT can submit.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Created"), @ApiResponse(responseCode = "400", description = "Validation error"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR','SUPPORT')")
    public ResponseEntity<ReportResponse> createReport(@Valid @RequestBody ReportCreateRequest request) {
        return ResponseEntity.ok(reportService.createReport(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update report", description = "Updates a report (e.g. status). Requires MODERATOR or ADMIN.")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Success"), @ApiResponse(responseCode = "400", description = "Validation error"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "Report not found") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<ReportResponse> updateReport(@PathVariable Long id, @Valid @RequestBody ReportUpdateRequest request) {
        return ResponseEntity.ok(reportService.updateReport(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete report", description = "Deletes a report by id. Returns 204 No Content.")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Deleted"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "Report not found") })
    @PreAuthorize("hasAnyAuthority('ADMIN','MODERATOR')")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
