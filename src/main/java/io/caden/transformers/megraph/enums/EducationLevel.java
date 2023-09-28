package io.caden.transformers.megraph.enums;

public enum EducationLevel {
  someSchool("Some school"),
  highSchool("High school diploma, GED or equivalent"),
  associatesDegree("Associates degree"),
  undergraduateDegree("Undergraduate degree"),
  master("Masters degree"),
  professionalDegree("Professional degree"),
  doctorate("Doctorate degree"),
  technical("Trade, technical, vocational training");

  public final String label;

  private EducationLevel(final String label) {
    this.label = label;
  }
}
