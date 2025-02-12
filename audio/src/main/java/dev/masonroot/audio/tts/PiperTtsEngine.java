package dev.masonroot.audio.tts;

import com.k2fsa.sherpa.onnx.*;
import dev.masonroot.audio.AudioInterface;
import dev.masonroot.audio.exceptions.PiperInitializationException;
import dev.masonroot.common.NoraLogger;
import dev.masonroot.common.SecurityUtils;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.NonNull;

/**
 * Text-to-Speech (TTS) engine that uses the Piper offline TTS model for speech synthesis.
 *
 * <p>This class provides methods to generate speech audio from text using the Piper offline TTS
 * model. It uses the Piper library to generate audio samples from text input.
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
 *   <li>Ensure that the data directory provided is valid and accessible.
 *   <li>Ensure that the model and tokens files are secure and accessible.
 * </ul>
 */
public final class PiperTtsEngine implements TtsEngine {
  /** The speaker ID for the VCTK dataset. TODO: Add support for multiple speakers. */
  private final int vctkSpeakerId = 91;

  /** The Piper offline TTS engine for generating speech audio. */
  private OfflineTts tts;

  /** The audio interface for handling audio input and output. */
  private AudioInterface audio;

  /** The data directory for storing Piper files. */
  private Path dataDirectory;

  @Override
  public void initialize(@NonNull final AudioInterface audio, @NonNull final Path dataDirectory) {
    SecurityUtils.throwIfIsNotDirectory(dataDirectory);
    this.audio = audio;

    Path piperDir = dataDirectory.resolve("piper");

    try {
      Files.createDirectories(piperDir.resolve("data"));
    } catch (IOException e) {
      NoraLogger.trace("Was unable to create piper directory");
      throw new PiperInitializationException(dataDirectory.resolve("piper"), e);
    }

    this.checkPiperFileSecurity(piperDir);
    this.dataDirectory = piperDir.resolve("data");

    String model = piperDir.resolve("model.onnx").toString();
    String tokens = piperDir.resolve("tokens.txt").toString();
    String dataDir = piperDir.toString();

    OfflineTtsVitsModelConfig vitsModelConfig =
        OfflineTtsVitsModelConfig.builder()
            .setModel(model)
            .setTokens(tokens)
            .setDataDir(dataDir)
            .build();

    OfflineTtsModelConfig modelConfig =
        OfflineTtsModelConfig.builder()
            .setVits(vitsModelConfig)
            .setNumThreads(4)
            .setDebug(false)
            .build();

    OfflineTtsConfig config = OfflineTtsConfig.builder().setModel(modelConfig).build();
    this.tts = new OfflineTts(config);
  }

  /**
   * Checks the security of the Piper files.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>To ensure that the Piper files are secure and accessible.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The method checks the security of the model and tokens files.
   * </ul>
   *
   * @param dataDirectory the path to the data directory containing the Piper files; must not be
   *     null.
   * @throws SecurityException if the Piper files are insecure.
   */
  private void checkPiperFileSecurity(Path dataDirectory) {
    SecurityUtils.throwIfIsNotDirectory(dataDirectory.resolve("data"));
    SecurityUtils.throwIfFileIsInsecure(dataDirectory.resolve("model.onnx"));
    SecurityUtils.throwIfFileIsInsecure(dataDirectory.resolve("tokens.txt"));
  }

  /**
   * Converts float samples to bytes.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>To convert the float samples to bytes for audio output.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The method converts the float samples to bytes using little-endian byte order.
   * </ul>
   *
   * @param samples the float samples to convert to bytes.
   * @return the byte array containing the converted samples.
   */
  private byte[] convertFloatSamplesToBytes(float[] samples) {
    int sampleCount = samples.length;
    byte[] bytes = new byte[sampleCount * 2];

    ByteBuffer buffer = ByteBuffer.wrap(bytes);
    buffer.order(ByteOrder.LITTLE_ENDIAN);

    for (float sample : samples) {
      short shortSample = (short) (sample * Short.MAX_VALUE);
      buffer.putShort(shortSample);
    }

    return bytes;
  }

  @Override
  public byte[] speak(@NonNull final String text) {
    GeneratedAudio generatedAudio = this.tts.generate(text, this.vctkSpeakerId);
    byte[] bytes = this.convertFloatSamplesToBytes(generatedAudio.getSamples());
    this.audio.write(bytes);
    return bytes;
  }

  @Override
  public void saveToFile(
      @NonNull final String text,
      final boolean overWrite,
      final boolean play,
      @NonNull final String filename) {
    File file = this.dataDirectory.resolve(filename).toFile();
    if (file.exists() && !overWrite && !play) return;

    GeneratedAudio generatedAudio = this.tts.generate(text, this.vctkSpeakerId);
    generatedAudio.save(file.getAbsolutePath());

    if (!play) return;

    byte[] bytes = this.convertFloatSamplesToBytes(generatedAudio.getSamples());
    this.audio.write(bytes);
  }

  @Override
  public void close() {
    this.tts.release();
  }
}
