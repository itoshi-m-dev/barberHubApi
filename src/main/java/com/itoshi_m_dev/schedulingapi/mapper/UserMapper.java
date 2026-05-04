package com.itoshi_m_dev.schedulingapi.mapper;

import com.itoshi_m_dev.schedulingapi.DTO.UserDTO.UserRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.UserDTO.UserResponseDTO;
import com.itoshi_m_dev.schedulingapi.enums.RolesEnum;
import com.itoshi_m_dev.schedulingapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserResponseDTO toDTO(User entity);

    default Set<String> mapRoles(Set<RolesEnum> roles){
        if(roles == null) return null;

        return roles.stream().map(Enum::name).collect(Collectors.toSet());
    }
}
