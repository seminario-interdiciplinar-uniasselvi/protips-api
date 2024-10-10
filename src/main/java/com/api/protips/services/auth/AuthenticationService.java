package com.api.protips.services.auth;

import com.api.protips.controllers.dto.auth.TokenDTO;
import com.api.protips.controllers.dto.users.UserDTO;
import java.util.UUID;

public interface AuthenticationService {

  void sendMagicLink(String email);

  TokenDTO authenticate(String token);
  void verifyAccount(String userId, String verificationToken);

  void sendAccountVerification(String email);

  UserDTO getAuthenticatedUser();
}
