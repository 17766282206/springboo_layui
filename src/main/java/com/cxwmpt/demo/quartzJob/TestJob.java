package com.cxwmpt.demo.quartzJob;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * @author Administrator
 */
public class TestJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)  {
        // 获取参数
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        System.out.println("Quartz ---->  Hello, ");
    }
}
