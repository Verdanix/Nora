package dev.masonroot.nora.audio.exceptions;

import java.nio.file.Path;

/**
 * Exception thrown when the Piper TTS engine fails to initialize with the specified detail message.
 *
 * <p>This exception is used to indicate issues during the initialization of the Piper
 * text-to-speech engine, such as when the model path is invalid or the model cannot be loaded.
 *
 * <p><b>Notes:</b>
 *
 * <ul>
 *   <li>This exception extends {@code EngineInitializationException} to provide more specific error
 *       information related to Piper initialization.
 * </ul>
 */
public final class PiperInitializationException extends EngineInitializationException {

  /**
   * Constructs a new {@code EngineInitialization} with the specified detail message and cause.
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
   * @param dataDirectory the path to the data directory; must not be null.
   * @param cause the cause of the exception; must not be null.
   */
  public PiperInitializationException(Path dataDirectory, Throwable cause) {
    super("Failed to initialize Piper engine with data directory: " + dataDirectory, cause);
  }
}
