package com.api.protips.services.newsletter;

import com.api.protips.configurations.mail.EmailSchedulerService;
import com.api.protips.controllers.dto.newsletter.NewsletterDTO;
import com.api.protips.controllers.dto.newsletter.NewsletterRequest;
import com.api.protips.exceptions.ResourceNotFoundException;
import com.api.protips.exceptions.UnexpectedException;
import com.api.protips.mail.MailSender;
import com.api.protips.mail.dto.EmailDTO;
import com.api.protips.models.newsletter.Newsletter;
import com.api.protips.models.newsletter.NewsletterEmailContent;
import com.api.protips.models.newsletter.NewsletterSubscriber;
import com.api.protips.models.user.User;
import com.api.protips.repositories.NewsletterRepository;
import com.api.protips.repositories.UserRepository;
import com.google.gson.Gson;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NewsletterServiceImpl implements NewsletterService {

  private final NewsletterRepository newsletterRepository;
  private final UserRepository userRepository;
  private final ModelMapper mapper;
  private final MailSender sender;
  private final EmailSchedulerService emailSchedulerService;

  @Override
  public NewsletterDTO createNewsletter(NewsletterRequest newsletterRequest ,String userId) {
    boolean exists = userRepository.existsById(userId);
    if (!exists) {
      throw new ResourceNotFoundException("User not found");
    }
    boolean validExpression = CronExpression.isValidExpression(newsletterRequest.getCron());
    if (!validExpression) {
      throw new UnexpectedException("Invalid cron expression");
    }
    NewsletterEmailContent content = newsletterRequest.getContent();
    content.setCurrent(true);
    Newsletter newsletter = mapper.map(newsletterRequest, Newsletter.class);
    newsletter.setUserId(userId);
    newsletter.setActive(true);
    newsletter.addContent(content);
    Newsletter saved = newsletterRepository.save(newsletter);

    try {
      emailSchedulerService.scheduleEmail(
        saved.getId(),
        new CronExpression(newsletterRequest.getCron())
      );
    } catch (SchedulerException | ParseException e) {
      throw new UnexpectedException(e.getMessage());
    }

    return mapper.map(saved, NewsletterDTO.class);
  }

  @Override
  public void updateNewsletter(NewsletterDTO newsletterDTO, String newsletterId) {
    Newsletter newsletter = newsletterRepository.findById(newsletterId)
      .orElseThrow(() -> new ResourceNotFoundException("Newsletter not found"));

    mapper.map(newsletterDTO, newsletter);

    newsletterRepository.save(newsletter);
  }

  @Override
  public NewsletterDTO getNewsletterById(String newsletterId) {
    return newsletterRepository.findById(newsletterId)
      .map(newsletter -> mapper.map(newsletter, NewsletterDTO.class))
      .orElseThrow(() -> new ResourceNotFoundException("Newsletter not found"));
  }

  @Override
  public void deleteNewsletter(String newsletterId) {
    newsletterRepository.deleteById(newsletterId);
  }

  @Override
  public List<NewsletterDTO> findAllByUser(String userId) {
    return newsletterRepository.findByUserId(userId)
      .stream()
      .map(newsletter -> mapper.map(newsletter, NewsletterDTO.class))
      .toList();
  }

  @Override
  public void subscribe(NewsletterSubscriber subscriber, String newsletterId) {
    Newsletter newsletter = newsletterRepository.findById(newsletterId)
      .orElseThrow(() -> new ResourceNotFoundException("Newsletter not found"));

    subscriber.setSubscribedAt(LocalDateTime.now());
    subscriber.setActive(true);

    newsletter.addSubscriber(subscriber);

    newsletterRepository.save(newsletter);
  }

  @Override
  public void unsubscribe(String email, String newsletterId) {
    Newsletter newsletter = newsletterRepository.findById(newsletterId)
      .orElseThrow(() -> new ResourceNotFoundException("Newsletter not found"));

    newsletter.removeSubscriber(email);

    newsletterRepository.save(newsletter);
  }

  @Override
  public void sendNewsletter(String newsletterId, String subject) {
    Newsletter newsletter = newsletterRepository.findById(newsletterId)
      .orElseThrow(() -> new ResourceNotFoundException("Newsletter not found"));

    String[] emails = newsletter
      .getSubscribers()
      .stream()
      .map(NewsletterSubscriber::getEmail)
      .toArray(String[]::new);
    if (subject != null){
      NewsletterEmailContent content = newsletter.getContent(subject)
        .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

      EmailDTO email = new EmailDTO(
        emails,
        subject,
        content.getBodyHtml()
      );

      sender.send(email, new HashMap<>());
      return;
    }
    NewsletterEmailContent content = newsletter.getCurrentEmailContent()
      .orElseThrow(() -> new ResourceNotFoundException("Content not found"));

    EmailDTO email = new EmailDTO(
      emails,
      content.getSubject(),
      content.getBodyHtml()
    );

    sender.send(email, new HashMap<>());
  }
}
