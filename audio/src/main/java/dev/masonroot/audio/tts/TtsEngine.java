package dev.masonroot.audio.tts;

import dev.masonroot.audio.AudioInterface;
import java.nio.file.Path;
import lombok.NonNull;

/**
 * Text-to-Speech (TTS) engine interface for generating speech audio from text.
 *
 * <p>This interface defines the contract for TTS engines in the Nora application. It provides
 * methods for initializing the engine, generating speech audio from text, and saving audio to a
 * file.
 *
 * <p><b>Why:</b>
 *
 * <ul>
 *   <li>The TtsEngine interface is essential for defining the contract for TTS engines in the Nora
 *       application. By providing a standard set of methods, it ensures that all TTS engines adhere
 *       to a common structure and can be easily integrated into the application.
 *   <li>This interface allows for the implementation of different TTS engines that can be used
 *       interchangeably based on the requirements of the application.
 * </ul>
 *
 * <p><b>Notes:</b>
 *
 * <ul>
 *   <li>Implementing classes should provide concrete implementations for the {@link #initialize},
 *       {@link #speak}, and {@link #saveToFile} methods.
 * </ul>
 */
public interface TtsEngine extends AutoCloseable {
  /**
   * Initializes the TTS engine with the specified audio interface and data directory.
   *
   * <p>This method is responsible for initializing the TTS engine with the provided {@link
   * AudioInterface} for audio input and output and the data directory for storing model files or
   * other resources. Implementing classes should perform any necessary setup or configuration
   * required for the TTS engine to function properly.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This method is essential for setting up the TTS engine with the required audio interface
   *       and resources. By providing a standard initialization method, it ensures that the TTS
   *       engine is properly configured before use.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>Implementing classes should ensure that the audio interface and data directory are valid
   *       and accessible before initializing the TTS engine.
   *   <li>Any necessary resource loading, model loading, or configuration should be performed
   *       during initialization.
   * </ul>
   *
   * @param audio the {@link AudioInterface} for audio input and output; must not be null.
   * @param dataDirectory the data directory for storing model files or resources; must not be null.
   * @throws NullPointerException if the audio or data directory is null.
   */
  void initialize(@NonNull final AudioInterface audio, final Path dataDirectory);

  /**
   * Generates speech audio from the specified text.
   *
   * <p>This method converts the input text into speech audio using the TTS engine. It returns the
   * audio data as a byte array, which can be used for future use.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This method is essential for converting text input into speech audio. By providing a
   *       standard method for speech synthesis, it allows for consistent generation of audio data
   *       from text.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>Implementing classes should handle the text-to-speech conversion using the underlying TTS
   *       engine or model.
   *   <li>The returned byte array should contain the audio data in a format that can be played or
   *       saved.
   * </ul>
   *
   * @param text the text to convert to speech audio.
   * @return the speech audio data as a byte array.
   * @throws NullPointerException if the text is null.
   */
  byte[] speak(@NonNull final String text);

  /**
   * Saves the speech audio data to a file.
   *
   * <p>This method saves the speech audio data to a file with the specified filename. It provides
   * options to overwrite an existing file, play the audio after saving, and specify the filename
   * for the saved audio file.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This method is essential for saving speech audio data to a file for future use. By
   *       providing a standard method for saving audio data, it allows for easy storage and
   *       retrieval of speech audio files.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>Implementing classes should handle the saving of audio data to a file using the specified
   *       options.
   *   <li>The filename should be validated to ensure that it is a valid and accessible path.
   * </ul>
   *
   * @param text the speech audio data to save to a file.
   * @param overWrite whether to overwrite an existing file with the same name.
   * @param play whether to play the audio after saving.
   * @param filename the filename to save the audio data to.
   * @throws NullPointerException if the text or filename is null.
   */
  void saveToFile(
      @NonNull final String text,
      final boolean overWrite,
      final boolean play,
      @NonNull final String filename);
}
