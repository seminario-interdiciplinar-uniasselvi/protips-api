package com.api.protips.controllers;

import static org.springframework.http.HttpStatus.CREATED;

import com.api.protips.controllers.dto.auth.TokenDTO;
import com.api.protips.controllers.dto.users.UserDTO;
import com.api.protips.services.auth.AuthenticationService;
import com.api.protips.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
    return ResponseEntity.status(CREATED).body(userService.createUser(user));
  }

  @PostMapping("/user/{userId}")
  public ResponseEntity<Void> updateUser(@RequestBody UserDTO user, @PathVariable String userId) {
    userService.updateUser(user, userId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/user/{email}")
  public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
    return ResponseEntity.ok(userService.getUserByEmail(email));
  }

  @DeleteMapping("/user/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
    userService.deleteUser(userId);
    return ResponseEntity.ok().build();
  }
}
