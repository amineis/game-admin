package dev.amineis.gameadmin.mapper;

import dev.amineis.gameadmin.dto.request.PlayerCreateRequest;
import dev.amineis.gameadmin.dto.request.PlayerUpdateRequest;
import dev.amineis.gameadmin.dto.response.PlayerResponse;
import dev.amineis.gameadmin.entity.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Player toEntity(PlayerCreateRequest playerCreateRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Player toEntity(PlayerUpdateRequest playerUpdateRequest);

    PlayerResponse toResponse(Player player);
}
