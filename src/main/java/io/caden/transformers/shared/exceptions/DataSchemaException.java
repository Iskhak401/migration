package io.caden.transformers.shared.exceptions;

import io.caden.transformers.shared.dtos.DataSchemaChangeDto;

/**
 * Thrown when an issue is encountered trying to parse extraction data.
 */
public class DataSchemaException extends RuntimeException {
  public DataSchemaException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }

  public DataSchemaException(DataSchemaChangeDto dto, Throwable err) {
    super(dto.toString(), err);
  }

  public DataSchemaException(DataSchemaChangeDto dto) {
    super(dto.toString());
  }
}