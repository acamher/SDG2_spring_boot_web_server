package com.example.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.Callable;

@Configuration
@EnableAsync
@EnableScheduling
public class ImageStreamingConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(360000).setTaskExecutor(getAsyncExecutor());
        configurer.registerCallableInterceptors(callableProcessingInterceptor());
    }

    @Bean(name = "taskExecutor")
    public AsyncTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("AsyncStreaming-");
        return executor;
    }

    @Bean
    public CallableProcessingInterceptor callableProcessingInterceptor() {
        return new TimeoutCallableProcessingInterceptor() {
            @Override
            public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task) throws
                    Exception {
                return super.handleTimeout(request, task);
            }
        };
    }
}