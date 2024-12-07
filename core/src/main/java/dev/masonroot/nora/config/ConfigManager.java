package dev.masonroot.nora.config;

import dev.masonroot.nora.NoraLogger;
import dev.masonroot.nora.exceptions.ConfigManagerException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.Properties;
import lombok.Getter;
import lombok.NonNull;

/**
 * Manages the configuration for the application.
 *
 * <p>This class provides methods to create, load, save, and watch configuration files. The class
 * ensures that the configuration file is created if it does not exist, and it can reload the
 * configuration at runtime if changes are detected.
 *
 * <p><b>Thread Safety:</b> <br>
 * This class is thread-safe. Methods that modify the configuration are synchronized to ensure
 * consistency.
 *
 * <p><b>Usage:</b> <br>
 *
 * <ul>
 *   <li>Instantiate this class with a specific configuration model and path.
 *   <li>Use the {@code create}, {@code reload}, and {@code save} methods to manage the
 *       configuration file.
 *   <li>Enable file watching to automatically reload the configuration when changes are detected.
 * </ul>
 *
 * <p><b>Example:</b><br>
 *
 * <pre>{@code
 * Path configPath = Paths.get("config.properties");
 * MyConfig config = new MyConfig();
 * ConfigManager configManager = new ConfigManager(config, configPath, true);
 * configManager.save();
 * configManager.reload();
 * }</pre>
 *
 * <p><b>Notes:</b> <br>
 *
 * <ul>
 *   <li>Ensure the configuration file path is readable and writable.
 *   <li>The {@code @NonNull} annotation indicates that parameters should not be null.
 *   <li>Handle {@code ConfigManagerException} for error scenarios.
 * </ul>
 */
@Getter
public final class ConfigManager {
  /**
   * The configuration model instance.
   *
   * <p><b>Why:</b> <br>
   * This variable holds the configuration model instance, which defines the structure of the
   * configuration settings. It is used to load, save, and manage the configuration data.
   *
   * <p><b>Performance:</b> <br>
   * As a final variable, it is initialized once and remains constant, ensuring efficient access and
   * avoiding repeated instantiation.
   *
   * <p><b>Notes:</b> <br>
   *
   * <ul>
   *   <li>This variable should not be null, as indicated by the {@code @NonNull} annotation.
   *   <li>The configuration model instance is used to set and retrieve configuration settings.
   * </ul>
   */
  private final Config<?> config;

  /**
   * The path to the configuration file.
   *
   * <p><b>Why:</b> <br>
   * This variable specifies the location of the configuration file on the filesystem. It is used to
   * read from and write to the configuration file.
   *
   * <p><b>Performance:</b> <br>
   * As a final variable, it is initialized once and remains constant, ensuring efficient access and
   * avoiding repeated path resolution.
   *
   * <p><b>Notes:</b> <br>
   *
   * <ul>
   *   <li>This variable should not be null, as indicated by the {@code @NonNull} annotation.
   *   <li>Ensure the path is readable and writable to avoid runtime exceptions.
   * </ul>
   */
  private final Path configPath;

  /**
   * The properties object holding configuration key-value pairs.
   *
   * <p><b>Why:</b> <br>
   * This variable stores the configuration settings as key-value pairs. It is used to load, save,
   * and manipulate the configuration data.
   *
   * <p><b>Performance:</b> <br>
   * The {@code Properties} object provides efficient storage and retrieval of configuration
   * settings. It is thread-safe when used in a synchronized context.
   *
   * <p><b>Notes:</b> <br>
   *
   * <ul>
   *   <li>This variable is initialized as an empty {@code Properties} object and populated during
   *       the configuration load process.
   *   <li>It is cleared and reloaded whenever the configuration file is reloaded.
   * </ul>
   */
  private final Properties properties = new Properties();

