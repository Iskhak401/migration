package io.caden.transformers.netflix.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.caden.transformers.netflix.dtos.NetflixApiProfileInfo;
import io.caden.transformers.netflix.dtos.NetflixApiViewedItem;
import io.caden.transformers.netflix.dtos.NetflixTransformationDto;
import io.caden.transformers.netflix.entities.NetflixGraphDbBatch;
import io.caden.transformers.netflix.repositories.BatchViewingActivityGraphDbRepository;
import io.caden.transformers.netflix.repositories.NetflixGraphDbBatchRepository;
import io.caden.transformers.netflix.repositories.NetflixViewingActivityRepository;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.services.vault.VaultManagementService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class NetflixViewingActivitiesTransformationTest {
    @Mock
    private VaultManagementService vaultManagementService;
    @Mock
    private NetflixViewingActivityRepository netflixViewingActivityRepo;
    @Mock
    private BatchViewingActivityGraphDbRepository batchSaveRepository;
    @Mock
    private NetflixGraphDbBatchRepository netflixGraphDbBatchRepository;
    @InjectMocks
    private NetflixViewingActivitiesTransformation netflixViewingActivitiesTransformation;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(netflixViewingActivitiesTransformation, "defaultBatchSize", 100);
    }

    @Test
    void receiveMessages() throws Exception {
        TransformationMessageBodyDto dto = new TransformationMessageBodyDto();
        UUID cognitoId = UUID.randomUUID();
        UUID cadenAlias = UUID.randomUUID();
        dto.setDatabaseEncryptionKey("test");
        dto.setPath(cognitoId+"/test/asdfasdg");
        dto.setCadenAlias(cadenAlias);

        NetflixTransformationDto netflixTransformationDto = new NetflixTransformationDto();
        NetflixApiProfileInfo netflixApiProfileInfo = new NetflixApiProfileInfo();
        netflixApiProfileInfo.setGuid(UUID.randomUUID().toString());
        netflixApiProfileInfo.setProfileName("test");
        netflixTransformationDto.setProfileInfo(netflixApiProfileInfo);
        NetflixApiViewedItem netflixApiViewedItem = new NetflixApiViewedItem();
        netflixApiViewedItem.setDate(new Date().getTime());
        netflixApiViewedItem.setTitle("test test");
        netflixApiViewedItem.setSeries(123l);
        netflixApiViewedItem.setEpisodeTitle("the test tested");
        netflixApiViewedItem.setSeriesTitle("something something");
        netflixApiViewedItem.setMovieID(123l);
        netflixTransformationDto.setViewedItems(Lists.newArrayList(netflixApiViewedItem));

        String data = objectMapper.writeValueAsString(netflixTransformationDto);
        when(vaultManagementService.getExtractedData(any())).thenReturn(data);

        netflixViewingActivitiesTransformation.receiveMessages(dto);

        verify(batchSaveRepository, atMostOnce()).queue(anyList(), any());
    }

    @Test
    void flush() throws Exception {
        when(netflixGraphDbBatchRepository.findFirstByOrderById()).thenReturn(null);

        netflixViewingActivitiesTransformation.flush();

        verify(batchSaveRepository).flush(eq(100));
    }

    @Test
    void flush_fromDB() throws Exception {
        NetflixGraphDbBatch netflixGraphDbBatch = new NetflixGraphDbBatch();
        netflixGraphDbBatch.setBatchSize(50);
        when(netflixGraphDbBatchRepository.findFirstByOrderById()).thenReturn(netflixGraphDbBatch);

        netflixViewingActivitiesTransformation.flush();

        verify(batchSaveRepository).flush(eq(50));
    }




}