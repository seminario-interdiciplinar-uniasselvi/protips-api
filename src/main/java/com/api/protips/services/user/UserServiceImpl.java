package com.api.protips.services.user;

import com.api.protips.controllers.dto.users.UserDTO;
import com.api.protips.exceptions.ResourceAlreadyExistsException;
import com.api.protips.exceptions.ResourceNotFoundException;
import com.api.protips.models.user.User;
import com.api.protips.repositories.UserRepository;
import com.api.protips.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final AuthenticationService authenticationService;
  private final ModelMapper mapper;
  private final PasswordEncoder encoder;
  @Override
  public UserDTO createUser(UserDTO userDTO) {
    checkUser(userDTO);
    User user = mapper.map(userDTO, User.class);
    user.setPassword(encoder.encode(""));
    User saved = userRepository.save(user);
//    authenticationService.sendAccountVerification(saved.getEmail());
    return mapper.map(saved, UserDTO.class);
  }

  @Override
  public void updateUser(UserDTO userDTO, String userId) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    checkUser(userDTO);
    mapper.map(userDTO, user);
    userRepository.save(user);
  }

  @Override
  public UserDTO getUserByEmail(String email) {
    return
      userRepository.findByEmail(email)
      .map(user -> mapper.map(user, UserDTO.class))
      .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  @Override
  public void deleteUser(String userId) {
    userRepository.deleteById(userId);
  }

  private void checkUser(UserDTO userDTO) {
    if (userDTO.getEmail() != null && userRepository.existsByEmail(userDTO.getEmail())) {
      throw new ResourceAlreadyExistsException("User already exists");
    }
  }
}
