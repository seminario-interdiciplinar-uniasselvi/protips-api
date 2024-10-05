package com.api.protips.models.newsletter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsletterSubscriber {

  private String email;
  private String name;
  private LocalDateTime subscribedAt;
  private boolean active;
}
