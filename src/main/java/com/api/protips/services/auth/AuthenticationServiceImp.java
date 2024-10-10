package com.api.protips.services.auth;

import com.api.protips.configurations.ApplicationProperties;
import com.api.protips.configurations.auth.JwtService;
import com.api.protips.controllers.dto.auth.TokenDTO;
import com.api.protips.controllers.dto.users.UserDTO;
import com.api.protips.exceptions.ForbiddenException;
import com.api.protips.exceptions.ResourceNotFoundException;
import com.api.protips.exceptions.VerificationNotMatchesException;
import com.api.protips.mail.MailSender;
import com.api.protips.mail.dto.Template;
import com.api.protips.mail.dto.EmailDTO;
import com.api.protips.models.user.User;
import com.api.protips.repositories.UserRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final MailSender sender;
  private final AuthenticatedUserUseCase authenticatedUserUseCase;
  private final ModelMapper mapper;
  private final ApplicationProperties applicationProperties;
  private final AuthenticationManager authenticationManager;

  @Override
  public void sendMagicLink(String email) {
    userRepository.findByEmail(email).ifPresent(user -> {
      EmailDTO emailDTO = new EmailDTO(
        new String[]{email},
        Template.MAGIC_LINK
      );

      String token = jwtService.generateToken(user);

      String baseUrl = applicationProperties.getBaseUrl();
      String loginUrl = UriComponentsBuilder
        .fromHttpUrl(baseUrl.concat("/v1/auth/authenticate"))
        .queryParam("token", token)
        .build()
        .toString();

      Map<String, Object> params = new HashMap<>();
      params.put("url", loginUrl);
      sender.send(emailDTO,params);
    });

  }

  @Override
  public TokenDTO authenticate(String magicLinkToken) {
    String email = jwtService.extractUsername(magicLinkToken);
    User user = userRepository
      .findByEmail(email)
      .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    if (!user.isVerified()) {
      throw new ForbiddenException("User not verified");
    }

    UsernamePasswordAuthenticationToken authToken =
      new UsernamePasswordAuthenticationToken(
        email,
        ""
      );

    authenticationManager.authenticate(authToken);
    String accessToken = jwtService.generateToken(user);

    return new TokenDTO(accessToken);
  }

  @Override
  public void verifyAccount(String email, String verificationToken) {
    User user = userRepository
      .findByEmail(email)
      .orElseThrow(() -> new ResourceNotFoundException("User not found"));
     if (user.isVerified()) {
      return;
    }

    boolean tokenValid = jwtService.isTokenValid(verificationToken, user);
    if (!tokenValid) {
      throw new VerificationNotMatchesException("Verification token does not match");
    }

    user.setVerified(true);
    userRepository.save(user);
  }

  @Override
  public void sendAccountVerification(String userEmail) {
    User user = userRepository
      .findByEmail(userEmail)
      .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    String token = jwtService.generateToken(user);
    String baseUrl = applicationProperties.getBaseUrl();
    String url = UriComponentsBuilder
      .fromHttpUrl(baseUrl.concat("/v1/auth/verify-account"))
      .queryParam("token", token)
      .queryParam("email", userEmail)
      .build()
      .toString();
    HashMap<String, Object> params = new HashMap<>();
    params.put("name", user.getName());
    params.put("url", url);
    sender.send(
      new EmailDTO(
        new String[]{user.getEmail()},
        Template.ACCOUNT_CONFIRMATION
      ),
      params
    );
  }

  @Override
  public UserDTO getAuthenticatedUser() {
    return mapper.map(authenticatedUserUseCase.get(), UserDTO.class);
  }
}
