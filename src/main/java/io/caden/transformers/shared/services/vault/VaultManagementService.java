package io.caden.transformers.shared.services.vault;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import io.caden.transformers.shared.dtos.TransformationMessageBodyDto;
import io.caden.transformers.shared.services.encryption.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class VaultManagementService {
  private final AmazonS3 client;
  private final EncryptionService encryptionService;

  @Value("${aws.s3.bucket.name}")
  private String bucketName;

  public String getExtractedData(final TransformationMessageBodyDto dto) throws Exception {
    byte[] data = getObjectAsBytes(this.bucketName, dto.getPath());
    return encryptionService.decrypt(dto.getDatabaseEncryptionKey(), data);
  }

  private byte[] getObjectAsBytes(final String bucketName, final String key) throws IOException {
    S3Object object = client.getObject(bucketName, key);
    return IOUtils.toByteArray(object.getObjectContent());
  }
}
