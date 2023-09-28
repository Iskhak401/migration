package io.caden.transformers.megraph.enums;

public enum EmploymentStatus {
  unemployed("Unemployed/student"),
  selfEmployed("Self employed"),
  partTime("Part time"),
  fullTime("Full time");

  public final String label;

  private EmploymentStatus(final String label) {
    this.label = label;
  }
}
