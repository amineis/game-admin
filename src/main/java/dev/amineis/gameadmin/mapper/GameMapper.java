



package dev.amineis.gameadmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.amineis.gameadmin.dto.request.GameCreateRequest;
import dev.amineis.gameadmin.dto.request.GameUpdateRequest;
import dev.amineis.gameadmin.dto.response.GameResponse;
import dev.amineis.gameadmin.entity.Game;

@Mapper(componentModel = "spring")
public interface GameMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Game toEntity(GameCreateRequest gameCreateRequest);

    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Game toEntity(GameUpdateRequest gameUpdateRequest);


    GameResponse toResponse(Game game);

    
}