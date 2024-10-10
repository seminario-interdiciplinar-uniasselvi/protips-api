package com.api.protips.configurations.async;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

@Slf4j
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {


  @Override
  public void handleUncaughtException(
    Throwable throwable,
    Method method,
    Object... obj
  ) {
    log.error("Exception message - {}", throwable.getMessage());
    log.error("Method name - {}", method.getName());
    for (Object param : obj) {
      log.error("Parameter value - {}", param);
    }
  }

}