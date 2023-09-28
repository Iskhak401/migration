package io.caden.transformers.netflix;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.caden.transformers.netflix.repositories.NetflixViewingActivityRepository;
import io.caden.transformers.netflix.services.NetflixViewingActivitiesTransformation;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.services.aws.AwsQueueExtendedService;
import io.caden.transformers.shared.services.vault.VaultManagementService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

/**
 * Tests the persistence of Netflix viewing activities in the remote GraphDB storage.
 *
 * @author Mihail Radkov
 */
@ExtendWith(MockitoExtension.class)
class NetflixViewingActivityIntegrationTest {

  // TODO: This is just an example & it requires a running GraphDB. Ideally, you could do it with test containers.

  @Mock
  private VaultManagementService vaultManagementService;

  @Mock
  private NetflixViewingActivityRepository viewingActivityRepository;

  @InjectMocks
  private NetflixViewingActivitiesTransformation viewingActivitiesTransformation;

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Test
  void shouldPersistViewingActivityInGraphDB() throws Exception {
    // Stub the queue service
    Message message = new Message();
    message.setBody(loadTestResource("netflix/transformation-message.json"));

    // Stub S3 vault
    String viewingActivity = loadTestResource("netflix/netflix-viewing-activity.json");
    String viewingActivities = MAPPER.writeValueAsString(new String[]{viewingActivity});
    when(vaultManagementService.getExtractedData(any())).thenReturn(viewingActivities);

    // Main test logic
    viewingActivitiesTransformation.receiveMessages(MAPPER.readValue(message.getBody(), TransformationMessageBodyDto.class));
    verify(viewingActivityRepository, atLeastOnce()).save(any());

    // TODO: You could query the repository and verify the statements
  }

  private static String loadTestResource(String resource) throws IOException {
    return IOUtils.toString(
        Objects.requireNonNull(
            NetflixViewingActivityIntegrationTest.class.getClassLoader().getResource(resource)), StandardCharsets.UTF_8);
  }

}
