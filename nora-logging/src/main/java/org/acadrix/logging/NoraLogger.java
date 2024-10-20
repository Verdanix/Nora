package org.acadrix.logging;

import org.slf4j.Logger;
import org.slf4j.event.Level;

/**
 * The {@code NoraLogger} class provides a simplified interface for logging messages at various
 * levels (INFO, WARN, ERROR, DEBUG, TRACE) using the SLF4J API. It is designed to work with a
 * pre-configured {@link LoggerConfiguration} instance to ensure consistent logging behavior across
 * the application.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * LoggerConfiguration config = new LoggerConfiguration(
 *     "/var/logs/myapp", "myappLogger", 30, "10MB", true, true);
 * NoraLogger logger = new NoraLogger(config);
 * logger.info("This is an info message");
 * logger.debug("This is a debug message");
 *
 * }</pre>
 *
 * <p>This class is thread-safe and can be used in a multi-threaded environment.
 *
 * <p>Note: Ensure that the {@link LoggerConfiguration} instance is properly configured before using
 * this class.
 *
 * <p>See also:
 *
 * <ul>
 *   <li>{@link LoggerConfiguration}
 *   <li>{@link Logger}
 *   <li>{@link Level}
 * </ul>
 *
 * @see LoggerConfiguration
 * @see org.slf4j.Logger
 * @see org.slf4j.event.Level
 */
public class NoraLogger {
  /** The SLF4J logger instance used for logging messages. */
  private final Logger logger;

  /**
   * Constructs a new NoraLogger instance with the provided logger.
   *
   * @param loggerConfiguration the logger configuration to use.
   */
  public NoraLogger(LoggerConfiguration loggerConfiguration) {
    this.logger = loggerConfiguration.getLogger();
  }

  /**
   * Logs a message at the specified log level. This method delegates the logging to the appropriate
   * SLF4J logger method based on the provided log level.
   *
   * @param level the log level at which the message should be logged.
   * @param message the message to log.
   * @param args the optional logback format arguments or Throwable.
   * @see Level
   * @see Throwable
   */
  private void log(Level level, String message, Object args) {
    if (args instanceof Throwable) {
      this.logWithThrowable(level, message, (Throwable) args);
    } else {
      this.logWithPlaceholders(level, message, (Object[]) args);
    }
  }

  /**
   * Logs a message at the specified log level with an associated throwable. This method delegates
   * the logging to the appropriate SLF4J logger method based on the provided log level.
   *
   * @param level the log level at which the message should be logged.
   * @param message the message to log.
   * @param throwable the throwable to log along with the message.
   * @see Level
   */
  private void logWithThrowable(Level level, String message, Throwable throwable) {
    switch (level) {
      case WARN -> this.logger.warn(message, throwable);
      case DEBUG -> this.logger.debug(message, throwable);
      case ERROR -> this.logger.error(message, throwable);
      case TRACE -> this.logger.trace(message, throwable);
      default -> this.logger.info(message, throwable);
    }
  }

  /**
   * Logs a message at the specified log level. This method delegates the logging to the appropriate
   * SLF4J logger method based on the provided log level.
   *
   * @param level the log level at which the message should be logged.
   * @param message the message to log.
   * @param args the optional logback format arguments.
   * @see Level
   */
  private void logWithPlaceholders(Level level, String message, Object[] args) {
    switch (level) {
      case WARN -> this.logger.warn(message, args);
      case DEBUG -> this.logger.debug(message, args);
      case ERROR -> this.logger.error(message, args);
      case TRACE -> this.logger.trace(message, args);
      default -> this.logger.info(message, args);
    }
  }

  /**
   * Log a message at the INFO level. INFO messages are useful for informative messages and should
   * not contain sensitive data.
   *
   * @param message the message to log with optional logback format arguments.
   * @param args the optional logback format arguments.
   */
  public void info(String message, Object... args) {
    this.log(Level.INFO, message, args);
  }

  /**
   * Log a message at the WARN level. WARN messages are useful for situations that may lead to
   * errors.
   *
   * @param message the message to log with optional logback format arguments.
   * @param args the optional logback format arguments.
   */
  public void warn(String message, Object... args) {
    this.log(Level.WARN, message, args);
  }

  /**
   * Log a message at the ERROR level. ERROR messages are useful for situations that lead to errors.
   *
   * @param message the message to log with optional logback format arguments.
   * @param args the optional logback format arguments.
   */
  public void error(String message, Object... args) {
    this.log(Level.ERROR, message, args);
  }

  /**
   * Log a message at the DEBUG level. DEBUG messages are useful for debugging purposes.
   *
   * @param message the message to log with optional logback format arguments.
   * @param args the optional logback format arguments.
   */
  public void debug(String message, Object... args) {
    this.log(Level.DEBUG, message, args);
  }

  /**
   * Log a message at the TRACE level. TRACE messages are useful for debugging purposes. They
   * provide the most detailed information.
   *
   * @param message the message to log with optional logback format arguments.
   * @param args the optional logback format arguments.
   */
  public void trace(String message, Object... args) {
    this.log(Level.TRACE, message, args);
  }

  /**
   * Logs a message at the TRACE level. TRACE messages are useful for debugging purposes. They
   * provide the most detailed information.
   *
   * @param message the message to log without logback format arguments.
   * @param throwable the throwable to log.
   */
  public void trace(String message, Throwable throwable) {
    this.log(Level.TRACE, message, throwable);
  }

  /**
   * Logs a message at the ERROR level. ERROR messages are useful for situations that lead to
   * errors.
   *
   * @param message the message to log without logback format arguments.
   * @param throwable the throwable to log.
   */
  public void error(String message, Throwable throwable) {
    this.log(Level.ERROR, message, throwable);
  }

  /**
   * Logs a message at the DEBUG level. DEBUG messages are useful for debugging purposes.
   *
   * @param message the message to log without logback format arguments.
   * @param throwable the throwable to log.
   */
  public void debug(String message, Throwable throwable) {
    this.log(Level.DEBUG, message, throwable);
  }
}
