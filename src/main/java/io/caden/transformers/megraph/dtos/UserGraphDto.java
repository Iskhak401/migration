package io.caden.transformers.megraph.dtos;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserGraphDto {
  private Map<String, String> aliases;
  private MeGraphDto meGraph;

  public Map<String, String> getAliases() {
    return aliases;
  }

  public void setAliases(Map<String, String> aliases) {
    this.aliases = aliases;
  }

  public MeGraphDto getMeGraph() {
    return meGraph;
  }

  public void setMeGraph(MeGraphDto meGraph) {
    this.meGraph = meGraph;
  }

  @JsonIgnore
  public String getCadenAlias() {
    return this.aliases.getOrDefault("cadenAlias", null);
  }
}
