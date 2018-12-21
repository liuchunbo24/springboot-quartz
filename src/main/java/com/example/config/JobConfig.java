package com.example.config;

import com.example.bean.JobConfigBean;
import com.example.service.CronTriggerJob;
import com.example.service.ScheduledJob;
import com.example.service.SimpleTriggerJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.quartz.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/20.
 */
@Configuration
public class JobConfig {

    @Autowired
    private SimpleTriggerJob jobBean;

    @Autowired
    private ScheduledJob scheduledJob;


    @Autowired
    private CronTriggerJob jobCornBean;


    /**
     * 配置调度器第一种方式
     * @return
     */
    @Bean("methodInvokingJobDetailFactoryBean")
    public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean(){
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        bean.setTargetObject(jobBean);
        bean.setTargetMethod("printMessage");
        bean.setConcurrent(false);
        return bean;
    }

    /**
     * 配置调度器 第二种方式可以传递额外的参数给定时job
     * @return
     */
    @Bean("jobDetailFactoryBean")
    public JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(scheduledJob.getClass());
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobCornBean",jobCornBean);
        jobDetailFactoryBean.setJobDataMap(jobDataMap);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }


    /**
     * 触发器 simpleTriggerFactoryBean
     * @param methodInvokingJobDetailFactoryBean
     * @return
     */
    @Bean("simpleTriggerFactoryBean")
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean(MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean){
        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(methodInvokingJobDetailFactoryBean.getObject());
        simpleTriggerFactoryBean.setStartDelay(1000);
        simpleTriggerFactoryBean.setRepeatInterval(1000);//每秒执行一次

        return simpleTriggerFactoryBean;
    }


    /**
     * 触发器  cronTriggerFactoryBean
     * @param jobDetailFactoryBean
     * @return
     */
    @Bean("cronTriggerFactoryBean")
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean){
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        cronTriggerFactoryBean.setCronExpression("0/5 * * ? * *");//每5秒执行一次
        return cronTriggerFactoryBean;
    }

    /**
     * 将上面的jobDetails和triggers都配置到一个类中，这样便于后续定时任务的添加
     * @return
     */
    @Bean("jobConfigBean")
    public JobConfigBean jobConfigBean(){
        JobConfigBean jobConfigBean = new JobConfigBean();
        List<JobDetail> jobDetails = new ArrayList<>();
        jobDetails.add(methodInvokingJobDetailFactoryBean().getObject());
        jobDetails.add(jobDetailFactoryBean().getObject());

        JobDetail[] jobDetailarr = createJobDetail(jobDetails);

        List<Trigger> triggers = new ArrayList<>();
        triggers.add(simpleTriggerFactoryBean(methodInvokingJobDetailFactoryBean()).getObject());
        triggers.add(cronTriggerFactoryBean(jobDetailFactoryBean()).getObject());


        Trigger[] triggerarr = createTriggers(triggers);

        jobConfigBean.setJobDetails(jobDetailarr);
        jobConfigBean.setTriggers(triggerarr);

        return jobConfigBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobConfigBean jobConfigBean){

        //一个定时任务
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobDetails(jobConfigBean.getJobDetails());
        schedulerFactoryBean.setTriggers(jobConfigBean.getTriggers());

        return schedulerFactoryBean;
    }

    public static JobDetail[] createJobDetail(List<JobDetail> jobDetails){
        JobDetail[] jobDetailArr = new JobDetail[jobDetails.size()];
        for (int i = 0; i < jobDetails.size(); i++) {
            jobDetailArr[i] = jobDetails.get(i);
        }

        return jobDetailArr;

    }


    public static Trigger[] createTriggers(List<Trigger> triggers){
        Trigger[] triggerarr = new Trigger[triggers.size()];

        for (int i = 0; i <triggers.size() ; i++) {
            triggerarr[i] = triggers.get(i);
        }

        return triggerarr;
    }
}
