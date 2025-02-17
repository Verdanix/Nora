package dev.masonroot.nora.common;

import dev.masonroot.nora.common.lang.Translator;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.NonNull;

/**
 * Security utilities for different operations.
 *
 * <p>This class provides methods to check the security of different operations.
 *
 * <p><b>Thread Safety:</b>
 *
 * <ul>
 *   <li>This class is thread-safe. The methods are designed to be used concurrently without any
 *       issues.
 * </ul>
 *
 * <p><b>Notes:</b>
 *
 * <ul>
 *   <li>Ensure that the file paths provided are valid and accessible.
 * </ul>
 */
public class SecurityUtils {
  /**
   * Checks if the file is secure for interaction.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>To ensure that the file is readable, writable, and not a symbolic link.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The {@code @NonNull} annotation indicates that the parameter should not be null.
   * </ul>
   *
   * @param file the file to check; must not be null.
   * @throws SecurityException if the file is insecure.
   */
  public static void throwIfFileIsInsecure(@NonNull final Path file) {
    if (!Files.isReadable(file)
        || !Files.isWritable(file)
        || !Files.isRegularFile(file)
        || Files.isSymbolicLink(file)) {
      throw new SecurityException(
          String.format("%s: %s", Translator.translate("common.exceptions.insecureFile"), file));
    }
  }

  /**
   * Checks if the file is a directory.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>To ensure that the file is a directory.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The {@code @NonNull} annotation indicates that the parameter should not be null.
   * </ul>
   *
   * @param file the file to check; must not be null.
   * @throws SecurityException if the file is not a directory.
   */
  public static void throwIfIsNotDirectory(@NonNull final Path file) {
    if (!Files.isDirectory(file)) {
      throw new SecurityException(
          String.format(
              "%s: %s", Translator.translate("common.exceptions.pathNotDirectory"), file));
    }
  }
}
