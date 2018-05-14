package com.lingyun.demos.job.job;

import com.lingyun.common.data.Constant;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class ActivityJob implements Job{
    private static int repeatCount=0;
    @Override
    public void execute(JobExecutionContext context) {
        repeatCount++;
        System.err.println("第 "+repeatCount+" 次计划任务启动......  " );
        JobDetail jobDetail=context.getJobDetail();
        String group=jobDetail.getKey().getGroup();
        System.out.println("job key:"+jobDetail.getKey().getName());//这个key可保存activity id，从而可以查询数据库获得实体
        System.out.println("job description:"+jobDetail.getDescription());
        System.out.println("job group:"+jobDetail.getKey().getGroup());//group可以存储一些有用的信息，甚至json再解析

        if(group!=null&&group.equals("preparation")){
            System.out.println("提前2分钟准备，发送倒计时准备websocket通知");
            //do something
        }else if(group!=null&&group.equals("start")){
            System.out.println("活动正式开始");
            //do something
        }

    }

}
