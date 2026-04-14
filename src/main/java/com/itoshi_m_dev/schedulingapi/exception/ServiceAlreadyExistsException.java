package com.itoshi_m_dev.schedulingapi.exception;

public class serviceAlrearyExistsException extends RuntimeException {
  public serviceAlrearyExistsException(String message) {
    super(message);
  }
}
