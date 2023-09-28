package io.caden.transformers.shared.utils;

import org.joda.time.LocalDateTime;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class RDFNamingConventionUtil {
  private RDFNamingConventionUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static String generateUserGraphName(final String cadenAlias) {
    return MessageFormat.format(
      "caden-user-{0}",
      cadenAlias
    );
  }

  // Generates a unique name for a UserAction based off action timestamp. Generates a new job creation timestamp
  public static String generateUserActionName(final String cadenAlias, final String actionName, final Date actionTimestamp) {
    return generateUserActionName(cadenAlias, actionName, actionTimestamp.getTime() / 1000L, (new Date()).getTime());
  }

  // Generates a unique name for a UserAction based off action timestamp and job creation timestamp
  public static String generateUserActionName(final String cadenAlias, final String actionName, final Date actionTimestamp, final Date creationTimestamp) {
    return generateUserActionName(cadenAlias, actionName, actionTimestamp.getTime() / 1000L, creationTimestamp.getTime());
  }

  public static String generateUserActionName(final String cadenAlias, final String actionName, final Date actionTimestamp, final Date creationTimestamp, final UUID uuid) {
    return generateUserActionName(cadenAlias, actionName, actionTimestamp.getTime(), creationTimestamp.getTime(), uuid);
  }

  // Formats the UserAction name
  private static String generateUserActionName(final String cadenAlias, final String actionName, final long actionTimestamp, final long creationTimestamp) {
    // <<cadenAlias>>_<<class name>>_<<UTC timestamp of when action was performed in Unix epoch>>_<<Creation timestamp in Unix epoch>>
    return MessageFormat.format(
      "{0}_{1}_{2}_{3}",
      cadenAlias,
      actionName,
      actionTimestamp,
      creationTimestamp
    );
  }

  private static String generateUserActionName(final String cadenAlias, final String actionName, final long actionTimestamp, final long creationTimestamp, final UUID uuid) {
    // <<cadenAlias>>_<<class name>>_<<UTC timestamp of when action was performed in Unix epoch>>_<<Creation timestamp in Unix epoch>>
    return MessageFormat.format(
            "{0}/{1}/{2}/{3}/{4}",
            cadenAlias,
            actionName,
            new LocalDateTime(actionTimestamp),
            new LocalDateTime(creationTimestamp),
            uuid
    );
  }

  public static String generateAuxiliaryName(final String cadenAlias, final String actionName, final Date actionTimestamp) {
    return generateAuxiliaryName(cadenAlias, actionName, actionTimestamp.getTime() / 1000L, (new Date()).getTime());
  }

  public static String generateAuxiliaryName(final String cadenAlias, final String actionName, final Date actionTimestamp, UUID uuid) {
    return generateAuxiliaryName(cadenAlias, actionName, actionTimestamp.getTime(), (new Date()).getTime(), uuid);
  }

  public static String generateAuxiliaryName(final String cadenAlias, final String actionName, final Date actionTimestamp, final Date subCreationTime) {
    return generateAuxiliaryName(cadenAlias, actionName, actionTimestamp.getTime() / 1000L, subCreationTime.getTime());
  }

  private static String generateAuxiliaryName(final String auxiliaryName, final String primaryName, final long actionTimestamp, final long creationTimestamp) {
    // <<auxiliary class name>>_<<primary class name>>_<<UTC timestamp of when action was performed in Unix epoch>>_<<Creation timestamp in Unix epoch>>
    return MessageFormat.format(
      "{0}_{1}_{2}_{3}",
      auxiliaryName,
      primaryName,
      actionTimestamp,
      creationTimestamp
    );
  }

  public static String generateAuxiliaryName(final String auxiliaryName, final String primaryName, final Date actionDate, Date creationDate, final UUID uuid) {
    return generateAuxiliaryName(auxiliaryName, primaryName, actionDate.getTime(), creationDate.getTime(), uuid);
  }

  private static String generateAuxiliaryName(final String auxiliaryName, final String primaryName, final long actionTimestamp, final long creationTimestamp, final UUID uuid) {
    return MessageFormat.format(
            "{0}/{1}/{2}/{3}/{4}",
            auxiliaryName,
            primaryName,
            new LocalDateTime(actionTimestamp),
            new LocalDateTime(creationTimestamp),
            uuid
    );
  }

  public static String generateStatusName(final String cadenAlias, final String actionName, final Date actionTimestamp) {
    return generateStatusName(cadenAlias, actionName, actionTimestamp.getTime() / 1000L, (new Date()).getTime());
  }
  public static String generateStatusName(final String cadenAlias, final String actionName, final Date actionTimestamp, final Date creationTimestamp) {
    return generateStatusName(cadenAlias, actionName, actionTimestamp.getTime() / 1000L, creationTimestamp.getTime());
  }
  public static String generateStatusName(final String cadenAlias, final String actionName, final long actionTimestamp, final long creationTimestamp) {
    // Same convention as user action name
    return generateUserActionName(cadenAlias, actionName, actionTimestamp, creationTimestamp);
  }

  public static String generateReferenceName(final String intake, final String identifier) {
    //<<source name>>_<<identifier provided by intake>>
    return MessageFormat.format(
      "{0}_{1}",
      intake,
      identifier
    );
  }

  public static String generateSlashReferenceName(final String intake, final String identifier) {
    //<<source name>>_<<identifier provided by intake>>
    return MessageFormat.format(
            "{0}/{1}",
            intake,
            identifier
    );
  }

  public static Map<String, String> getUserActionIriTokens(final String iri) {
    Map<String, String> tokens = new HashMap<>();

    String[] urlTokens = iri.split("/");
    String[] iriTokens = urlTokens[urlTokens.length - 1].split("_");

    tokens.put("cadenAlias", iriTokens[0]);
    tokens.put("actionName", iriTokens[1]);
    tokens.put("timestamp", iriTokens[2]);
    tokens.put("uuid", iriTokens[3]);

    return tokens;
  }
}
