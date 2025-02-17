package dev.masonroot.nora.audio.stt;

import dev.masonroot.nora.audio.AudioInterface;
import dev.masonroot.nora.audio.exceptions.VoskInitializationException;
import dev.masonroot.nora.common.SecurityUtils;
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
public final class VoskSttEngine implements SttEngine {
  /** The audio interface for handling audio input and output. */
  private AudioInterface audioInterface;

  /** The Vosk model used for speech recognition. */
  private Model model;

  /** The Vosk recognizer used for transcribing audio data. */
  private Recognizer recognizer;

  @Override
  public void initialize(@NonNull final AudioInterface audio, @NonNull final Path modelPath) {
    LibVosk.setLogLevel(LogLevel.INFO);
    SecurityUtils.throwIfIsNotDirectory(modelPath);
    this.audioInterface = audio;
    try {
      this.model = new Model(modelPath.toString());
      this.recognizer = new Recognizer(this.model, audio.microphone().getFormat().getSampleRate());
    } catch (IOException e) {
      throw new VoskInitializationException(modelPath, e);
    }
  }

  @Override
  public synchronized String transcribe(long timeoutInMs) {
    byte[] bytes = this.audioInterface.read(timeoutInMs);
    this.recognizer.acceptWaveForm(bytes, bytes.length);
    return this.trimFinalResult(this.recognizer.getFinalResult());
  }

  /**
   * Trims the final result from the Vosk recognizer to extract the recognized text.
   *
   * <p>This method removes the JSON structure from the result string, leaving only the recognized
   * text. For example, it transforms:
   *
   * <pre>
   * {
   *   "text": "hello"
   * }
   * </pre>
   *
   * <p>into:
   *
   * <pre>
   * hello
   * </pre>
   *
   * @param result the JSON string containing the recognized text
   * @return the extracted text from the JSON result
   */
  private String trimFinalResult(String result) {
    /*
       Before:
       {
         "text": "hello"
       }

       After:
       hello

       This regex replaces everything except the text value between the double quotes.
    */
    return result.replaceAll("(\\{\\s+[\"\\w\\s:\\s]+\\s\"|\"\\s+\\})", "");
  }

  @Override
  public void close() {
    if (this.recognizer != null) this.recognizer.close();
    if (this.model != null) this.model.close();
  }
}
