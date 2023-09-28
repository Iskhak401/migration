package io.caden.transformers.shared.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Error payload to be logged after encountering an issue
 * with data schema changes
 */
public class DataSchemaChangeDto {
  private String html;
  private String path;

  public static DataSchemaChangeDto fromHtml(String html) {
    DataSchemaChangeDto dto = new DataSchemaChangeDto();
    dto.setHtml(html);

    return dto;
  }

  public static DataSchemaChangeDto fromPath(String path) {
    DataSchemaChangeDto dto = new DataSchemaChangeDto();
    dto.setPath(path);

    return dto;
  }

  public String getPath() {
    return this.path;
  }
  public void setPath(String path) {
    this.path = path;
  }

  public String getHtml() {
    return this.html;
  }
  public void setHtml(String html) {
    this.html = html;
  }

  @Override
  public String toString() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      return "";
    }
  }
}
