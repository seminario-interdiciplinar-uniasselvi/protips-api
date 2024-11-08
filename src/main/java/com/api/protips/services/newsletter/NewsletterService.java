package com.api.protips.services.newsletter;

import com.api.protips.controllers.dto.newsletter.NewsletterDTO;
import com.api.protips.controllers.dto.newsletter.NewsletterRequest;
import com.api.protips.models.newsletter.NewsletterEmailContent;
import com.api.protips.models.newsletter.NewsletterSubscriber;

public interface NewsletterService {

  NewsletterDTO createNewsletter(NewsletterRequest newsletterDTO, String userId);

  void addContent(
    NewsletterEmailContent content,
    String newsletterId
  );
  NewsletterEmailContent findContent(
    String subject,
    String newsletterId
  );

  void updateNewsletter(NewsletterDTO newsletterDTO, String newsletterId);

  void updateNewsletterContent(
    NewsletterEmailContent content,
    String newsletterId,
    String subject
  );

  NewsletterDTO getNewsletterById(String newsletterId);

  void deleteNewsletter(String newsletterId);

  NewsletterDTO findByUser(String userId);

  void subscribe(NewsletterSubscriber subscriber, String newsletterId);

  void unsubscribe(String email, String newsletterId);

  void sendNewsletter(String newsletterId, String subject);

  void deleteContent(String subject, String newsletterId);
}
