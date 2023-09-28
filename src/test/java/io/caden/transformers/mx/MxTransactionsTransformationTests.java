package io.caden.transformers.mx;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.caden.transformers.mx.repositories.BatchStagingSaveRepository;
import io.caden.transformers.mx.repositories.MxTransactionRepository;
import io.caden.transformers.mx.services.MxTransactionsTransformation;
import io.caden.transformers.mx.services.MxTransactionsTransformationHelper;
import io.caden.transformers.shared.services.vault.VaultManagementService;
import io.caden.transformers.util.GenerateObjects;
import lombok.SneakyThrows;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MxTransactionsTransformationTests {
    @Mock
    private VaultManagementService vaultManagementService;
    @Mock
    private MxTransactionRepository mxTransactionRepo;
    @Mock
    private MxTransactionsTransformationHelper mxTransactionsTransformationHelper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private BatchStagingSaveRepository batchStagingSaveRepository;
    @InjectMocks
    private MxTransactionsTransformation mxTransactionsTransformation;

    private final SimpleValueFactory svf = SimpleValueFactory.getInstance();

    @Test
    @SneakyThrows
    void testReceivingMessage() {
        when(vaultManagementService.getExtractedData(any()))
                .thenReturn(GenerateObjects.generateMxTransactionTransformationMessageDto("test_account", "test_id").toString());
        when(objectMapper.readValue(anyString(), any(Class.class)))
                .thenReturn(GenerateObjects.generateMxTransactionTransformationMessageDto("test_account", "test_id"));

        mxTransactionsTransformation.receiveMessage(GenerateObjects.generateTransformationMessageBodyDto());
        verify(mxTransactionRepo).exists(anyString(), anyString());
        verify(mxTransactionsTransformationHelper).transformTransaction(anyString(), any(), any());
        verify(batchStagingSaveRepository, atMostOnce()).queue(any(),any(), any() , any());
        verifyNoMoreInteractions(mxTransactionsTransformationHelper);
        verifyNoMoreInteractions(mxTransactionRepo);
    }
}
