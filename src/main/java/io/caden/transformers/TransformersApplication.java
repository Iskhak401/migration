package io.caden.transformers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication(scanBasePackages = "io.caden.*")
public class TransformersApplication {
  private static final int SCHEDULER_POOL_SIZE = 10;
  private static final String SCHEDULER_PREFIX = "IntakeTaskExecutor-";

  public static void main(final String[] args) {
    SpringApplication.run(TransformersApplication.class, args);
  }

  @Bean
  public TaskScheduler taskScheduler() {
    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    threadPoolTaskScheduler.setPoolSize(TransformersApplication.SCHEDULER_POOL_SIZE);
    threadPoolTaskScheduler.setThreadNamePrefix(TransformersApplication.SCHEDULER_PREFIX);
    return threadPoolTaskScheduler;
  }
}
