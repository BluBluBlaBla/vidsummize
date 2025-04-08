package com.vidsummize.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AsyncConfig {

    @Bean(name = "transcriptionExecutorService")
    public java.util.concurrent.ExecutorService executorService() {
        return java.util.concurrent.Executors.newFixedThreadPool(2);
    }
}
