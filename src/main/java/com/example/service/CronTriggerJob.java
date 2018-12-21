package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/12/20.
 */
@Component("jobCornBean")
@Slf4j
public class CronTriggerJob {
    public void printAnotherMessage(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
        log.info("CronTriggerFactoryBean任务在"+sdf.format(new Date())+"执行.....");
    }
}
