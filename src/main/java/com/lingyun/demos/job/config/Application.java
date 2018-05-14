package com.lingyun.demos.job.config;

import com.lingyun.demos.job.job.ActivityJob;
import com.lingyun.demos.job.websocket.MyWebSocket;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.servlet.MultipartConfigElement;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

//import com.lingyun.demos.job.domain.person.entity.Person;
//import com.lingyun.demos.job.domain.person.repo.PersonRepository;


@SpringBootApplication(scanBasePackages = {
        "com.lingyun.demos.job.domain.**.controller",
        "com.lingyun.demos.job.config",
        "com.lingyun.demos.job.websocket"})
@EnableAutoConfiguration
@EntityScan(basePackages ={"com.lingyun.demos.job.domain.**.entity"})
@ServletComponentScan("com.lingyun.demos.job.filter")
public class Application implements ApplicationListener<ContextRefreshedEvent> {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }




    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("20MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("30MB");
        return factory.createMultipartConfig();
    }



    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//            String[] beanNames = ctx.getBeanDefinitionNames();
        };
    }


    @Value("${spring.profiles.active}")
    private String profile;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
    @Value("${app.scheduler.start}")
    private boolean startScheduler;

    public boolean isStartScheduler() {
        return startScheduler;
    }

    public void setStartScheduler(boolean startScheduler) {
        this.startScheduler = startScheduler;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext ctx=contextRefreshedEvent.getApplicationContext();

        initBeans(ctx);


        //启动计划任务
//        System.out.println("startScheduler:"+startScheduler);
//        if (startScheduler){
            startScheduler();
//        }
    }

    private void initBeans(ApplicationContext ctx) {

        RepositoryManager.webSocket = ctx.getBean("myWebSocket", MyWebSocket.class);
    }

    private void startScheduler() {
        // Grab the Scheduler instance from the Factory
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            // and start it off


            // define the job and tie it to our MyJob class
            JobDetail job = newJob(ActivityJob.class)
                    .withIdentity("start activity everyday", "common")
                    .build();

            // Trigger the job to run now, and then repeat every 40 seconds
            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "common")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(60)
                            .repeatForever())
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
