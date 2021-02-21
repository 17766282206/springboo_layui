package com.cxwmpt.demo.quartzJob;


import com.cxwmpt.demo.model.system.SysJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class QuartzManager {

    /**
     * 注入调度器
     */
    private final Scheduler scheduler;


    public QuartzManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * "0 * * * * ?"
     *
     * @param jobClass
     * @param sysJob
     * @param jobData
     */
    public void addJob(Class jobClass, SysJob sysJob, Map jobData) {
        try {
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            // 任务名称和组构成任务key
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(sysJob.getJobName(), sysJob.getJobGroup())
                    .build();
            // 设置job参数
            if (jobData != null && jobData.size() > 0) {
                jobDetail.getJobDataMap().putAll(jobData);
            }
            // 定义调度触发规则
            // 使用cornTrigger规则
            // 触发器key
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(sysJob.getJobName(), sysJob.getJobGroup())
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(sysJob.getCronExpression())).startNow().build();
            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改 一个job的 时间表达式
     */
    public void updateJob(SysJob sysJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(sysJob.getJobName(), sysJob.getJobGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(sysJob.getCronExpression())).build();
            // 重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 删除任务一个job
     */
    public void deleteJob(SysJob sysJob) {
        try {
            scheduler.deleteJob(new JobKey(sysJob.getJobName(), sysJob.getJobGroup()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 暂停一个job
     */
    public void stopJob(SysJob sysJob) {
        try {
            JobKey jobKey = JobKey.jobKey(sysJob.getJobName(), sysJob.getJobGroup());
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 重启一个job
     * @param sysJob
     */
    public void restartJob(SysJob sysJob) {

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(sysJob.getJobName(), sysJob.getJobGroup());
            Trigger trigger = scheduler.getTrigger(triggerKey);
            scheduler.rescheduleJob(triggerKey, trigger);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 恢复一个job
     */
    public void recoveryJob(SysJob sysJob) {
        try {
            JobKey jobKey = JobKey.jobKey(sysJob.getJobName(), sysJob.getJobGroup());
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 立即执行一个job
     */
    public void runJob(SysJob sysJob) {
        try {
            JobKey jobKey = JobKey.jobKey(sysJob.getJobName(), sysJob.getJobGroup());
            scheduler.triggerJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     */
    public List<Map<String, Object>> queryAllJob() {
        List<Map<String, Object>> jobList = null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = new ArrayList<Map<String, Object>>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jobName", jobKey.getName());
                    map.put("jobGroupName", jobKey.getGroup());
                    map.put("description", "触发器:" + trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    map.put("jobStatus", triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        map.put("jobTime", cronExpression);
                    }
                    jobList.add(map);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jobList;
    }

    /**
     * 获取所有正在运行的job
     *
     * @return
     */
    public List<Map<String, Object>> queryRunJob() {
        List<Map<String, Object>> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<Map<String, Object>>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String, Object> map = new HashMap<String, Object>();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                map.put("jobName", jobKey.getName());
                map.put("jobGroupName", jobKey.getGroup());
                map.put("description", "触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                map.put("jobStatus", triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    map.put("jobTime", cronExpression);
                }
                jobList.add(map);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jobList;
    }
    /**
     * @Description:启动所有定时任务
     *
     */
    public  void startJobs() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     *
     */
    public  void stopJobs() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}