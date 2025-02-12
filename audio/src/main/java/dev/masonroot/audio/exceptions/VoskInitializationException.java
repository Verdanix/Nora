package dev.masonroot.audio.exceptions;

import java.nio.file.Path;

/**
 * Exception thrown when the Vosk STT engine fails to initialize with the specified model path.
 *
 * <p>This exception is used to indicate issues during the initialization of the Vosk speech
 * recognition engine, such as when the model path is invalid or the model cannot be loaded.
 *
 * <p><b>Notes:</b>
 *
 * <ul>
 *   <li>This exception extends {@code SttEngineInitialization} to provide more specific error
 *       information related to Vosk initialization.
 * </ul>
 */
public final class VoskInitializationException extends EngineInitializationException {
  /**
   * Constructs a new {@code VoskInitializationException} with the specified model path and cause.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>To provide a specific error message related to Vosk initialization failures.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The {@code @NonNull} annotation indicates that the parameter should not be null.
   * </ul>
   *
   * @param modelPath the path to the Vosk model; must not be null.
   * @param cause the cause of the exception; must not be null.
   */
  public VoskInitializationException(Path modelPath, Throwable cause) {
    super("Failed to initialize Vosk engine with model path: " + modelPath, cause);
  }
}
