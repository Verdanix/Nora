package dev.masonroot.nora.common.lang;

import java.util.ResourceBundle;
import lombok.Getter;
import lombok.NonNull;

/**
 * Utility class for translating messages using resource bundles.
 *
 * <p>This class provides static methods to translate messages based on the system locale using
 * resource bundles. It uses the {@code NoraLocales} enum to manage different locales and their
 * associated resource directories.
 *
 * <p><b>Thread Safety:</b>
 *
 * <ul>
 *   <li>This class is thread-safe. All methods are static and do not modify any shared state.
 * </ul>
 *
 * <p><b>Notes:</b>
 *
 * <ul>
 *   <li>This class cannot be instantiated.
 *   <li>Ensure the resource bundles for the supported locales are available in the specified
 *       directories.
 * </ul>
 */
@Getter
public final class Translator {
  /** The system locale to use for translations. TODO: Add support for plugin translations. */
  private static NoraLocales systemLocale = NoraLocales.EN_US;

  private Translator() {}

  /**
   * Retrieves the resource bundle for the given locale.
   *
   * <p>This method retrieves the resource bundle corresponding to the given locale. It is useful
   * for translating messages based on a specific locale.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Retrieving the resource bundle for a specific locale allows the application to support
   *       multiple languages and provide a localized user experience.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The {@code @NonNull} annotation indicates that the locale parameter should not be null.
   *   <li>Ensure that the resource bundles for the supported locales contain the necessary keys.
   * </ul>
   *
   * @param locale the locale for which to retrieve the resource bundle; must not be null
   * @return the resource bundle for the given locale
   */
  private static ResourceBundle getBundle(@NonNull final NoraLocales locale) {
    return ResourceBundle.getBundle(
        locale.getResourceDir().resolve("lang").toString(), locale.getLocale());
  }

  /**
   * Translates a message key to its corresponding message using the system locale.
   *
   * <p>This method retrieves the message associated with the given key from the resource bundle
   * corresponding to the system locale. It is useful for internationalizing messages in the
   * application.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Translating messages based on the system locale allows the application to support
   *       multiple languages and provide a localized user experience.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The {@code @NonNull} annotation indicates that the key parameter should not be null.
   *   <li>Ensure that the resource bundles for the supported locales contain the necessary keys.
   * </ul>
   *
   * @param key the message key to be translated; must not be null
   * @return the translated message corresponding to the given key
   */
  public static String translate(@NonNull final String key) {
    return Translator.getBundle(systemLocale).getString(key);
  }

  /**
   * Sets the system locale to use for translations.
   *
   * <p>This method sets the system locale to the given locale. It is useful for changing the locale
   * used for translating messages in the application.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Changing the system locale allows the application to provide a localized user experience
   *       based on the user's preferences.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The {@code @NonNull} annotation indicates that the locale parameter should not be null.
   *   <li>Ensure that the resource bundles for the supported locales contain the necessary keys.
   * </ul>
   *
   * @param locale the locale to set as the system locale; must not be null
   */
  public static void setSystemLocale(@NonNull final NoraLocales locale) {
    Translator.systemLocale = locale;
  }
}
