package dev.masonroot.nora.audio.stt;

import dev.masonroot.nora.audio.AudioInterface;
import dev.masonroot.nora.audio.exceptions.EngineInitializationException;
import java.nio.file.Path;
import lombok.NonNull;

/**
 * Speech-to-Text (STT) engine interface for converting spoken language into written text.
 *
 * <p>This interface defines the methods required for initializing an STT engine, transcribing audio
 * data, and managing resources. Implementations of this interface should provide the necessary
 * functionality to perform speech recognition tasks.
 *
 * <p><b>Thread Safety:</b>
 *
 * <ul>
 *   <li>Implementations should ensure thread safety when accessing shared resources.
 * </ul>
 *
 * <p><b>Notes:</b>
 *
 * <ul>
 *   <li>Implementations should handle any necessary audio processing and transcription logic.
 * </ul>
 */
public interface SttEngine extends AutoCloseable {
  /**
   * Initializes the STT engine with the specified audio interface and model path.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>To set up the necessary resources for speech-to-text conversion.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The {@code @NonNull} annotation indicates that the parameters should not be null.
   * </ul>
   *
   * @param audio the {@code AudioInterface} for handling audio input; must not be null
   * @param modelPath the path to the STT model; must not be null.
   * @throws EngineInitializationException if the STT engine fails to initialize.
   * @throws NullPointerException if the AudioInterface is null.
   */
  void initialize(@NonNull final AudioInterface audio, final Path modelPath)
      throws EngineInitializationException;

  /**
   * Transcribes audio data to text.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>To convert spoken language into written text.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The method should handle any necessary audio processing and transcription logic.
   * </ul>
   *
   * @param timeoutInMs the timeout in milliseconds for the transcription process.
   * @return the transcribed text.
   */
  String transcribe(final long timeoutInMs);
}
