package com.api.protips.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("application")
public class ApplicationProperties {

  private String baseUrl;
  private String frontBaseUrl;
  private Jwt jwt;
  private Cors cors;


  @Getter
  @Setter
  public static class Jwt {

    private String expiration;
    private String refreshExpiration;
    private String secretKey;

  }

  @Getter
  @Setter
  public static class Cors {

    private String[] allowedOrigins;
    private String allowedMethods;
    private String allowedHeaders;
    private String exposedHeaders;
    private String maxAge;
    private boolean allowCredentials;

  }
}
