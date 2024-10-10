package com.api.protips.configurations.mail;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailSchedulerService {

  private final Scheduler scheduler;

  public void scheduleEmail(
    String newsletterId,
    CronExpression cronExpression
  ) throws SchedulerException {

    JobKey jobKey = JobKey.jobKey(newsletterId);
    if (scheduler.checkExists(jobKey)){
      scheduler.deleteJob(jobKey);
    }
    JobDetail jobDetail = JobBuilder.newJob(EmailJob.class)
      .withIdentity(jobKey)
      .usingJobData("newsletterId", newsletterId)
      .build();

    Trigger trigger = TriggerBuilder.newTrigger()
      .withIdentity("EmailTrigger", "EmailGroup")
      .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
      .startNow()
      .build();

    scheduler.scheduleJob(jobDetail, trigger);

  }
}
