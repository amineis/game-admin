package dev.amineis.gameadmin.service;

import dev.amineis.gameadmin.dto.request.ReportCreateRequest;
import dev.amineis.gameadmin.dto.request.ReportUpdateRequest;
import dev.amineis.gameadmin.dto.response.PagedResponse;
import dev.amineis.gameadmin.dto.response.ReportResponse;
import dev.amineis.gameadmin.entity.Player;
import dev.amineis.gameadmin.entity.Report;
import dev.amineis.gameadmin.enums.ReportStatus;
import dev.amineis.gameadmin.exception.BusinessRuleException;
import dev.amineis.gameadmin.exception.ResourceNotFoundException;
import dev.amineis.gameadmin.mapper.ReportMapper;
import dev.amineis.gameadmin.repository.PlayerRepository;
import dev.amineis.gameadmin.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final PlayerRepository playerRepository;
    private final ReportMapper reportMapper;

    @Transactional(readOnly = true)
    public PagedResponse<ReportResponse> getAllReports(Pageable pageable) {
        return toPagedResponse(reportRepository.findAll(pageable));
    }

    @Transactional(readOnly = true)
    public ReportResponse getReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report", id));
        return reportMapper.toResponse(report);
    }

    @Transactional(readOnly = true)
    public PagedResponse<ReportResponse> getReportsByStatus(ReportStatus status, Pageable pageable) {
        return toPagedResponse(reportRepository.findByStatus(status, pageable));
    }

    @Transactional(readOnly = true)
    public PagedResponse<ReportResponse> getReportsByReportedPlayer(Long playerId, Pageable pageable) {
        return toPagedResponse(reportRepository.findByReportedPlayerId(playerId, pageable));
    }

    @Transactional(readOnly = true)
    public PagedResponse<ReportResponse> getReportsByReporterPlayer(Long playerId, Pageable pageable) {
        return toPagedResponse(reportRepository.findByReporterPlayerId(playerId, pageable));
    }

    @Transactional
    public ReportResponse createReport(ReportCreateRequest request) {
        if (request.getReportedPlayerId().equals(request.getReporterPlayerId())) {
            throw new BusinessRuleException("A player cannot report themselves");
        }
        if (reportRepository.existsByReportedPlayerIdAndReporterPlayerId(
                request.getReportedPlayerId(), request.getReporterPlayerId())) {
            throw new BusinessRuleException("A report for this player pair already exists");
        }

        Player reportedPlayer = playerRepository.findById(request.getReportedPlayerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player", request.getReportedPlayerId()));
        Player reporterPlayer = playerRepository.findById(request.getReporterPlayerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player", request.getReporterPlayerId()));

        Report report = reportMapper.toEntity(request);
        report.setReportedPlayer(reportedPlayer);
        report.setReporterPlayer(reporterPlayer);

        Report saved = reportRepository.save(report);
        return reportMapper.toResponse(saved);
    }

    @Transactional
    public ReportResponse updateReport(Long id, ReportUpdateRequest request) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report", id));

        if (request.getReason() != null) {
            report.setReason(request.getReason());
        }
        if (request.getStatus() != null) {
            report.setStatus(request.getStatus());
        }

        Report updated = reportRepository.save(report);
        return reportMapper.toResponse(updated);
    }

    @Transactional
    public void deleteReport(Long id) {
        if (!reportRepository.existsById(id)) {
            throw new ResourceNotFoundException("Report", id);
        }
        reportRepository.deleteById(id);
    }

    private PagedResponse<ReportResponse> toPagedResponse(Page<Report> page) {
        return PagedResponse.<ReportResponse>builder()
                .content(page.getContent().stream().map(reportMapper::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
