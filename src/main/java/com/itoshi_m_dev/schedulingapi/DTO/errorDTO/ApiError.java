package com.itoshi_m_dev.schedulingapi.DTO.errorDTO;

import java.time.LocalDateTime;

public record ApiError(
        LocalDateTime timeStamp,
        int status,
        String error,
        String message,
        String path
) {
}
