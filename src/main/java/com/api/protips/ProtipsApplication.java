package com.api.protips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync(proxyTargetClass = true)
@EnableScheduling
@ConfigurationPropertiesScan("com.api.protips.configurations")
@SpringBootApplication
public class ProtipsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProtipsApplication.class, args);
  }

}
