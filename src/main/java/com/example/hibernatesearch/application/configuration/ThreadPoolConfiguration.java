/**
 * 
 */
package com.example.hibernatesearch.application.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author LinhLH
 */
@Slf4j
@EnableScheduling
@EnableAsync
@Configuration
public class ThreadPoolConfiguration {
    /* Schedule Thread Pool */
    /* start */
    @Value("${task.execution.pool.schedule.core-size}")
    private int corePoolSize;

    @Value("${task.execution.pool.schedule.max-size}")
    private int maxPoolSize;

    @Value("${task.execution.pool.schedule.queue-capacity}")
    private int queueCapacity;

    @Value("${task.execution.pool.schedule.keep-alive}")
    private int keepAliveSeconds;

    @Value("${task.execution.pool.schedule.allow-core-thread-timeout}")
    private boolean allowCoreThreadTimeOut;

    @Value("${task.execution.pool.schedule.name}")
    private String threadName;

    @Bean("nagakawaScheduleExecutor")
    public Executor messageQueueExecutor() {
        return threadPoolTaskExecutor(maxPoolSize, maxPoolSize, queueCapacity, keepAliveSeconds, allowCoreThreadTimeOut,
                threadName);
    }
    /* end */

    private ThreadPoolTaskExecutor threadPoolTaskExecutor(int corePoolSize, int maxPoolSize, int queueCapacity,
            int keepAliveSeconds, boolean allowCoreThreadTimeOut, String threadName) {
        log.info("create thread pool task executor");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
        threadPoolTaskExecutor.setThreadNamePrefix(threadName);
        threadPoolTaskExecutor.initialize();

        threadPoolTaskExecutor.setRejectedExecutionHandler((runnable, executor) -> {
            executor.execute(runnable);
        });

        return threadPoolTaskExecutor;
    }
}
