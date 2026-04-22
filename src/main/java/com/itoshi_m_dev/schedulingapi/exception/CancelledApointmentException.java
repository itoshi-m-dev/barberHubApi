package com.itoshi_m_dev.schedulingapi.exception;

public class CancelledApointmentException extends RuntimeException {
    public CancelledApointmentException(String message) {
        super(message);
    }
}
