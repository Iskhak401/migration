package io.caden.transformers.shared.dtos;

import java.util.UUID;

public class DataTransformationRequestDto {
  private UUID extractionId;
  private UUID cognitoId;

  public UUID getExtractionId() {
    return this.extractionId;
  }

  public void setExtractionId(final UUID extractionId) {
    this.extractionId = extractionId;
  }

  public UUID getCognitoId() {
    return this.cognitoId;
  }

  public void setCognitoId(final UUID cognitoId) {
    this.cognitoId = cognitoId;
  }
}
