package dev.masonroot.nora;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for logging messages using SLF4J.
 *
 * <p>This class provides static methods to log messages at different levels (INFO, WARN, ERROR,
 * DEBUG, TRACE) using the SLF4J logging framework. It uses Lombok's {@code @Slf4j} annotation to
 * automatically generate a logger instance.
 *
 * <p><b>Thread Safety:</b>
 *
 * <ul>
 *   <li>This class is thread-safe. All methods are static and do not modify any shared state.
 * </ul>
 *
 * <p><b>Notes:</b>
 *
 * <ul>
 *   <li>This class cannot be instantiated.
 *   <li>Ensure SLF4J and a compatible logging implementation (e.g., Logback) are included in the
 *       classpath.
 * </ul>
 */
@Slf4j
public final class NoraLogger {
  private NoraLogger() {}

  /**
   * Logs an informational message using SLF4J.
   *
   * <p>This method logs a message at the INFO level. It is useful for logging general information
   * about the application's state or behavior that is not necessarily indicative of a problem.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Logging informational messages helps in understanding the flow of the application and can
   *       be useful for debugging and monitoring purposes. It provides insights into the
   *       application's operations and can help identify issues when they arise.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>This method uses varargs to allow multiple arguments to be passed, which will be
   *       formatted into the message.
   *   <li>The {@code @NonNull} annotation indicates that the parameters should not be null.
   * </ul>
   *
   * @param message the message to be logged; must not be null
   * @param args the arguments to be formatted into the message; must not be null
   */
  public static void info(@NonNull final String message, @NonNull final Object... args) {
    log.info(message, args);
  }

  /**
   * Logs an informational message with an associated throwable using SLF4J.
   *
   * <p>This method logs a message at the INFO level along with an associated throwable (e.g.,
   * exception or error). It is useful for logging exceptions or errors that occur during the
   * application's execution.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Logging exceptions or errors helps in identifying and diagnosing issues within the
   *       application.
   *   <li>It provides a way to capture and log the details of an exception, which can be useful for
   *       troubleshooting and debugging.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>This method is useful for logging exceptions or errors that occur during the
   *       application's execution.
   *   <li>The {@code @NonNull} annotation indicates that the parameters should not be null.
   * </ul>
   *
   * @param message the message to be logged; must not be null
   * @param throwable the throwable associated with the message; must not be null
   */
  public static void info(@NonNull final String message, @NonNull final Throwable throwable) {
    log.info(message, throwable);
  }

  /**
   * Logs a warning message using SLF4J.
   *
   * <p>This method logs a message at the WARN level. It is useful for logging warnings or potential
   * issues that do not necessarily indicate an error but should be noted.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Logging warning messages helps in identifying potential issues or areas of concern within
   *       the application. It provides a way to capture and log messages that may require attention
   *       or further investigation.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>This method uses varargs to allow multiple arguments to be passed, which will be
   *       formatted into the message.
   *   <li>The {@code @NonNull} annotation indicates that the parameters should not be null.
   * </ul>
   *
   * @param message the message to be logged; must not be null
   * @param args the arguments to be formatted into the message; must not be null
   */
  public static void warn(@NonNull final String message, @NonNull final Object... args) {
    log.warn(message, args);
  }

  /**
   * Logs a warning message with an associated throwable using SLF4J.
   *
   * <p>This method logs a message at the WARN level along with an associated throwable (e.g.,
   * exception or error). It is useful for logging warnings or potential issues that may require
   * further investigation.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Logging warning messages with an associated throwable helps in identifying potential
   *       issues or areas of concern within the application. It provides a way to capture and log
   *       messages that may require attention or further investigation.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>This method is useful for logging warnings or potential issues that may require further
   *       investigation.
   *   <li>The {@code @NonNull} annotation indicates that the parameters should not be null.
   * </ul>
   *
   * @param message the message to be logged; must not be null
   * @param throwable the throwable associated with the message; must not be null
   */
  public static void warn(@NonNull final String message, @NonNull final Throwable throwable) {
    log.warn(message, throwable);
  }

  /**
   * Logs an error message using SLF4J.
   *
   * <p>This method logs a message at the ERROR level. It is useful for logging errors or exceptions
   * that indicate a problem within the application.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Logging error messages helps in identifying and diagnosing issues within the application.
   *   <li>It provides a way to capture and log messages that indicate a problem or error condition.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>This method uses varargs to allow multiple arguments to be passed, which will be
   *       formatted into the message.
   *   <li>The {@code @NonNull} annotation indicates that the parameters should not be null.
   * </ul>
   *
   * @param message the message to be logged; must not be null
   * @param args the arguments to be formatted into the message; must not be null
   */
  public static void error(@NonNull final String message, @NonNull final Object... args) {
    log.error(message, args);
  }

