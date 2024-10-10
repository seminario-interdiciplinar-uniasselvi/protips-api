package com.api.protips.configurations.mail;

import com.api.protips.mail.MailSender;
import com.api.protips.mail.dto.EmailDTO;
import com.api.protips.models.newsletter.Newsletter;
import com.api.protips.models.newsletter.NewsletterEmailContent;
import com.api.protips.models.newsletter.NewsletterSubscriber;
import com.api.protips.repositories.NewsletterRepository;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class EmailJob implements Job {

    private final MailSender sender;
    private final NewsletterRepository newsletterRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String newsletterId = context.getJobDetail().getJobDataMap().getString("newsletterId");

        Optional<Newsletter> newsletterOptional = newsletterRepository.findById(newsletterId);
        if (newsletterOptional.isEmpty()) {
            log.error("Newsletter not found");
            return;
        }
        Newsletter newsletter = newsletterOptional.get();
        Optional<NewsletterEmailContent> emailContentOptional = newsletter.getCurrentEmailContent();
        if (emailContentOptional.isEmpty()) {
            log.error("Content not found");
            return;
        }

        NewsletterEmailContent content = emailContentOptional.get();
        EmailDTO email = new EmailDTO();
        email.setBody(content.getBodyHtml());
        email.setSubject(content.getSubject());
        newsletter
          .getSubscribers()
          .forEach(newsletterSubscriber -> email.addTo(newsletterSubscriber.getEmail()));

        sender.send(email, null);
    }
}
