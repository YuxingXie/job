package com.lingyun.demos.job.domain.job.controller;

import com.lingyun.common.context.BaseReturn;
import com.lingyun.common.data.Constant;
import com.lingyun.demos.job.domain.activity.entity.Activity;
import com.lingyun.demos.job.job.ActivityJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.Calendar;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@RestController
public class JobController {
    @RequestMapping("/job/edit/starttime")
    public BaseReturn jobEditTime(){
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            //移除job
            TriggerKey triggerKey=TriggerKey.triggerKey("trigger1", "common");
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            JobKey jobKey=JobKey.jobKey("start activity everyday","common");
            scheduler.deleteJob(jobKey);

            //重新定义job，注意name和group
            JobDetail job = newJob(ActivityJob.class)
                    .withIdentity("start activity everyday", "common")
                    .build();
            Calendar calendar=new GregorianCalendar();
            calendar.setTimeInMillis(System.currentTimeMillis()+10000);
            System.out.println("重新定义开始时间，十秒以后开始");
            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "common")
                    .startAt(calendar.getTime())
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(Constant.JOB_INTERVAL_SECONDS)
                            .repeatForever())
                    .build();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
            return new BaseReturn(true,"trigger is modified",null);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增一个活动加入计划任务(修改活动类似)
     * 让活动在开始5分钟之前发出预告
     * @return
     */
    @RequestMapping("/job/add/activity")
    public BaseReturn addActivityJob(){
        Activity commonActivity =new Activity();
        commonActivity.setId("common activity id 1");
        Calendar calendarBegin =new GregorianCalendar();
        //即便开始时间在过去，启动job后job仍然执行
        long currentTimeMillis = System.currentTimeMillis();
        calendarBegin.setTimeInMillis(currentTimeMillis +1000*60*4);//4分钟以后正式开始
        commonActivity.setBeginDate(calendarBegin.getTime());
        commonActivity.setType("custom");
        commonActivity.setTitle("自定义照片墙启动。。。。。。");
        Calendar calendarEnd=new GregorianCalendar();
        calendarEnd.setTimeInMillis(currentTimeMillis +1000*60*14);//14分钟后结束，活动时长10分钟



        commonActivity.setEndDate(calendarEnd.getTime());
        long activityRunningSeconds=(commonActivity.getEndDate().getTime()-commonActivity.getBeginDate().getTime())/1000;
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail customJob = newJob(ActivityJob.class)
                    .withIdentity(commonActivity.getId(), "start")
                    .withDescription(commonActivity.getTitle())
                    .build();

            Trigger customTrigger = newTrigger()
                    .withIdentity(commonActivity.getId(), "start")
                    .startAt(commonActivity.getBeginDate())
                    .endAt(commonActivity.getEndDate())
                    .withSchedule(simpleSchedule().withRepeatCount(1).withIntervalInSeconds(Integer.parseInt(""+activityRunningSeconds)))
                    .build();


            JobDetail preparationJob = newJob(ActivityJob.class)
                    .withIdentity(commonActivity.getId(), "preparation")
                    .withDescription(commonActivity.getTitle())
                    .build();
            Calendar preparationTriggerCalendar= new GregorianCalendar();
            preparationTriggerCalendar.setTimeInMillis(commonActivity.getBeginDate().getTime()-1000*60*2);//提前2分钟
            Trigger preparationTrigger = newTrigger()
                    .withIdentity(commonActivity.getId(), "preparation")
                    .startAt(preparationTriggerCalendar.getTime())
                    .endAt(commonActivity.getEndDate())
                    .withSchedule(simpleSchedule().withRepeatCount(1).withIntervalInSeconds(Integer.parseInt(""+(activityRunningSeconds+1000*60*2))))
                    .build();


            Map<JobDetail,Set<? extends Trigger>> jobDetailMap=new HashMap<>();
            Set<Trigger> triggerSet=new HashSet<>();
            triggerSet.add(customTrigger);
            jobDetailMap.put(customJob,triggerSet);

            Set<Trigger> preparationTriggerSet=new HashSet<>();
            preparationTriggerSet.add(preparationTrigger);
            jobDetailMap.put(preparationJob,preparationTriggerSet);

            scheduler.scheduleJobs(jobDetailMap,false);
//            scheduler.start();web程序启动的时候已经start了，不用再start
            System.out.println("~~~~~~~~~~~~~~~~~启动了新任务~~~~~~~~~~~~~~~~~~~~");
            return new BaseReturn(true,"new job join in scheduler",null);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
