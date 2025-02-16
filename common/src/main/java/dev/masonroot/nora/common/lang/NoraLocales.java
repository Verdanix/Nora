package dev.masonroot.nora.common.lang;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum for managing different locales.
 *
 * <p>This enum provides constants for different locales and their associated resource directories.
 * It is used by the {@code Translator} class to retrieve resource bundles for translations.
 *
 * <p><b>Thread Safety:</b>
 *
 * <ul>
 *   <li>This enum is thread-safe. All constants are immutable and do not modify any shared state.
 * </ul>
 *
 * <p><b>Notes:</b>
 *
 * <ul>
 *   <li>Ensure the resource directories for the supported locales are available and contain the
 *       necessary resource bundles.
 * </ul>
 */
@Getter
@AllArgsConstructor
public enum NoraLocales {
  /** The English (United States) locale. */
  EN_US(Paths.get("lang/en_US/"), new Locale("en, US")),
  /** The English (United Kingdom) locale. */
  EN_UK(Paths.get("lang/en_UK/"), new Locale("en, UK"));

  /** The directory containing the resource bundles for the locale. */
  private final Path resourceDir;

  /** The locale object for the locale. */
  private final Locale locale;
}
