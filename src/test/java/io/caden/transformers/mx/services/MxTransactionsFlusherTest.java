package io.caden.transformers.mx.services;

import io.caden.transformers.mx.entities.MxGraphDbBatch;
import io.caden.transformers.mx.repositories.MxGraphDbBatchRepository;
import io.caden.transformers.mx.repositories.BatchStagingSaveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MxTransactionsFlusherTest {

    @Mock
    private BatchStagingSaveRepository mxBatchStagingRepository;
    @Mock
    private MxGraphDbBatchRepository mxGraphDbBatchRepository;
    @InjectMocks
    private MxTransactionsFlusher mxTransactionsFlusher;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(mxTransactionsFlusher, "defaultBatchSize", 100);
        ReflectionTestUtils.setField(mxTransactionsFlusher, "lock", false);
    }

    @Test
    void mxTransactionFlusher() throws Exception {
        mxTransactionsFlusher.mxTransactionFlusher();

        int dbBatchSize = mxTransactionsFlusher.getDbBatchSize();
        verify(mxGraphDbBatchRepository, atMostOnce()).findFirstByOrderById();
        verify(mxBatchStagingRepository, atMostOnce()).flush(anyInt());
        assertEquals(100, dbBatchSize);
    }

    @Test
    void mxTransactionFlusher_fromDb() throws Exception {
        MxGraphDbBatch mxGraphDbBatch = new MxGraphDbBatch();
        mxGraphDbBatch.setBatchSize(200);
        when(mxGraphDbBatchRepository.findFirstByOrderById()).thenReturn(mxGraphDbBatch);

        mxTransactionsFlusher.mxTransactionFlusher();
        int dbBatchSize = mxTransactionsFlusher.getDbBatchSize();

        verify(mxGraphDbBatchRepository, atMostOnce()).findFirstByOrderById();
        verify(mxBatchStagingRepository, atMostOnce()).flush(anyInt());
        assertEquals(200, dbBatchSize);
    }
}