  /**
   * The watch service for monitoring configuration file changes.
   *
   * <p><b>Why:</b> <br>
   * This variable is used to watch for changes to the configuration file. It enables automatic
   * reloading of the configuration when the file is modified.
   *
   * <p><b>Performance:</b> <br>
   * The {@code WatchService} is efficient for monitoring file system events. It runs in a separate
   * thread to avoid blocking the main application flow.
   *
   * <p><b>Notes:</b> <br>
   *
   * <ul>
   *   <li>This variable is initialized when file watching is enabled.
   *   <li>Ensure proper handling of {@code IOException} when starting and closing the watch
   *       service.
   * </ul>
   */
  private WatchService watchService;

  /**
   * Creates a new configuration manager with the specified configuration model and file path.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This constructor initializes the configuration manager with the provided configuration
   *       model and file path.
   *   <li>It allows for the creation of a configuration manager that can manage the configuration
   *       settings for the application.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The configuration manager is created with the specified configuration model and file
   *       path.
   *   <li>File watching for automatic reloading is disabled by default.
   * </ul>
   *
   * <p><b>Performance:</b>
   *
   * <ul>
   *   <li>Initializing the configuration manager with the configuration model and file path ensures
   *       that the manager is ready to manage the configuration settings.
   *   <li>Disabling file watching by default reduces unnecessary overhead when not needed.
   * </ul>
   *
   * @param config the configuration model instance; must not be null
   * @param configPath the path to the configuration file; must not be null
   */
  public ConfigManager(final Config<?> config, final Path configPath) {
    this(config, configPath, false);
  }

  /**
   * Creates a new configuration manager with the specified configuration model, file path and
   * whether to auto-reload.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This constructor initializes the configuration manager with the provided configuration
   *       model and file path.
   *   <li>It allows for the creation of a configuration manager that can manage the configuration
   *       settings for the application.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The configuration manager is created with the specified configuration model and file
   *       path.
   *   <li>If the {@code reload} parameter is set to {@code true}, the configuration manager will
   *       start a file watcher to monitor changes to the configuration file.
   * </ul>
   *
   * <p><b>Performance:</b>
   *
   * <ul>
   *   <li>Initializing the configuration manager with the configuration model and file path ensures
   *       that the manager is ready to manage the configuration settings.
   *   <li>Starting the file watcher only when necessary reduces unnecessary overhead.
   * </ul>
   *
   * @param config the configuration model instance; must not be null
   * @param configPath the path to the configuration file; must not be null
   * @param reload {@code true} to enable file watching for automatic reloading; {@code false}
   *     otherwise
   */
  public ConfigManager(
      @NonNull final Config<?> config, @NonNull final Path configPath, final boolean reload) {
    this.configPath = configPath;
    this.config = config;
    this.create();
    this.reload();
    if (reload) {
      this.startFileWatcher();
    }
  }

  /**
   * Creates the configuration file if it does not exist.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This method ensures that the configuration file is created if it does not exist.
   *   <li>It checks the file path and creates the file if necessary.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>If the configuration file already exists, it validates that the file is readable and
   *       writable.
   *   <li>The method throws an exception if the file path is invalid or the file cannot be created.
   * </ul>
   *
   * <p><b>Performance:</b>
   *
   * <ul>
   *   <li>It avoids redundant file creation by checking the file's existence first.
   *   <li>The use of `Files.createFile` ensures atomic file creation, reducing the risk of race
   *       conditions.
   *   <li>Validating the file's readable and writable upfront prevents potential runtime errors,
   *       improving overall application stability.
   * </ul>
   *
   * @throws ConfigManagerException if the configuration file cannot be created
   */
  public void create() throws ConfigManagerException {
    if (!this.exists()) {
      try {
        Files.createFile(this.configPath);
      } catch (IOException e) {
        throw new ConfigManagerException("Failed to create configuration file", e);
      }
      return;
    }
    if (!Files.isReadable(configPath)
        || !Files.isWritable(configPath)
        || !Files.isRegularFile(configPath)
        || Files.isSymbolicLink(configPath)) {
      throw new IllegalArgumentException(
          "Config path must be a readable and writable regular file: " + configPath);
    }
  }

