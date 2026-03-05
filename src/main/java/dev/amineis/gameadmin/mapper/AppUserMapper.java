package dev.amineis.gameadmin.mapper;

import dev.amineis.gameadmin.dto.response.AppUserResponse;
import dev.amineis.gameadmin.entity.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {

  AppUserResponse toResponse(AppUser appUser);
}
