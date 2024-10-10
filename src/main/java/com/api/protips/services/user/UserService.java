package com.api.protips.services.user;

import com.api.protips.controllers.dto.users.UserDTO;

public interface UserService {

  UserDTO createUser(UserDTO user);

  void updateUser(UserDTO user, String userId);

  UserDTO getUserByEmail(String email);

  void deleteUser(String userId);

}
