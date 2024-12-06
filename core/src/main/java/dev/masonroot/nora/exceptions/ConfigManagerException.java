package dev.masonroot.nora.exceptions;

import lombok.NonNull;

/**
 * Custom exception class for handling configuration management errors.
 *
 * <p>This class extends {@code RuntimeException} and provides constructors to create exceptions
 * with a message and an optional cause. It is used to indicate issues related to configuration
 * management within the application.
 *
 * <p><b>Thread Safety:</b>
 *
 * <ul>
 *   <li>This class is thread-safe. It does not maintain any state and only provides constructors
 *       for creating exception instances.
 * </ul>
 *
 * <p><b>Notes:</b>
 *
 * <ul>
 *   <li>This class can be used to wrap other exceptions that occur during configuration management.
 *   <li>Ensure to provide meaningful messages and causes when creating instances of this exception.
 * </ul>
 */
public class ConfigManagerException extends RuntimeException {

  /**
   * Constructs a new {@code ConfigManagerException} with the specified detail message.
   *
   * <p>This constructor creates an instance of {@code ConfigManagerException} with a message that
   * describes the error. It is useful for indicating issues related to configuration management
   * within the application.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Throwing custom exceptions with meaningful messages helps in identifying and diagnosing
   *       issues specific to configuration management. It provides a way to capture and handle
   *       errors that occur during configuration operations.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>Ensure to provide a meaningful message when creating instances of this exception.
   *   <li>The {@code @NonNull} annotation indicates that the parameter should not be null.
   * </ul>
   *
   * @param message the detail message; must not be null.
   * @throws NullPointerException if the message is null.
   */
  public ConfigManagerException(@NonNull final String message) {
    super(message);
  }

  /**
   * Constructs a new {@code ConfigManagerException} with the specified detail message and cause.
   *
   * <p>This constructor creates an instance of {@code ConfigManagerException} with a message that
   * describes the error and an optional cause. It is useful for indicating issues related to
   * configuration management within the application and wrapping other exceptions that occur during
   * configuration operations.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Throwing custom exceptions with meaningful messages and causes helps in identifying and
   *       diagnosing issues specific to configuration management. It provides a way to capture and
   *       handle errors that occur during configuration operations and wrap other exceptions that
   *       may have caused the error.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>Ensure to provide a meaningful message and cause when creating instances of this
   *       exception.
   *   <li>The {@code @NonNull} annotation indicates that the parameters should not be null.
   * </ul>
   *
   * @param message the detail message; must not be null.
   * @param cause the cause of the exception; must not be null.
   * @throws NullPointerException if the message or cause is null.
   */
  public ConfigManagerException(@NonNull final String message, @NonNull final Throwable cause) {
    super(message, cause);
  }
}
