package dev.masonroot.nora.config;

import java.util.Properties;
import lombok.NonNull;

/**
 * The Config interface defines the contract for configuration models in the Nora application.
 *
 * <p>This interface provides methods for setting and retrieving configuration properties, ensuring
 * that all configuration models adhere to a standard structure. Implementing this interface allows
 * for consistent handling of configuration data across different parts of the application.
 *
 * <p><b>Why:</b>
 *
 * <ul>
 *   <li>The Config interface is essential for maintaining a uniform approach to configuration
 *       management within the Nora application. By defining a standard set of methods, it ensures
 *       that all configuration models can be easily managed, validated, and converted to and from
 *       {@link Properties} objects.
 *   <li>This consistency simplifies the process of loading, saving, and validating configuration
 *       data.
 * </ul>
 *
 * <p><b>Notes:</b>
 *
 * <ul>
 *   <li>Implementing classes should provide concrete implementations for the {@link
 *       #setFields(Properties)} and {@link #toProperties()} methods.
 *   <li>The {@link #handleValidity()} method provides a default implementation that does nothing.
 *       Implementing classes can override this method to provide custom validation logic.
 * </ul>
 */
public interface Config {
  /**
   * Sets the fields of the configuration model using the provided {@link Properties} object.
   *
   * <p>This method is responsible for populating the configuration model with values from the given
   * {@link Properties} object. Implementing classes should provide concrete implementations that
   * map the properties to the appropriate fields of the configuration model.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This method is essential for loading configuration data into the model. By using a {@link
   *       Properties} object, it allows for a flexible and standardized way of setting
   *       configuration values, which can be easily loaded from various sources such as files,
   *       databases, or environment variables.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>Implementing classes should ensure that all required fields are set and handle any
   *       necessary validation or conversion of property values.
   *   <li>If a property value is missing or invalid, the implementing class should decide how to
   *       handle such cases, either by providing default values, throwing exceptions, or logging
   *       warnings.
   * </ul>
   *
   * @param properties the {@link Properties} object containing the configuration data; must not be
   *     null. Declared as {@code @NonNull} to ensure that the properties object is provided.
   * @throws NullPointerException if the properties object is null.
   */
  void setFields(@NonNull final Properties properties);

  /**
   * Converts the configuration model to a {@link Properties} object.
   *
   * <p>This method is responsible for converting the configuration model into a {@link Properties}
   * object, which can be used to store or export the configuration data. Implementing classes
   * should provide concrete implementations that map the fields of the configuration model to
   * properties in the resulting object.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>Converting the configuration model to a {@link Properties} object allows for easy
   *       serialization and storage of the configuration data. This method provides a standardized
   *       way to export the configuration data, making it easy to save to files, databases, or
   *       other persistent storage mechanisms.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>Implementing classes should ensure that all fields are correctly mapped to properties in
   *       the resulting object.
   *   <li>If a field value is null or invalid, the implementing class should decide how to handle
   *       such cases, either by providing default values, throwing exceptions, or logging warnings.
   * </ul>
   *
   * @return a {@link Properties} object containing the configuration data.
   * @throws NullPointerException if the properties object is null.
   */
  @NonNull
  Properties toProperties();

  /**
   * Validates the configuration model after the fields have been set.
   *
   * <p>This method is called after {@link #setFields(Properties)} to ensure that the configuration
   * model is in a valid state. Implementing classes should use the class's fields to perform any
   * necessary validation checks.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This method is essential for ensuring that the configuration data is valid and
   *       consistent. By performing validation checks, it helps to catch any errors or
   *       inconsistencies in the configuration data before it is used by the application.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>Implementing classes should provide concrete implementations that check the validity of
   *       the fields and handle any validation errors appropriately.
   *   <li>If a field value is invalid, the implementing class should decide how to handle such
   *       cases, either by logging warnings or providing default values.
   * </ul>
   */
  default void handleValidity() {}
}
