package com.api.protips.models.newsletter;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsletterEmailContent {

  @Indexed(unique = true)
  private String subject;
  private String bodyHtml;
  private List<EmailAttachment> attachments = new ArrayList<>();
  private boolean current;
  private boolean sent;
}
