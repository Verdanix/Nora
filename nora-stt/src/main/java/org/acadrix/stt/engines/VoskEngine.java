package org.acadrix.stt.engines;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sound.sampled.*;
import org.acadrix.logging.NoraLogger;
import org.acadrix.stt.exceptions.UnsupportedLineException;
import org.vosk.Model;
import org.vosk.Recognizer;

/**
 * The {@code VoskEngine} class provides an implementation of the {@link SttEngine} interface using
 * the Vosk speech recognition engine. It uses the Vosk Java bindings to interact with the Vosk C++
 * library.
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
 * <p>This class is thread-safe and can be used in a multi-threaded environment.
 *
 * <p>See also:
 *
 * <ul>
 *   <li>{@link SttEngine}
 *   <li>{@link NoraLogger}
 *   <li>{@link Model}
 *   <li>{@link Recognizer}
 * </ul>
 */
public class VoskEngine implements SttEngine {
  /**
   * An {@link AtomicBoolean} flag indicating whether the engine is currently listening.
   *
   * <p>This flag is used to control the listening state of the {@code VoskEngine}. It is set to
   * {@code true} when the engine starts listening for audio input and set to {@code false} when the
   * engine stops listening. The initial value is {@code false}.
   *
   * @see AtomicBoolean
   * @see VoskEngine#start()
   * @see VoskEngine#stop()
   */
  private final AtomicBoolean listening = new AtomicBoolean(false);

  /**
   * The Vosk model used by the engine for speech recognition.
   *
   * <p>This field holds the Vosk model used by the engine for speech recognition. It is initialized
   * during the {@link VoskEngine#initialize(NoraLogger, String, float, int, int)} method.
   *
   * @see Model
   * @see VoskEngine#initialize(NoraLogger, String, float, int, int)
   */
  private Model voskModel;

  /**
   * The Vosk recognizer used by the engine for speech recognition.
   *
   * <p>This field holds the Vosk recognizer used by the engine for speech recognition. It is
   * initialized during the {@link VoskEngine#initialize(NoraLogger, String, float, int, int)}
   * method.
   *
   * @see Recognizer
   * @see VoskEngine#initialize(NoraLogger, String, float, int, int)
   */
  private Recognizer voskRecognizer;

  /**
   * The audio format used by the engine for audio input.
   *
   * <p>This field holds the audio format used by the engine for audio input. It is initialized
   * during the {@link VoskEngine#getAudioFormat()} method.
   *
   * @see AudioFormat
   * @see VoskEngine#getAudioFormat()
   */
  private AudioFormat audioFormat;

  /**
   * The target data line used by the engine for audio input.
   *
   * <p>This field holds the target data line used by the engine for audio input. It is initialized
   * during the {@link VoskEngine#getDataLine()} method.
   *
   * @see TargetDataLine
   * @see VoskEngine#getDataLine()
   */
  private TargetDataLine targetDataLine;

  /**
   * The sample rate used by the engine for audio input.
   *
   * <p>This field holds the sample rate used by the engine for audio input. It is initialized
   * during the {@link VoskEngine#initialize(NoraLogger, String, float, int, int)} method.
   *
   * @see VoskEngine#initialize(NoraLogger, String, float, int, int)
   */
  private float sampleRate;

  /**
   * The sample size used by the engine for audio input.
   *
   * <p>This field holds the sample size used by the engine for audio input. It is initialized
   * during the {@link VoskEngine#initialize(NoraLogger, String, float, int, int)} method.
   *
   * @see VoskEngine#initialize(NoraLogger, String, float, int, int)
   */
  private int sampleSize;

  /**
   * The number of channels used by the engine for audio input.
   *
   * <p>This field holds the number of channels used by the engine for audio input. It is
   * initialized during the {@link VoskEngine#initialize(NoraLogger, String, float, int, int)}
   * method.
   *
   * @see VoskEngine#initialize(NoraLogger, String, float, int, int)
   */
  private int channels;

  /**
   * A flag indicating whether the engine is initialized.
   *
   * <p>This flag is used to indicate whether the engine is initialized. It is set to {@code true}
   * during the {@link VoskEngine#initialize(NoraLogger, String, float, int, int)} method and set to
   * {@code false} when the engine is closed.
   */
  private volatile boolean isInitialized = false;

  /**
   * The logger used by the engine for logging messages.
   *
   * <p>This field holds the logger used by the engine for logging messages. It is initialized
   * during the {@link VoskEngine#initialize(NoraLogger, String, float, int, int)} method.
   *
   * @see NoraLogger
   * @see VoskEngine#initialize(NoraLogger, String, float, int, int)
   */
  private NoraLogger logger;

  @Override
  public boolean initialize(
      NoraLogger logger, String modelPath, float sampleRate, int sampleSize, int channels)
      throws IOException, LineUnavailableException {
    logger.debug(
        "Initializing VoskEngine with model: {}, sampleRate: {}, sampleSize: {}, channels: {}",
        modelPath,
        sampleRate,
        sampleSize,
        channels);
    if (!this.isInitialized) {
      logger.debug("VoskEngine is not initialized, initializing...");
      this.logger = logger;
      this.voskModel = new Model(modelPath);
      this.voskRecognizer = new Recognizer(this.voskModel, sampleRate);
      this.sampleRate = sampleRate;
      this.sampleSize = sampleSize;
      this.channels = channels;

      this.audioFormat = this.getAudioFormat();
      this.targetDataLine = this.getDataLine();
      this.isInitialized = true;
    }
    return true;
  }

  @Override
  public String listen() {
    this.start();
    int bytesRead = 0;

    byte[] buffer = new byte[4096];
    while (this.listening.get() && bytesRead <= 50000) {
      bytesRead = this.targetDataLine.read(buffer, 0, buffer.length);
      if (this.voskRecognizer.acceptWaveForm(buffer, buffer.length)) {
        this.logger.debug("Accepted waveform. Breaking...");
        break;
      }
    }
    this.stop();
    return this.voskRecognizer.getFinalResult();
  }

  @Override
  public AudioFormat getAudioFormat() {
    return new AudioFormat(
        AudioFormat.Encoding.PCM_SIGNED,
        this.sampleRate,
        this.sampleSize,
        this.channels,
        (this.sampleSize / 8) * this.channels,
        this.sampleRate,
        false);
  }

  @Override
  public TargetDataLine getDataLine() throws LineUnavailableException {
    TargetDataLine targetDataLine = null;
    DataLine.Info info = new DataLine.Info(TargetDataLine.class, this.audioFormat);
    if (!AudioSystem.isLineSupported(info)) {
      throw new UnsupportedLineException(info.toString());
    }
    targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
    targetDataLine.open(this.audioFormat);
    return targetDataLine;
  }

  @Override
  public void start() {
    this.logger.debug("Starting Microphone input...");
    this.targetDataLine.start();
    this.listening.set(true);
    this.logger.debug("Listening.");
  }

  @Override
  public void stop() {
    this.logger.debug("Stopping Microphone input...");
    this.targetDataLine.stop();
    this.listening.set(false);
    this.logger.debug("Stopped.");
  }

  @Override
  public void close() {
    this.logger.debug("Closing Model...");
    this.voskModel.close();
    this.logger.debug("Closing Recognizer...");
    this.voskRecognizer.close();
    this.logger.debug("Closing TargetDataLine...");
    this.targetDataLine.close();
    this.logger.debug("Closed.");
  }
}
