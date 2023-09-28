package io.caden.transformers.megraph.enums;

public enum YearlyHouseholdIncome {
  lessThan20k("$0-$19,999"),
  between20kAnd49k("$20,000-$49,999"),
  between50kAnd89k("$50,000-$89,999"),
  between90kAnd149k("$90,000-$149,999"),
  moreThan150k("$150,000+"),
  preferNotToSay("Prefer not to say");

  public final String label;

  private YearlyHouseholdIncome(final String label) {
    this.label = label;
  }
}
