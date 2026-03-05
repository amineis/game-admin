package dev.amineis.gameadmin.mapper;

import dev.amineis.gameadmin.dto.request.MatchPlayerRequest;
import dev.amineis.gameadmin.dto.response.MatchPlayerResponse;
import dev.amineis.gameadmin.entity.MatchPlayer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MatchPlayerMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "score", ignore = true)
  @Mapping(target = "match", ignore = true)
  @Mapping(target = "player.id", source = "playerId")
  MatchPlayer toEntity(MatchPlayerRequest matchPlayerRequest);

  @Mapping(target = "playerId", source = "player.id")
  @Mapping(target = "playerUsername", source = "player.username")
  MatchPlayerResponse toResponse(MatchPlayer matchPlayer);
}