  /**
   * Logs an error message with an associated throwable using SLF4J.
   *
   * <p>This method logs a message at the ERROR level along with an associated throwable (e.g.,
   * exception or error). It is useful for logging errors or exceptions that indicate a problem
   * within the application.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Logging error messages with an associated throwable helps in identifying and diagnosing
   *       issues within the application. It provides a way to capture and log messages that
   *       indicate a problem or error condition.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>This method is useful for logging errors or exceptions that indicate a problem within the
   *       application.
   *   <li>The {@code @NonNull} annotation indicates that the parameters should not be null.
   * </ul>
   *
   * @param message the message to be logged; must not be null
   * @param throwable the throwable associated with the message; must not be null
   */
  public static void error(@NonNull final String message, @NonNull final Throwable throwable) {
    log.error(message, throwable);
  }

  /**
   * Logs a debug message using SLF4J.
   *
   * <p>This method logs a message at the DEBUG level. It is useful for logging detailed information
   * about the application's state or behavior that is not typically logged at the INFO level.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Logging debug messages helps in understanding the internal workings of the application.
   *   <li>It provides detailed information that can be useful for debugging and troubleshooting
   *       purposes.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>This method uses varargs to allow multiple arguments to be passed, which will be
   *       formatted into the message.
   *   <li>The {@code @NonNull} annotation indicates that the parameters should not be null.
   * </ul>
   *
   * @param message the message to be logged; must not be null
   * @param args the arguments to be formatted into the message; must not be null
   */
  public static void debug(@NonNull final String message, @NonNull final Object... args) {
    log.debug(message, args);
  }

  /**
   * Logs a debug message with an associated throwable using SLF4J.
   *
   * <p>This method logs a message at the DEBUG level along with an associated throwable (e.g.,
   * exception or error). It is useful for logging exceptions or errors that occur during the
   * application's execution.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Logging debug messages with an associated throwable helps in identifying and diagnosing
   *       issues within the application. It provides a way to capture and log the details of an
   *       exception, which can be useful for troubleshooting and debugging.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>This method is useful for logging exceptions or errors that occur during the
   *       application's execution.
   *   <li>The {@code @NonNull} annotation indicates that the parameters should not be null.
   * </ul>
   *
   * @param message the message to be logged; must not be null
   * @param throwable the throwable associated with the message; must not be null
   */
  public static void debug(@NonNull final String message, @NonNull final Throwable throwable) {
    log.debug(message, throwable);
  }

  /**
   * Logs a trace message using SLF4J.
   *
   * <p>This method logs a message at the TRACE level. It is useful for logging detailed information
   * about the application's state or behavior that is not typically logged at the DEBUG level.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Logging trace messages helps in understanding the internal workings of the application.
   *   <li>It provides detailed information that can be useful for debugging and troubleshooting
   *       purposes.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>This method uses varargs to allow multiple arguments to be passed, which will be
   *       formatted into the message.
   *   <li>The {@code @NonNull} annotation indicates that the parameters should not be null.
   * </ul>
   *
   * @param message the message to be logged; must not be null
   * @param args the arguments to be formatted into the message; must not be null
   */
  public static void trace(@NonNull final String message, @NonNull final Object... args) {
    log.trace(message, args);
  }

  /**
   * Logs a trace message with an associated throwable using SLF4J.
   *
   * <p>This method logs a message at the TRACE level along with an associated throwable (e.g.,
   * exception or error). It is useful for logging exceptions or errors that occur during the
   * application's execution.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Logging trace messages with an associated throwable helps in identifying and diagnosing
   *       issues within the application. It provides a way to capture and log the details of an
   *       exception, which can be useful for troubleshooting and debugging.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>This method is useful for logging exceptions or errors that occur during the
   *       application's execution.
   *   <li>The {@code @NonNull} annotation indicates that the parameters should not be null.
   * </ul>
   *
   * @param message the message to be logged; must not be null
   * @param throwable the throwable associated with the message; must not be null
   */
  public static void trace(@NonNull final String message, @NonNull final Throwable throwable) {
    log.trace(message, throwable);
  }
}
