package com.api.protips.controllers;

import static org.springframework.http.HttpStatus.CREATED;

import com.api.protips.controllers.dto.newsletter.NewsletterDTO;
import com.api.protips.controllers.dto.newsletter.NewsletterRequest;
import com.api.protips.models.newsletter.NewsletterEmailContent;
import com.api.protips.models.newsletter.NewsletterSubscriber;
import com.api.protips.services.newsletter.NewsletterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users/{userId}/newsletters")
public class NewsletterController {

  private final NewsletterService newsletterService;

  @PostMapping
  public ResponseEntity<NewsletterDTO> createNewsletter(
    @RequestBody NewsletterRequest newsletterRequest,
    @PathVariable String userId
  ) {
    NewsletterDTO created = newsletterService.createNewsletter(newsletterRequest, userId);
    return ResponseEntity.status(CREATED).body(created);
  }

  @PostMapping("/{newsletterId}/content")
  public ResponseEntity<Void> addContent(
    @RequestBody NewsletterEmailContent content,
    @PathVariable String newsletterId,
    @PathVariable String userId
  ) {
    newsletterService.addContent(content, newsletterId);
    return ResponseEntity.noContent().build();
  }
  @GetMapping("/{newsletterId}/content/{subject}")
  public ResponseEntity<NewsletterEmailContent> findContent(
    @PathVariable String newsletterId,
    @PathVariable String subject,
    @PathVariable String userId
  ) {
    return ResponseEntity.ok(newsletterService.findContent(subject, newsletterId));
  }


  @GetMapping
  public ResponseEntity<NewsletterDTO> findByUser(
    @PathVariable String userId
  ) {
    return ResponseEntity.ok(newsletterService.findByUser(userId));
  }

  @PutMapping("/{newsletterId}")
  public ResponseEntity<Void> updateNewsletter(
    @RequestBody NewsletterDTO newsletterDTO,
    @PathVariable String newsletterId,
    @PathVariable String userId
  ) {
    newsletterService.updateNewsletter(newsletterDTO, newsletterId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{newsletterId}/content/{subject}")
  public ResponseEntity<Void> updateNewsletterContent(
    @RequestBody NewsletterEmailContent content,
    @PathVariable String newsletterId,
    @PathVariable String subject,
    @PathVariable String userId
  ) {
    newsletterService.updateNewsletterContent(
      content,
      newsletterId,
      subject
    );
    return ResponseEntity.noContent().build();
  }
  @DeleteMapping("/{newsletterId}/content/{subject}")
  public ResponseEntity<Void> deleteContent(
    @PathVariable String newsletterId,
    @PathVariable String subject,
    @PathVariable String userId
  ) {
    newsletterService.deleteContent(subject, newsletterId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{newsletterId}")
  public ResponseEntity<NewsletterDTO> getNewsletterById(
    @PathVariable String newsletterId,
    @PathVariable String userId
  ) {

    return ResponseEntity.ok(newsletterService.getNewsletterById(newsletterId));
  }

  @DeleteMapping("/{newsletterId}")
  public ResponseEntity<Void> deleteNewsletter(
    @PathVariable String newsletterId,
    @PathVariable String userId
  ) {
    newsletterService.deleteNewsletter(newsletterId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{newsletterId}/subscribe")
  public ResponseEntity<Void> subscribe(
    @RequestBody NewsletterSubscriber subscriber,
    @PathVariable String newsletterId,
    @PathVariable String userId
  ) {
    newsletterService.subscribe(subscriber, newsletterId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{newsletterId}/unsubscribe")
  public ResponseEntity<Void> unsubscribe(
    @RequestBody NewsletterSubscriber subscriber,
    @PathVariable String newsletterId,
    @PathVariable String userId
  ) {
    newsletterService.unsubscribe(subscriber.getEmail(), newsletterId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{newsletterId}/send")
  public ResponseEntity<Void> sendNewsletter(
    @PathVariable String newsletterId,
    @RequestBody String subject,
    @PathVariable String userId
  ) {
    newsletterService.sendNewsletter(newsletterId, subject);
    return ResponseEntity.noContent().build();
  }
}
