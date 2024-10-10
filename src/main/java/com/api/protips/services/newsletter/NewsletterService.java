package com.api.protips.services.newsletter;

import com.api.protips.controllers.dto.newsletter.NewsletterDTO;
import com.api.protips.controllers.dto.newsletter.NewsletterRequest;
import com.api.protips.models.newsletter.NewsletterSubscriber;
import java.util.List;

public interface NewsletterService {

  NewsletterDTO createNewsletter(NewsletterRequest newsletterDTO, String userId);

  void updateNewsletter(NewsletterDTO newsletterDTO, String newsletterId);

  NewsletterDTO getNewsletterById(String newsletterId);

  void deleteNewsletter(String newsletterId);

  List<NewsletterDTO> findAllByUser(String userId);

  void subscribe(NewsletterSubscriber subscriber, String newsletterId);

  void unsubscribe(String email, String newsletterId);

  void sendNewsletter(String newsletterId, String subject);

}
