package com.itoshi_m_dev.schedulingapi.DTO.UserDTO;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponseDTO(

        Long id,

        String name,

        String email,

        String phone,

        Set<String> roles,

        LocalDateTime createdAt
) {
}
