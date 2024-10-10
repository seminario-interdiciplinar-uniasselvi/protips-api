package com.api.protips.services.auth;

import com.api.protips.models.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserUseCaseImp implements AuthenticatedUserUseCase {

  @Override
  public User get() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return null;
    }
    return (User) authentication.getPrincipal();
  }
}
