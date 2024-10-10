package com.api.protips.configurations.mail;

import com.api.protips.mail.MailSender;
import com.api.protips.mail.SimpleMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfig {


  @Bean
  public MailSender sender(
    JavaMailSender javaMailSender,
    MailProperties mailProperties
  ) {
    return new SimpleMailSender(javaMailSender, mailProperties);
  }
}
