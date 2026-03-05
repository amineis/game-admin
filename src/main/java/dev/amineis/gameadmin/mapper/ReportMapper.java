package dev.amineis.gameadmin.mapper;

import dev.amineis.gameadmin.dto.request.ReportCreateRequest;
import dev.amineis.gameadmin.dto.response.ReportResponse;
import dev.amineis.gameadmin.entity.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "reportedPlayer", ignore = true)
  @Mapping(target = "reporterPlayer", ignore = true)
  Report toEntity(ReportCreateRequest reportCreateRequest);

  @Mapping(source = "reportedPlayer.id", target = "reportedPlayerId")
  @Mapping(source = "reportedPlayer.username", target = "reportedPlayerUsername")
  @Mapping(source = "reporterPlayer.id", target = "reporterPlayerId")
  @Mapping(source = "reporterPlayer.username", target = "reporterPlayerUsername")
  ReportResponse toResponse(Report report);
}
