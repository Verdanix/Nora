package dev.masonroot.audio;

import dev.masonroot.common.NoraLogger;
import java.io.ByteArrayOutputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import lombok.NonNull;

/**
 * Audio interface for handling audio input and output.
 *
 * <p>This class provides methods to read audio data from a microphone and write audio data to a
 * speaker. It uses the Java Sound API to manage audio input and output streams.
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
public record AudioInterface(TargetDataLine microphone, SourceDataLine speaker)
    implements AutoCloseable {

  /**
   * Constructs an {@code AudioInterface} with the specified microphone and speaker lines.
   *
   * <p>This constructor initializes the audio interface with the provided {@code TargetDataLine}
   * for the microphone and {@code SourceDataLine} for the speaker. Both lines must be properly
   * initialized before being passed to this constructor.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>To facilitate audio input and output operations by managing the microphone and speaker
   *       lines.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The {@code @NonNull} annotation indicates that the parameters should not be null.
   * </ul>
   *
   * @param microphone the {@code TargetDataLine} for the microphone; must not be null
   * @param speaker the {@code SourceDataLine} for the speaker; must not be null
   */
  public AudioInterface(
      @NonNull final TargetDataLine microphone, @NonNull final SourceDataLine speaker) {
    this.microphone = microphone;
    this.speaker = speaker;
  }

  /**
   * Writes the specified audio data to the speaker.
   *
   * <p>This method opens the speaker line, starts it, writes the provided audio data to it, and
   * then stops and drains the line. It ensures that the audio data is played through the speaker.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>To facilitate audio output by writing audio data to the speaker.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The {@code @NonNull} annotation indicates that the parameter should not be null.
   * </ul>
   *
   * @param data the audio data to be written to the speaker; must not be null
   */
  public synchronized void write(@NonNull final byte[] data) {
    try {
      this.speaker.open();
      this.speaker.start();
      this.speaker.write(data, 0, data.length);
    } catch (IllegalArgumentException | IllegalStateException | LineUnavailableException e) {
      NoraLogger.trace("Failed to write audio data to speaker.", e);
    } finally {
      this.speaker.stop();
      this.speaker.drain();
    }
  }

  /**
   * Reads audio data from the microphone for the specified timeout duration.
   *
   * <p>This method opens the microphone line, starts it, and reads audio data into a buffer until
   * the specified timeout duration is reached. It then stops and drains the line.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>To facilitate audio input by reading audio data from the microphone.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The {@code @NonNull} annotation indicates that the parameter should not be null.
   * </ul>
   *
   * @param timeoutInMs the maximum time to read audio data in milliseconds; must not be null
   * @return the audio data read from the microphone as a byte array
   */
  public synchronized byte[] read(final long timeoutInMs) {
    final byte[] data = new byte[this.microphone.getBufferSize() / 5];
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    long elapsedTime = 0;

    try {
      this.microphone.open();
      this.microphone.start();
      final long startTime = System.currentTimeMillis();
      while (elapsedTime < timeoutInMs) {
        final int bytesRead = this.microphone.read(data, 0, data.length);
        out.write(data, 0, bytesRead);
        elapsedTime = System.currentTimeMillis() - startTime;
      }

    } catch (IllegalArgumentException | IllegalStateException | LineUnavailableException e) {
      NoraLogger.trace("Failed to read audio data from microphone.", e);
    } finally {
      this.microphone.stop();
      this.microphone.drain();
    }
    return out.toByteArray();
  }

  @Override
  public void close() throws Exception {
    this.microphone.close();
    this.speaker.close();
  }
}
