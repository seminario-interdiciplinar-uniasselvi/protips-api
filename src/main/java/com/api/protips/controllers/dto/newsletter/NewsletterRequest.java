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
public class NewsletterRequest {

  private String title;
  private String description;
  private NewsletterEmailContent content;
  private String cron;
}
