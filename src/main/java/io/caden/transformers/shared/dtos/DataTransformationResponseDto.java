package io.caden.transformers.shared.dtos;

import java.util.UUID;

public class DataTransformationResponseDto {
   // Temporary Access Key (TAK): AccessKeyId from AWS STS
   private String accessKeyId;
   // Temporary Access Key (TAK): SecretAccessKey from AWS STS
   private String secretAccessKey;
   // Temporary Access Key (TAK): SessionToken from AWS STS
  private String sessionToken;
  private String databaseEncryptionKey;
  private UUID cadenAlias;

  public String getAccessKeyId() {
    return this.accessKeyId;
  }

  public void setAccessKeyId(final String accessKeyId) {
    this.accessKeyId = accessKeyId;
  }

  public String getSecretAccessKey() {
    return this.secretAccessKey;
  }

  public void setSecretAccessKey(final String secretAccessKey) {
    this.secretAccessKey = secretAccessKey;
  }

  public String getSessionToken() {
    return this.sessionToken;
  }

  public void setSessionToken(final String sessionToken) {
    this.sessionToken = sessionToken;
  }

  public String getDatabaseEncryptionKey() {
    return this.databaseEncryptionKey;
  }

  public void setDatabaseEncryptionKey(final String databaseEncryptionKey) {
    this.databaseEncryptionKey = databaseEncryptionKey;
  }

  public UUID getCadenAlias() {
    return this.cadenAlias;
  }

  public void setCadenAlias(final UUID cadenAlias) {
    this.cadenAlias = cadenAlias;
  }
}
