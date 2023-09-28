package io.caden.transformers.megraph.enums;

public enum Relationship {
  single("Single"),
  inRelationship("In a relationship"),
  inDomesticPartnership("In a domestic partnership"),
  married("Married"),
  preferNotToSay("Prefer not to say");

  public final String label;

  private Relationship(final String label) {
    this.label = label;
  }
}
