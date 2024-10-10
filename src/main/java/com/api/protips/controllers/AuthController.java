package com.api.protips.controllers;

import com.api.protips.controllers.dto.auth.TokenDTO;
import com.api.protips.controllers.dto.users.UserDTO;
import com.api.protips.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

  private final AuthenticationService authenticationService;


  @PostMapping("/magic-link")
  public ResponseEntity<Void> sendMagicLink(@RequestParam String email) {
    authenticationService.sendMagicLink(email);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/authenticate")
  public ResponseEntity<TokenDTO> authenticate(@RequestParam String token) {
    return ResponseEntity.ok(authenticationService.authenticate(token));
  }

  @GetMapping("/verify-account")
  public ResponseEntity<Void> verifyAccount(
    @RequestParam String email,
    @RequestParam String token
  ) {
    authenticationService.verifyAccount(email, token);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/send-verification")
  public ResponseEntity<Void> sendAccountVerification(@RequestParam String email) {
    authenticationService.sendAccountVerification(email);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/authenticated-user")
  public ResponseEntity<UserDTO> getAuthenticatedUser() {
    return ResponseEntity.ok(authenticationService.getAuthenticatedUser());
  }
}
