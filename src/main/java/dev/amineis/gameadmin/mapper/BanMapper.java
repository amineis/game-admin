



package dev.amineis.gameadmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.amineis.gameadmin.dto.request.BanCreateRequest;
import dev.amineis.gameadmin.dto.response.BanResponse;
import dev.amineis.gameadmin.entity.Ban;

@Mapper(componentModel = "spring")
public interface BanMapper {

    @Mapping(target = "player", ignore = true)
    @Mapping(target = "game", ignore = true)
    @Mapping(target = "bannedByUser", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Ban toEntity(BanCreateRequest banCreateRequest);

    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "player.username", target = "playerUsername")
    @Mapping(source = "game.id", target = "gameId")
    @Mapping(source = "game.name", target = "gameName")
    @Mapping(source = "bannedByUser.id", target = "bannedByUserId")
    @Mapping(source = "bannedByUser.username", target = "bannedByUsername")
    BanResponse toResponse(Ban ban);
}