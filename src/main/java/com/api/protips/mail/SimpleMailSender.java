package com.api.protips.mail;

import com.api.protips.configurations.mail.MailProperties;
import com.api.protips.mail.dto.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class SimpleMailSender implements MailSender {

  private final JavaMailSender sender;
  private final MailProperties mailProperties;

  @Override
  @Async
  public void send(
    @NonNull EmailDTO emailDTO,
    Map<String, Object> parameters
  ) {
    if (emailDTO.getTo() == null || emailDTO.getTo().length == 0) {
      log.debug("No recipient found");
      return;
    }
    try {
      sender.send(createMessage(emailDTO, parameters));
    } catch (MessagingException e) {
      throw new MailSendException(e.getMessage());
    }
  }

  private String usingTemplate(final EmailDTO emailDTO, Map<String, Object> parameters) {
    if (parameters == null) {
      parameters = new HashMap<>();
    }
    if (emailDTO.getTemplate() != null){
      return StringSubstitutor.replace(emailDTO.getTemplate().loadTemplate(), parameters);

    }
    return StringSubstitutor.replace(emailDTO.getBody(), parameters);
  }

  private MimeMessage createMessage(
    EmailDTO emailDTO,
    Map<String, Object> parameters
  )
    throws MessagingException {
    final MimeMessage message = sender.createMimeMessage();
    final MimeMessageHelper helper = new MimeMessageHelper(message, true);

    final String from = mailProperties.getUsername();
    helper.setSubject(emailDTO.getSubject());
    helper.setTo(emailDTO.getTo());
    helper.setText(usingTemplate(emailDTO, parameters), true);
    helper.setFrom(new InternetAddress(from));

    return message;
  }

}
