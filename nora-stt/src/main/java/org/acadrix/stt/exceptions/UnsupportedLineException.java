package org.acadrix.stt.exceptions;

/**
 * Exception thrown when an unsupported line is encountered in the STT (Speech-to-Text) processing.
 * This exception is a runtime exception and indicates that the provided line cannot be processed by
 * the system.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * if (!isSupported(line)) {
 *   throw new UnsupportedLineException(line);
 * }
 * }</pre>
 *
 * <p>This exception should be used to signal that the input data does not conform to the expected
 * format or contains unsupported content.
 *
 * @see RuntimeException
 */
public class UnsupportedLineException extends RuntimeException {
  /**
   * Constructs a new UnsupportedLineException with the specified detail message. The detail message
   * is a string that describes this particular exception.
   *
   * @param line the unsupported line that caused this exception to be thrown.
   */
  public UnsupportedLineException(String line) {
    super("Unsupported line with this info: " + line);
  }
}
