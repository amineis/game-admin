package dev.amineis.gameadmin.repository;

import dev.amineis.gameadmin.entity.Report;
import dev.amineis.gameadmin.enums.ReportStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

  Page<Report> findByStatus(ReportStatus status, Pageable pageable);

  Page<Report> findByReportedPlayerId(Long playerId, Pageable pageable);

  Page<Report> findByReporterPlayerId(Long playerId, Pageable pageable);

  List<Report> findByReportedPlayerIdAndStatus(Long playerId, ReportStatus status);

  boolean existsByReportedPlayerIdAndReporterPlayerId(Long reportedPlayerId, Long reporterPlayerId);
}