  /**
   * Starts the file watcher to monitor configuration file changes.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This method initializes and starts a file watcher to monitor changes to the configuration
   *       file.
   *   <li>It enables automatic reloading of the configuration when the file is modified, ensuring
   *       that the application always uses the latest configuration settings.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The file watcher runs in a separate daemon thread to avoid blocking the main application
   *       flow.
   *   <li>Proper handling of {@code IOException} is necessary when starting and closing the watch
   *       service.
   *   <li>If the file watcher fails to start, an error is logged, but the application will continue
   *       to function without automatic configuration reloading.
   * </ul>
   *
   * <p><b>Performance:</b>
   *
   * <ul>
   *   <li>The {@code WatchService} is efficient for monitoring file system events and runs in a
   *       separate thread to minimize impact on the main application.
   *   <li>Using a daemon thread ensures that the file watcher does not prevent the JVM from
   *       shutting down.
   *   <li>Efficient handling of file system events reduces the overhead associated with monitoring
   *       file changes.
   *   <li>Proper synchronization ensures thread safety when accessing the watch service.
   * </ul>
   */
  private void startFileWatcher() {
    try {
      synchronized (this) {
        this.watchService = this.configPath.getParent().getFileSystem().newWatchService();
        this.configPath
            .getParent()
            .register(this.watchService, StandardWatchEventKinds.ENTRY_MODIFY);
      }

      Thread watchThread = new Thread(this::watchForChanges);
      watchThread.setDaemon(true);
      watchThread.start();
    } catch (IOException e) {
      ConfigManagerException exception =
          new ConfigManagerException("Failed to start file watcher", e);
      NoraLogger.error(
          "Failed to start file watcher. This only means the file won't be reloaded at runtime.",
          exception);
    }
  }

  /**
   * Watches for changes to the configuration file and reloads the configuration if the file is
   * modified.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This method continuously monitors the configuration file for any modifications.
   *   <li>When a change is detected, it triggers a reload of the configuration to ensure that the
   *       application always uses the latest settings.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The method runs in a separate daemon thread to avoid blocking the main application flow.
   *   <li>Proper handling of {@code InterruptedException} and {@code IOException} is necessary to
   *       ensure the watcher operates smoothly.
   *   <li>If the file watcher fails to start or encounters an error, an appropriate log message is
   *       generated, but the application will continue to function without automatic configuration
   *       reloading.
   * </ul>
   *
   * <p><b>Performance:</b>
   *
   * <ul>
   *   <li>The {@code WatchService} is efficient for monitoring file system events and runs in a
   *       separate thread to minimize impact on the main application.
   *   <li>Using a daemon thread ensures that the file watcher does not prevent the JVM from
   *       shutting down.
   *   <li>Efficient handling of file system events reduces the overhead associated with monitoring
   *       file changes.
   * </ul>
   */
  private void watchForChanges() {
    final String fileName = this.configPath.getFileName().toString();
    try {
      while (!Thread.currentThread().isInterrupted()) {
        WatchKey key = watchService.take();
        for (WatchEvent<?> event : key.pollEvents()) {
          if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY
              && event.context().toString().equals(fileName)) {
            this.reload();
          }
        }
        key.reset();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      NoraLogger.warn("File watcher thread interrupted", e);
    } finally {
      try {
        watchService.close();
      } catch (IOException e) {
        throw new ConfigManagerException("Failed to close watch service", e);
      }
    }
  }

