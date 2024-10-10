package com.api.protips.controllers.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

  private String id;
  private String name;
  private String email;
  private boolean verified;
}
