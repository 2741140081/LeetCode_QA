package com.marks.schedule.config;

import com.marks.schedule.task.DailyTaskJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    /**
     * 定义任务详情
     */
    @Bean
    public JobDetail dailyTaskJobDetail() {
        return JobBuilder.newJob(DailyTaskJob.class)
                .withIdentity("dailyTaskJob", "dailyGroup")
                .storeDurably()
                .build();
    }

    /**
     * 定义触发器 - 每天上午10:20执行
     */
    @Bean
    public Trigger dailyTaskTrigger() {
        // 使用 Cron 表达式: 秒 分 时 日 月 周
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 20 10 * * ?");

        return TriggerBuilder.newTrigger()
                .forJob(dailyTaskJobDetail())
                .withIdentity("dailyTaskTrigger", "dailyGroup")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
