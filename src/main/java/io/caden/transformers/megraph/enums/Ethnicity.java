package io.caden.transformers.megraph.enums;

public enum Ethnicity {
  americanIndian("American Indian"),
  pacificIslander("Pacific Islander"),
  black("Black/African Descent"),
  white("White"),
  asian("Asian"),
  other("Other");

  public final String label;

  private Ethnicity(final String label) {
    this.label = label;
  }
}
