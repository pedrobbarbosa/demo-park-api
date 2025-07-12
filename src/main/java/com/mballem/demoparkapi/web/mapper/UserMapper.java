package com.mballem.demoparkapi.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.mballem.demoparkapi.web.dto.UserRequestDTO;
import com.mballem.demoparkapi.web.dto.UserResponseDTO;
import com.mballem.demoparkapi.web.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toUserDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "ROLE_CLIENTE")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    User toUser(UserRequestDTO userRequestDTO);

}
