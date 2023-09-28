package io.caden.transformers.location.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.caden.transformers.location.entities.LocationGraphDbBatch;
import io.caden.transformers.location.repositories.BatchMoveActionGraphDbRepository;
import io.caden.transformers.location.repositories.LocationGraphDbBatchRepository;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.services.vault.VaultManagementService;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationTransformationTest {

    @Mock
    private VaultManagementService vaultManagementService;
    @Mock
    private BatchMoveActionGraphDbRepository batchMoveActionGraphDbRepository;
    @Mock
    private LocationGraphDbBatchRepository locationGraphDbBatchRepository;
    @InjectMocks
    private LocationTransformation locationTransformation;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(locationTransformation, "defaultBatchSize", 100);
    }

    @Test
    void execute() throws Exception {

        when(vaultManagementService.getExtractedData(any())).thenReturn(objectMapper.writeValueAsString(generateLocationObject()));

        locationTransformation.receiveMessage(generateMessageBodyDto());

        verify(batchMoveActionGraphDbRepository, atMostOnce()).queue(anyList(), any());
    }

    @Test
    void flush() throws Exception {
        when(locationGraphDbBatchRepository.findFirstByOrderById()).thenReturn(null);

        locationTransformation.flush();

        verify(batchMoveActionGraphDbRepository).flush(eq(100));
    }

    @Test
    void flush_fromDB() throws Exception {
        LocationGraphDbBatch locationGraphDbBatch = new LocationGraphDbBatch();
        locationGraphDbBatch.setBatchSize(50);
        when(locationGraphDbBatchRepository.findFirstByOrderById()).thenReturn(locationGraphDbBatch);

        locationTransformation.flush();

        verify(batchMoveActionGraphDbRepository).flush(eq(50));
    }

    TransformationMessageBodyDto generateMessageBodyDto() {
        TransformationMessageBodyDto transformationMessageBodyDto = new TransformationMessageBodyDto();
        transformationMessageBodyDto.setAccessKeyId("test");
        transformationMessageBodyDto.setCadenAlias(UUID.randomUUID());
        transformationMessageBodyDto.setPath(UUID.randomUUID()+"/mx/"+UUID.randomUUID());
        transformationMessageBodyDto.setSessionToken("test");
        transformationMessageBodyDto.setSecretAccessKey("test");
        return transformationMessageBodyDto;
    }

    LocationObject generateLocationObject() {
        LocationObject locationObject = new LocationObject();
        locationObject.setDate(1692284909l);
        locationObject.setCadenAlias(UUID.randomUUID());
        locationObject.setLatitude(40.00);
        locationObject.setLongitude(160.00);
        return locationObject;
    }

    @Data
    class LocationObject {
        private UUID cadenAlias;
        private Long date;
        private Double latitude;
        private Double longitude;
    }
}