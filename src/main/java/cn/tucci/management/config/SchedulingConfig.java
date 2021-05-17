package cn.tucci.management.config;

import cn.tucci.management.service.monitor.TaskScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @author tucci.lee
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 存储执行中的定时任务
     *
     * @return hashmap
     */
    @Bean
    public Map<Long, ScheduledFuture<?>> scheduledFutureMap() {
        return new HashMap<>();
    }

    /**
     * 监听器，项目启动就开启的定时任务
     *
     * @return ApplicationListener
     */
    @Bean
    public ApplicationListener<ContextRefreshedEvent> startSysTaskListener() {
        return event -> {
            ApplicationContext ac = event.getApplicationContext();
            TaskScheduleService bean = ac.getBean(TaskScheduleService.class);
            bean.startSysTask();
            logger.info("系统定时任务启动成功");
        };
    }
}

