package com.api.protips.configurations.mail;

import java.time.ZoneId;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class EmailSchedulerService {

  private final Scheduler scheduler;

  public void scheduleEmail(
    String newsletterId,
    CronExpression cronExpression
  ) throws SchedulerException {

    JobKey jobKey = JobKey.jobKey(newsletterId);
    TriggerKey triggerKey = TriggerKey.triggerKey(newsletterId);
    if (scheduler.checkExists(jobKey)){
      scheduler.deleteJob(jobKey);
    }
    if (scheduler.checkExists(triggerKey)){
      scheduler.deleteJob(jobKey);
    }
    JobDetail jobDetail = JobBuilder.newJob(EmailJob.class)
      .withIdentity(jobKey)
      .usingJobData("newsletterId", newsletterId)
      .build();

    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
      .cronSchedule(cronExpression)
      .inTimeZone(TimeZone.getTimeZone(ZoneId.of("America/Sao_Paulo")));
    Trigger trigger = TriggerBuilder.newTrigger()
      .withIdentity(triggerKey)
      .withSchedule(cronScheduleBuilder)
      .startNow()
      .build();

    scheduler.scheduleJob(jobDetail, trigger);

  }

  public void unscheduledEmail(String userId) {
    JobKey jobKey = JobKey.jobKey(userId);
    TriggerKey triggerKey = TriggerKey.triggerKey(userId);
    try {
      scheduler.deleteJob(jobKey);
      scheduler.unscheduleJob(triggerKey);
    } catch (SchedulerException e) {
      log.error("Error unscheduling email", e);
    }
  }
}
