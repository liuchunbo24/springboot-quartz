package com.example.bean;

import org.quartz.JobDetail;
import org.quartz.Trigger;


public class JobConfigBean {

    private JobDetail[] jobDetails;

    private Trigger[] triggers;

    public JobDetail[] getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(JobDetail[] jobDetails) {
        this.jobDetails = jobDetails;
    }

    public Trigger[] getTriggers() {
        return triggers;
    }

    public void setTriggers(Trigger[] triggers) {
        this.triggers = triggers;
    }
}
