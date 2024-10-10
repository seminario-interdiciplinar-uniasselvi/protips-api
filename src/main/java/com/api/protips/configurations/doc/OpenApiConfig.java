package com.api.protips.configurations.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("API CABELIN BARBER")
        .version("v0")
        .description(
          "Add description"
        )
        .termsOfService("add terms")
        .license(new License()
          .name("license")
          .url("url"))
        .contact(contact()));

  }

  private Contact contact() {
    Contact contact = new Contact();
    contact.setEmail("add email");
    contact.setName("Cabelin Professional");
    contact.setUrl("add url");
    return contact;
  }

}