package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 任务一 使用simpleTrigger
 * Created by Administrator on 2018/12/20.
 */
@Component
@Slf4j
public class SimpleTriggerJob {
    public void printMessage() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
        log.info("SimpleTriggerFactoryBean任务在"+sdf.format(new Date())+"执行......");
    }
}
