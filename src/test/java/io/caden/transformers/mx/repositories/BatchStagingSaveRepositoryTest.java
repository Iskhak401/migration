package io.caden.transformers.mx.repositories;

import io.caden.transformers.config.RDFConfiguration;
import io.caden.transformers.mx.entities.BatchMxTransaction;
import io.caden.transformers.mx.entities.MxTransaction;
import io.caden.transformers.shared.repositories.UserGraphRepository;
import io.caden.transformers.shared.utils.ExecuteStatements;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatchStagingSaveRepositoryTest {

    @Mock
    private SimpleValueFactory svf;
    @Mock
    private BatchMxTransactionsRepository batchStageRepository;
    @Mock
    private MxTransactionRepository mxTransactionRepo;
    @Mock
    private UserGraphRepository userGraphRepository;
    @Mock
    private RDFConfiguration rdfConfiguration;
    @InjectMocks
    private BatchStagingSaveRepository batchStagingSaveRepository;

    @Mock
    private Page<BatchMxTransaction> records;

    @Test
    void flush() throws Exception {
        batchStagingSaveRepository.setSvf(svf);
        when(records.hasContent()).thenReturn(true);
        when(records.getContent()).thenReturn(generateBatchMxTransactions(10, false));
        when(mxTransactionRepo.exists(any(), anyString())).thenReturn(false);

        ExecuteStatements executeStatements = mock(ExecuteStatements.class);
        executeStatements.addAllToReferenceGraph(List.of(mock(Statement.class)));
        executeStatements.addAllToUserGraph(List.of(mock(Statement.class)));
        when(mxTransactionRepo.buildStatements(any())).thenReturn(executeStatements);
        when(batchStageRepository.findAll(any(Pageable.class))).thenReturn(records);

        batchStagingSaveRepository.flush(10);

        verify(mxTransactionRepo, times(10)).buildStatements(any());
        verify(userGraphRepository, atMostOnce()).save(any());
        verify(batchStageRepository, atMostOnce()).deleteAllByIdInBatch(any());
    }

    @Test
    void flush_whenNothingToFlush() throws Exception {
        batchStagingSaveRepository.setSvf(svf);
        when(records.hasContent()).thenReturn(false);
        when(batchStageRepository.findAll(any(Pageable.class))).thenReturn(records);

        batchStagingSaveRepository.flush(1);

        verify(userGraphRepository, never()).save(any());
        verify(batchStageRepository, never()).deleteAllByIdInBatch(any());
    }

    @Test
    void flush_whenDuplicationIsPresent() throws Exception {
        batchStagingSaveRepository.setSvf(svf);
        when(records.hasContent()).thenReturn(true);
        when(records.getContent()).thenReturn(generateBatchMxTransactions(10, true));
        when(mxTransactionRepo.exists(any(), anyString())).thenReturn(false);

        ExecuteStatements executeStatements = mock(ExecuteStatements.class);
        executeStatements.addAllToReferenceGraph(List.of(mock(Statement.class)));
        executeStatements.addAllToUserGraph(List.of(mock(Statement.class)));
        when(mxTransactionRepo.buildStatements(any())).thenReturn(executeStatements);
        when(batchStageRepository.findAll(any(Pageable.class))).thenReturn(records);

        batchStagingSaveRepository.flush(11);

        verify(mxTransactionRepo, times(10)).buildStatements(any());
        verify(userGraphRepository, atMostOnce()).save(any());
        verify(batchStageRepository, atMostOnce()).deleteAllByIdInBatch(any());
    }

    @Test
    void flush_whenRecordsExists() throws Exception {
        batchStagingSaveRepository.setSvf(svf);
        when(records.hasContent()).thenReturn(true);
        when(records.getContent()).thenReturn(generateBatchMxTransactions(10, false));
        when(mxTransactionRepo.exists(any(), anyString())).thenReturn(true).thenReturn(false);

        ExecuteStatements executeStatements = mock(ExecuteStatements.class);
        executeStatements.addAllToReferenceGraph(List.of(mock(Statement.class)));
        executeStatements.addAllToUserGraph(List.of(mock(Statement.class)));
        when(mxTransactionRepo.buildStatements(any())).thenReturn(executeStatements);
        when(batchStageRepository.findAll(any(Pageable.class))).thenReturn(records);

        batchStagingSaveRepository.flush(10);

        verify(mxTransactionRepo, times(9)).buildStatements(any());
        verify(userGraphRepository, atMostOnce()).save(any());
        verify(batchStageRepository, atMostOnce()).deleteAllByIdInBatch(any());
    }

    @Test
    void flush_whenRecordsExistsAndDupsPresent() throws Exception {
        batchStagingSaveRepository.setSvf(svf);
        when(records.hasContent()).thenReturn(true);
        when(records.getContent()).thenReturn(generateBatchMxTransactions(10, true));
        when(mxTransactionRepo.exists(any(), anyString())).thenReturn(true).thenReturn(false);

        ExecuteStatements executeStatements = mock(ExecuteStatements.class);
        executeStatements.addAllToReferenceGraph(List.of(mock(Statement.class)));
        executeStatements.addAllToUserGraph(List.of(mock(Statement.class)));
        when(mxTransactionRepo.buildStatements(any())).thenReturn(executeStatements);
        when(batchStageRepository.findAll(any(Pageable.class))).thenReturn(records);

        batchStagingSaveRepository.flush(10);

        verify(mxTransactionRepo, times(9)).buildStatements(any());
        verify(userGraphRepository, atMostOnce()).save(any());
        verify(batchStageRepository, atMostOnce()).deleteAllByIdInBatch(any());
    }

    private List<BatchMxTransaction> generateBatchMxTransactions(int n, boolean dup) {
        List<BatchMxTransaction> batchMxTransactions = new LinkedList<>();
        String dupMxTransactionUUID = "";
        for (int i = 0; i < n; i++) {
            BatchMxTransaction batchMxTransaction = new BatchMxTransaction();
            MxTransaction mxTransaction = new MxTransaction();
            mxTransaction.setCadenAlias(UUID.randomUUID().toString());
            mxTransaction.setMxGuid("TR-" + UUID.randomUUID());
            dupMxTransactionUUID = mxTransaction.getMxGuid();
            batchMxTransaction.setTransaction(mxTransaction);
            batchMxTransaction.setCognitoId(UUID.randomUUID());
            batchMxTransactions.add(batchMxTransaction);
        }

        if(dup) {
            BatchMxTransaction batchMxTransaction = new BatchMxTransaction();
            MxTransaction mxTransaction = new MxTransaction();
            mxTransaction.setMxGuid(dupMxTransactionUUID);
            batchMxTransaction.setTransaction(mxTransaction);
            batchMxTransactions.add(batchMxTransaction);
        }

        return batchMxTransactions;
    }
}