package com.api.protips.controllers.dto.newsletter;

import com.api.protips.models.newsletter.NewsletterEmailContent;
import com.api.protips.models.newsletter.NewsletterSubscriber;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.support.CronExpression;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsletterDTO {

  private String id;
  private String title;
  private String description;
  private List<NewsletterSubscriber> subscribers = new ArrayList<>();
  private List<NewsletterEmailContent> contents = new ArrayList<>();
  private String cron;
  private boolean active;
}