  /**
   * Reloads the configuration from the configuration file, creating the file if it does not exist.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This method ensures that the latest configuration settings are loaded from the
   *       configuration file.
   *   <li>It is useful for dynamically updating the application's configuration without restarting
   *       the application.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The method reads the configuration file and updates the properties object with the new
   *       settings.
   *   <li>If the configuration file cannot be read, an error is logged, and the existing
   *       configuration remains unchanged.
   *   <li>Proper handling of {@code IOException} is necessary to ensure smooth operation.
   * </ul>
   *
   * <p><b>Performance:</b>
   *
   * <ul>
   *   <li>It avoids unnecessary updates by comparing the new properties with the existing ones.
   *   <li>Proper synchronization ensures thread safety when accessing the properties object.
   *   <li>Efficient handling of file I/O operations reduces the overhead associated with reloading
   *       the configuration.
   * </ul>
   *
   * @throws ConfigManagerException if the configuration file cannot be read
   */
  public synchronized void reload() {
    if (!this.exists()) {
      this.create();
    }

    try (InputStream in = Files.newInputStream(this.configPath)) {
      Properties newProperties = new Properties();
      newProperties.load(in);

      if (!newProperties.equals(this.properties)) {
        return;
      }
      this.properties.clear();
      this.properties.putAll(newProperties);
      this.setConfigValues();

    } catch (IOException e) {
      ConfigManagerException exception =
          new ConfigManagerException("Failed to reload configuration", e);
      NoraLogger.error(
          "Failed to reload configuration. The config model won't reflect file changes.",
          exception);
    }
  }

  /**
   * Sets the configuration values from the properties object to the configuration model.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This method updates the configuration model with the latest values from the properties
   *       object.
   *   <li>It ensures that the configuration model reflects the current settings loaded from the
   *       configuration file.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>This method should be called after the properties object is updated, such as during the
   *       reload process.
   * </ul>
   *
   * <p><b>Performance:</b>
   *
   * <ul>
   *   <li>The method avoids unnecessary updates by only setting the configuration values when
   *       changes are detected.
   *   <li>Efficient handling of the properties object reduces the overhead associated with updating
   *       the configuration model.
   * </ul>
   */
  private void setConfigValues() {
    this.config.load(this.properties);
  }

  /**
   * Checks if the configuration file exists.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This method verifies whether the configuration file exists on the filesystem.
   *   <li>It is used to determine if the configuration file is present before attempting to load or
   *       save the configuration.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The method returns a boolean value indicating whether the configuration file exists.
   *   <li>It is used to prevent errors when attempting to load or save a non-existent configuration
   *       file.
   * </ul>
   *
   * @return {@code true} if the configuration file exists; {@code false} otherwise
   */
  public boolean exists() {
    return Files.exists(this.configPath);
  }

  /**
   * Saves the current configuration to the configuration file.
   *
   * <p><b>Why:</b>
   *
   * <ul>
   *   <li>This method ensures that the current configuration settings are persisted to the
   *       configuration file.
   *   <li>It is useful for saving changes made to the configuration model so that they can be
   *       reloaded in future application runs.
   * </ul>
   *
   * <p><b>Notes:</b>
   *
   * <ul>
   *   <li>The method validates that the configuration file exists before attempting to save.
   *   <li>If the configuration file does not exist, an exception is thrown.
   *   <li>Proper handling of {@code IOException} is necessary to ensure smooth operation.
   * </ul>
   *
   * <p><b>Performance:</b>
   *
   * <ul>
   *   <li>Proper synchronization ensures thread safety when accessing the configuration file.
   *   <li>The use of `Files.newOutputStream` ensures efficient writing to the file.
   * </ul>
   *
   * @throws ConfigManagerException if the configuration file does not exist or cannot be saved
   */
  public synchronized void save() {
    if (!this.exists()) {
      throw new ConfigManagerException("Configuration file does not exist: " + this.configPath);
    }
    try (OutputStream out = Files.newOutputStream(this.configPath)) {
      this.config.toProperties().store(out, null);
    } catch (IOException e) {
      throw new ConfigManagerException("Failed to save configuration", e);
    }
  }
}
