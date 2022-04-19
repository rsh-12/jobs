package ru.rsh12.resume.config;
/*
 * Date: 19.04.2022
 * Time: 9:30 AM
 * */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class PoolConfig {

    private final Integer THREAD_POOL_SIZE;
    private final Integer TASK_QUEUE_SIZE;

    @Autowired
    public PoolConfig(
            @Value("${app.threadPoolSize:10}") Integer threadPoolSize,
            @Value("${app.taskQueueSize:100}") Integer taskQueueSize) {
        THREAD_POOL_SIZE = threadPoolSize;
        TASK_QUEUE_SIZE = taskQueueSize;
    }

    @Bean
    public Scheduler jdbcScheduler() {
        return Schedulers.newBoundedElastic(THREAD_POOL_SIZE, TASK_QUEUE_SIZE, "jdbc-pool");
    }

}
