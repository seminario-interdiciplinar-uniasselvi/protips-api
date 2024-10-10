package com.api.protips.mail.dto;

import com.api.protips.exceptions.MailSendException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;

@Getter
@AllArgsConstructor
public enum Template {
  MAGIC_LINK("magic-link.html", "Clique aqui e faça login"),
  ACCOUNT_CONFIRMATION("account-confirmation.html", "Confirme sua conta");

  private final String filename;
  private final String subject;

  public String loadTemplate() {
    try {
      ClassPathResource classPathResource =
        new ClassPathResource("templates/".concat(this.filename));

      InputStream is = classPathResource.getInputStream();
      final BufferedReader br = new BufferedReader(
        new InputStreamReader(
          is,
          StandardCharsets.UTF_8
        )
      );

      new InputStreamReader(
        Objects.requireNonNull(
          is,
          "Template not found"
        ),
        StandardCharsets.UTF_8
      );
      return br.lines().collect(Collectors.joining("\n"));
    } catch (Exception e) {
      throw new MailSendException("Error loading template: " + e.getMessage());
    }
  }
}