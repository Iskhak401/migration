package io.caden.transformers.netflix.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetflixApiProfileInfo {
  private String guid;
  private String profileName;
}
