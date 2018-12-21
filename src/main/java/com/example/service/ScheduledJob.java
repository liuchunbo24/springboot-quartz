package com.example.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


@Component
public class ScheduledJob extends QuartzJobBean {

    private CronTriggerJob jobCornBean;


    @Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {
           jobCornBean.printAnotherMessage();
    }

    public void setJobCornBean(CronTriggerJob jobCornBean) {
        this.jobCornBean = jobCornBean;
    }
}
