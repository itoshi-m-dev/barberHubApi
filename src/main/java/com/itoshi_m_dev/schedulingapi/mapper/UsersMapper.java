package com.itoshi_m_dev.schedulingapi.mapper;

import com.itoshi_m_dev.schedulingapi.DTO.UserDTO.UserRequestDTO;
import com.itoshi_m_dev.schedulingapi.DTO.UserDTO.UserResponseDTO;
import com.itoshi_m_dev.schedulingapi.enums.RolesEnum;
import com.itoshi_m_dev.schedulingapi.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "roles", expression = "java(mapStringToRoles(dto.roles()))")
    Users toEntity(UserRequestDTO dto);

    @Mapping(target = "roles", expression = "java(mapRolesToString(users.getRoles()))")
    UserResponseDTO toDTO(Users users);

    default Set<String> mapRolesToString(Set<RolesEnum> roles){
        if(roles == null) return null;

        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    default Set<RolesEnum> mapStringToRoles(Set<String> roles){
        if(roles == null) return null;

        return roles.stream()
                .map(RolesEnum::valueOf)
                .collect(Collectors.toSet());
    }
}
