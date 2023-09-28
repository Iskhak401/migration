package io.caden.transformers.megraph.enums;

public enum Sexuality {
  straight("Straight"),
  lesbian("Lesbian"),
  gay("Gay"),
  bisexual("Bisexual"),
  pansexual("Pansexual"),
  asexual("Asexual"),
  preferNotToSay("Prefer not to say");

  public final String label;

  private Sexuality(final String label) {
    this.label = label;
  }
}
