package io.caden.transformers.amazon.services;


import io.caden.transformers.shared.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class AmazonOrdersFlusherJobScheduler {
    private final AmazonOrdersTransformation amazonOrdersTransformation;

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    @SchedulerLock(name = "amazonOrderTransformationFlusher", lockAtMostFor = "30m")
    public void amazonOrderTransformationFlusher() {
        try{
            amazonOrdersTransformation.flush();
        }catch (Exception e) {
            log.error("Error amazonOrderTransformationFlusher.", e);
            throw CustomException.withCode(HttpStatus.INTERNAL_SERVER_ERROR, "class: " + e.getClass().getName() + "message: " + e.getMessage());
        }

    }
}
