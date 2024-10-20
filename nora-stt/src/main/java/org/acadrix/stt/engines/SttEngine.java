package org.acadrix.stt.engines;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import org.acadrix.logging.NoraLogger;

/**
 * The {@code SttEngine} interface provides a common interface for speech-to-text (STT) engines.
 * Implementations of this interface should provide methods for initializing the engine, listening
 * for audio input, and returning the recognized text.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * SttEngine engine = new VoskEngine();
 * engine.initialize(logger, "model", 16000, 16, 1);
 * String text = engine.listen();
 * System.out.println("Recognized text: " + text);
 * }</pre>
 *
 * <p>Implementations of this interface should be thread-safe and should provide a way to start and
 * stop listening for audio input.
 *
 * <p>See also:
 *
 * <ul>
 *   <li>{@link VoskEngine}
 *   <li>{@link NoraLogger}
 *   <li>{@link AudioFormat}
 *   <li>{@link TargetDataLine}
 * </ul>
 */
public interface SttEngine extends AutoCloseable {

  /**
   * Initializes the speech-to-text engine with the specified parameters.
   *
   * <p>This method sets up the engine with the provided model, sample rate, sample size, and number
   * of channels. It also configures the logger for the engine. Implementations should ensure that
   * the engine is ready to start processing audio input after this method is called.
   *
   * @param logger the {@link NoraLogger} instance to be used for logging
   * @param model the model to be used by the engine
   * @param sampleRate the sample rate of the audio input
   * @param sampleSize the sample size of the audio input
   * @param channels the number of audio channels
   * @return {@code true} if the initialization was successful, {@code false} otherwise
   * @throws IOException if an I/O error occurs during initialization
   * @throws LineUnavailableException if the audio line cannot be opened
   * @see NoraLogger
   * @see IOException
   * @see LineUnavailableException
   */
  boolean initialize(
      NoraLogger logger, String model, float sampleRate, int sampleSize, int channels)
      throws IOException, LineUnavailableException;

  /**
   * Listens for audio input and returns the recognized text.
   *
   * <p>This method starts the speech-to-text engine to listen for audio input from the configured
   * audio source. It processes the audio input and returns the recognized text as a {@code String}.
   * Implementations should ensure that the engine is properly initialized and ready to process
   * audio input before calling this method.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * SttEngine engine = new VoskEngine();
   * engine.initialize(logger, "model", 16000, 16, 1);
   * String text = engine.listen();
   * System.out.println("Recognized text: " + text);
   * }</pre>
   *
   * @return the recognized text as a {@code String}
   */
  String listen();

  /**
   * Retrieves the audio format used by the speech-to-text engine.
   *
   * <p>This method returns the {@link AudioFormat} instance that describes the format of the audio
   * data that the engine is configured to process. The audio format includes information such as
   * the sample rate, sample size, and number of channels.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * SttEngine engine = new VoskEngine();
   * engine.initialize(logger, "model", 16000, 16, 1);
   * AudioFormat format = engine.getAudioFormat();
   * System.out.println("Audio format: " + format);
   * }</pre>
   *
   * @return the {@link AudioFormat} instance describing the audio format
   * @see AudioFormat
   */
  AudioFormat getAudioFormat();

  /**
   * Retrieves the data line used by the speech-to-text engine.
   *
   * <p>This method returns the {@link TargetDataLine} instance that the engine uses to capture
   * audio input. The data line is configured according to the audio format specified during the
   * engine's initialization. Implementations should ensure that the data line is properly opened
   * and ready to capture audio data.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * SttEngine engine = new VoskEngine();
   * engine.initialize(logger, "model", 16000, 16, 1);
   * TargetDataLine dataLine = engine.getDataLine();
   * dataLine.start();
   * // Capture audio data from the data line
   * }</pre>
   *
   * @return the {@link TargetDataLine} instance used by the engine
   * @throws LineUnavailableException if the data line cannot be opened
   * @see TargetDataLine
   * @see LineUnavailableException
   */
  TargetDataLine getDataLine() throws LineUnavailableException;

  /**
   * Starts the speech-to-text engine.
   *
   * <p>This method begins the process of capturing and processing audio input. Implementations
   * should ensure that the engine is properly initialized and ready to start listening for audio
   * input. Once started, the engine should continuously process audio data until the {@link
   * #stop()} method is called.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * SttEngine engine = new VoskEngine();
   * engine.initialize(logger, "model", 16000, 16, 1);
   * engine.start();
   * }</pre>
   *
   * @see #stop
   */
  void start();

  /**
   * Stops the speech-to-text engine.
   *
   * <p>This method stops the process of capturing and processing audio input. Implementations
   * should ensure that the engine is properly stopped and that any resources used for audio capture
   * are released. Once stopped, the engine should no longer process any audio data until the {@link
   * #start()} method is called again.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * SttEngine engine = new VoskEngine();
   * engine.initialize(logger, "model", 16000, 16, 1);
   * engine.start();
   * // After some time, stop the engine
   * engine.stop();
   * }</pre>
   *
   * @see #start()
   */
  void stop();
}
