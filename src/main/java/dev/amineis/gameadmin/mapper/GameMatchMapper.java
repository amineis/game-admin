



package dev.amineis.gameadmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.amineis.gameadmin.dto.request.MatchCreateRequest;
import dev.amineis.gameadmin.dto.response.MatchResponse;
import dev.amineis.gameadmin.entity.GameMatch;


@Mapper(componentModel = "spring")
public interface GameMatchMapper {

    @Mapping(target = "game.id", source = "gameId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    GameMatch toEntity(MatchCreateRequest matchCreateRequest);

    @Mapping(target = "gameId", source = "game.id")
    @Mapping(target = "gameName", source = "game.name")
    @Mapping(target = "players", ignore = true)
    MatchResponse toResponse(GameMatch gameMatch);
}