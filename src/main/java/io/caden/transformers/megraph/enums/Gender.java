package io.caden.transformers.megraph.enums;

public enum Gender {
  male("Male"),
  female("Female"),
  nonBinary("Non-binary"),
  preferNotToSay("Prefer not to say");

  public final String label;

  private Gender(final String label) {
    this.label = label;
  }
}
