package io.caden.transformers.mx.services;

import io.caden.transformers.mx.entities.MxGraphDbBatch;
import io.caden.transformers.mx.repositories.MxGraphDbBatchRepository;
import io.caden.transformers.mx.repositories.BatchStagingSaveRepository;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class MxTransactionsFlusher {

    @Autowired
    private BatchStagingSaveRepository mxBatchStagingRepository;
    @Autowired
    private MxGraphDbBatchRepository mxGraphDbBatchRepository;
    @Value("${mx.transactions.batch.size}")
    private int defaultBatchSize;
    private int dbBatchSize = 0;
    private boolean lock = true;

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    @SchedulerLock(name = "mxTransactionFlusher", lockAtMostFor = "30m")
    public void mxTransactionFlusher() throws Exception {
        checkForLock();
        if(dbBatchSize == 0) {
            MxGraphDbBatch mxGraphDbBatch = mxGraphDbBatchRepository.findFirstByOrderById();

            if (mxGraphDbBatch != null) {
                dbBatchSize = mxGraphDbBatch.getBatchSize();
            } else {
                dbBatchSize = defaultBatchSize;
            }
        }

        mxBatchStagingRepository.flush(dbBatchSize);
    }

    private void checkForLock() {
        if(lock) {
            LockAssert.assertLocked();
        }
    }

    public int getDbBatchSize() {
        return dbBatchSize;
    }
}
