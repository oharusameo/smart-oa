package com.quxue.template.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class SpringAsyncConfig {

    @Bean(name = "emailTaskExecutor")
    public Executor emailTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(8);
        taskExecutor.setQueueCapacity(20);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix("emailTaskExecutor-thread-");
        // 拒绝的处理策略，默认有abortPolicy、CallerRunsPolicy；默认为后者
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 调度器shutdown被调用时等待当前被调度的任务完成
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 空闲存活时间
        taskExecutor.setAwaitTerminationSeconds(20);
        taskExecutor.initialize();
        return taskExecutor;
    }
}