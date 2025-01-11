package dev.masonroot.audio.stt;

import dev.masonroot.audio.AudioInterface;
import dev.masonroot.common.NoraLogger;
import java.io.IOException;
import java.nio.file.Path;
import lombok.NonNull;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

/**
 * Vosk STT engine for converting spoken language into written text using the Vosk speech
 * recognition library.
 *
 * <p>This class provides methods to initialize the STT engine, transcribe audio data, and manage
 * resources. It uses the Vosk library to perform speech recognition tasks.
 *
 * <p><b>Thread Safety:</b>
 *
 * <ul>
 *   <li>This class is thread-safe. The read and write methods are synchronized to prevent
 *       concurrent access.
 * </ul>
 *
 * <p><b>Notes:</b>
 *
 * <ul>
 *   <li>This class implements {@code AutoCloseable} to ensure that audio resources are properly
 *       released.
 *   <li>Ensure that the microphone and speaker lines are properly initialized before using this
 *       class.
 * </ul>
 */
public class VoskSttEngine implements SttEngine {
  private AudioInterface audioInterface;
  private Model model;
  private Recognizer recognizer;

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
   * @param audio the {@code AudioInterface} for handling audio input; must not be null.
   * @param modelPath the path to the STT model; must not be null.
   */
  @Override
  public void initialize(@NonNull final AudioInterface audio, @NonNull final Path modelPath) {
    LibVosk.setLogLevel(LogLevel.INFO);
    this.audioInterface = audio;
    try {
      this.model = new Model(modelPath.toString());
      this.recognizer = new Recognizer(this.model, audio.microphone().getFormat().getSampleRate());
    } catch (IOException e) {
      NoraLogger.trace("Failed to load Vosk model.", e);
    }
  }

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
   * @param timeoutInMs the timeout in milliseconds for reading audio data.
   * @return the transcribed text.
   */
  @Override
  public synchronized String transcribe(long timeoutInMs) {
    NoraLogger.info("Transcribing audio...");
    byte[] bytes = this.audioInterface.read(timeoutInMs);
    this.recognizer.acceptWaveForm(bytes, bytes.length);
    return this.trimFinalResult(this.recognizer.getFinalResult());
  }

  private String trimFinalResult(String result) {
    return result.replaceAll("(\\{\\s+[\"\\w\\s:\\s]+\\s\"|\"\\s+\\})", "");
  }

  @Override
  public void close() throws Exception {
    if (this.model == null) {
      return;
    }
    this.model.close();
  }
}
