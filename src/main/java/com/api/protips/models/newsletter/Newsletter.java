package com.api.protips.models.newsletter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.scheduling.support.CronExpression;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "newsletter")
public class Newsletter {

  @MongoId
  private String id;

  private String title;
  private String description;
  private List<NewsletterSubscriber> subscribers = new ArrayList<>();
  private List<NewsletterEmailContent> contents = new ArrayList<>();
  private CronExpression cron;
  private boolean active;

  public void addSubscriber(NewsletterSubscriber subscriber) {
    this.subscribers.add(subscriber);
  }

  public void addContent(NewsletterEmailContent content) {
    this.contents.add(content);
  }

  public Optional<NewsletterEmailContent> getCurrentEmailContent() {
    return this.contents.stream()
      .filter(NewsletterEmailContent::isCurrent)
      .findFirst();
  }
}