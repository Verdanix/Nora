package dev.masonroot.common;

import java.nio.file.Files;
import java.nio.file.Path;

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
  public static void throwIfFileIsInsecure(Path file) {
    if (!Files.isReadable(file)
        || !Files.isWritable(file)
        || !Files.isRegularFile(file)
        || Files.isSymbolicLink(file)) {
      throw new SecurityException("File is not secure: " + file);
    }
  }
}
