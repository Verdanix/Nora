package dev.masonroot.audio.exceptions;

/**
 * Exception thrown when an engine fails to initialize.
 *
 * <p>This exception is used to indicate issues during the initialization of an engine, such as when
 * the model path is invalid or the model cannot be loaded.
 *
 * <p><b>Notes:</b>
 *
 * <ul>
 *   <li>This exception extends {@code RuntimeException} to provide more specific error information
 *       related to an engine initialization.
 * </ul>
 */
public class EngineInitializationException extends RuntimeException {
  /**
   * Constructs a new {@code EngineInitialization} with the specified detail message.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>To provide a specific error message related to an engine initialization failures.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The {@code @NonNull} annotation indicates that the parameter should not be null.
   * </ul>
   *
   * @param message the detail message; must not be null.
   */
  public EngineInitializationException(String message) {
    super(message);
  }
}
