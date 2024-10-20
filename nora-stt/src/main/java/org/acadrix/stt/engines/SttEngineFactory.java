package org.acadrix.stt.engines;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import javax.sound.sampled.LineUnavailableException;
import org.acadrix.logging.NoraLogger;

/**
 * The {@code SttEngineFactory} class provides a factory for creating and managing instances of
 * {@link SttEngine} implementations. It allows for registering and creating instances of different
 * STT engines, and provides a way to switch between them at runtime.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * SttEngineFactory factory = SttEngineFactory.getInstance(logger);
 * factory.registerEngine("vosk", VoskEngine::new);
 * SttEngine engine = factory.getEngine("vosk", "model", 16000, 16, 1);
 * engine.listen();
 * }</pre>
 *
 * <p>This class is thread-safe and can be used in a multi-threaded environment.
 *
 * <p>See also:
 *
 * <ul>
 *   <li>{@link SttEngine}
 *   <li>{@link VoskEngine}
 *   <li>{@link NoraLogger}
 *   <li>{@link AtomicReference}
 *   <li>{@link ConcurrentHashMap}
 *   <li>{@link CompletableFuture}
 *   <li>{@link ExecutorService}
 *   <li>{@link Executors}
 * </ul>
 */
public class SttEngineFactory {
  /**
   * The singleton instance of the SttEngineFactory class. This is lazily initialized using the
   * AtomicReference class.
   */
  private static final AtomicReference<SttEngineFactory> INSTANCE = new AtomicReference<>();

  /** The current engine instance that is being used. */
  private final AtomicReference<SttEngine> CURRENT_ENGINE;

  /** A map of engine names to engine suppliers. */
  private final Map<String, Supplier<SttEngine>> ENGINE_SUPPLIERS;

  /** An executor service for running asynchronous tasks. */
  private final ExecutorService executor;

  /** The logger instance used for logging messages. */
  private final NoraLogger logger;

  /**
   * Constructs a new instance of the {@code SttEngineFactory} class.
   *
   * <p>This constructor initializes the factory with the provided {@link NoraLogger} instance. It
   * sets up the necessary internal structures for managing and creating instances of {@link
   * SttEngine} implementations. Specifically, it initializes the following components:
   *
   * <ul>
   *   <li>{@code CURRENT_ENGINE}: An {@link AtomicReference} to hold the current {@link SttEngine}
   *       instance.
   *   <li>{@code ENGINE_SUPPLIERS}: A {@link ConcurrentHashMap} to store engine names and their
   *       corresponding suppliers.
   *   <li>{@code executor}: An {@link ExecutorService} for running asynchronous tasks, initialized
   *       as a cached thread pool.
   *   <li>{@code logger}: The {@link NoraLogger} instance used for logging messages.
   * </ul>
   *
   * <p>This constructor is private to enforce the singleton pattern. Instances of this class should
   * be obtained through the {@link #getInstance(NoraLogger)} method.
   *
   * @param logger the {@link NoraLogger} instance used for logging messages; must not be null
   * @throws NullPointerException if the {@code logger} parameter is null
   * @see NoraLogger
   * @see AtomicReference
   * @see ConcurrentHashMap
   * @see Executors
   */
  private SttEngineFactory(NoraLogger logger) {
    this.CURRENT_ENGINE = new AtomicReference<>();
    this.ENGINE_SUPPLIERS = new ConcurrentHashMap<>();
    this.executor = Executors.newCachedThreadPool();
    this.logger = logger;
  }

  /**
   * Returns the singleton instance of the {@code SttEngineFactory} class.
   *
   * <p>This method ensures that only one instance of the {@code SttEngineFactory} class is created
   * and returned. It uses the {@link
   * AtomicReference#updateAndGet(java.util.function.UnaryOperator)} method to lazily initialize the
   * instance if it is not already created. The provided {@link NoraLogger} instance is used for
   * logging messages during the initialization process.
   *
   * @param logger the {@link NoraLogger} instance used for logging messages; must not be null
   * @return the singleton instance of the {@code SttEngineFactory} class
   * @throws NullPointerException if the {@code logger} parameter is null
   * @see NoraLogger
   * @see AtomicReference
   */
  public static SttEngineFactory getInstance(NoraLogger logger) {
    return SttEngineFactory.INSTANCE.updateAndGet(
        instance -> {
          if (instance == null) {
            instance = new SttEngineFactory(logger);
          }
          return instance;
        });
  }

  /**
   * Registers a new STT engine with the factory.
   *
   * <p>This method allows for registering a new {@link SttEngine} implementation with the factory.
   * The engine is registered with a unique name, which can later be used to retrieve and initialize
   * the engine instance. The engine supplier is a functional interface that provides a new instance
   * of the engine when called.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * SttEngineFactory factory = SttEngineFactory.getInstance(logger);
   * factory.registerEngine("vosk", VoskEngine::new);
   * }</pre>
   *
   * @param engineName the unique name of the engine to register; must not be null
   * @param engine the supplier that provides a new instance of the engine; must not be null
   * @throws NullPointerException if the {@code engineName} or {@code engine} parameter is null
   * @see Supplier
   * @see SttEngine
   * @see Objects#requireNonNull
   */
  public void registerEngine(String engineName, Supplier<SttEngine> engine) {
    Objects.requireNonNull(engineName, "engineName cannot be null");
    Objects.requireNonNull(engine, "engine supplier cannot be null");

    this.logger.debug("Registering engine \"{}\".", engineName);
    this.ENGINE_SUPPLIERS.compute(engineName, (key, existing) -> engine);
    this.logger.debug("Successfully registered \"{}\".", engineName);
  }

  /**
   * Asynchronously retrieves an instance of the specified STT engine.
   *
   * <p>This method allows for retrieving an instance of a registered {@link SttEngine}
   * implementation asynchronously. The engine is identified by its unique name, and the method
   * initializes the engine with the provided model path, sample rate, sample size, and number of
   * channels. The method returns a {@link Future} that will complete with an {@link Optional}
   * containing the engine instance, or an empty {@link Optional} if the engine could not be
   * created.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * SttEngineFactory factory = SttEngineFactory.getInstance(logger);
   * Future<Optional<SttEngine>> futureEngine = factory.getEngineAsync("vosk", "model", 16000, 16, 1);
   * futureEngine.thenAccept(engineOpt -> engineOpt.ifPresent(SttEngine::listen));
   * }</pre>
   *
   * @param engineName the unique name of the engine to retrieve; must not be null
   * @param model the path to the model to be used by the engine; must not be null
   * @param sampleRate the sample rate for the audio input
   * @param sampleSize the sample size for the audio input
   * @param channels the number of audio channels
   * @return a {@link Future} that will complete with an {@link Optional} containing the engine
   *     instance, or an empty {@link Optional} if the engine could not be created
   * @throws NullPointerException if the {@code engineName} parameter is null
   * @see SttEngine
   * @see Optional
   * @see Future
   * @see CompletableFuture
   */
  public Future<Optional<SttEngine>> getEngineAsync(
      String engineName, String model, float sampleRate, int sampleSize, int channels) {
    Objects.requireNonNull(engineName, "engineName cannot be null");
    return CompletableFuture.supplyAsync(
            () -> {
              try {
                logger.debug("Getting engine \"{}\" asynchronously.", engineName);
                return Optional.of(getEngine(engineName, model, sampleRate, sampleSize, channels));
              } catch (Exception e) {
                logger.error("Failed to get engine: {}", e.getMessage(), e);
                throw new CompletionException(e);
              }
            },
            executor)
        .handle(
            (result, ex) -> {
              if (ex != null) {
                logger.error("Async engine creation failed", ex.getCause());
                return Optional.empty();
              }
              return result;
            });
  }

  /**
   * Retrieves an instance of the specified STT engine.
   *
   * <p>This method allows for retrieving an instance of a registered {@link SttEngine}
   * implementation. The engine is identified by its unique name, and the method initializes the
   * engine with the provided model path, sample rate, sample size, and number of channels. If the
   * engine cannot be found or initialized, an appropriate exception is thrown.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * SttEngineFactory factory = SttEngineFactory.getInstance(logger);
   * SttEngine engine = factory.getEngine("vosk", "model", 16000, 16, 1);
   * engine.listen();
   * }</pre>
   *
   * @param engineName the unique name of the engine to retrieve; must not be null
   * @param model the path to the model to be used by the engine; must not be null
   * @param sampleRate the sample rate for the audio input
   * @param sampleSize the sample size for the audio input
   * @param channels the number of audio channels
   * @return the instance of the specified {@link SttEngine}
   * @throws IOException if an I/O error occurs during engine initialization
   * @throws LineUnavailableException if the audio line cannot be opened
   * @throws IllegalArgumentException if the engine cannot be found or initialized
   * @see SttEngine
   */
  public SttEngine getEngine(
      String engineName, String model, float sampleRate, int sampleSize, int channels)
      throws IOException, LineUnavailableException, IllegalArgumentException {
    Objects.requireNonNull(engineName, "engineName cannot be null");
    Supplier<SttEngine> factory = this.ENGINE_SUPPLIERS.get(engineName);

    this.logger.debug("SttEngine null check: {}", factory == null);
    if (factory == null) {
      this.logger.error("Engine not found: {}", engineName);
      throw new IllegalArgumentException("Engine not found");
    }

    SttEngine engine = factory.get();

    this.logger.debug("Engine null check: {}", engine == null);
    if (engine == null) {
      this.logger.error("Failed to create engine instance for: {}.", engineName);
      throw new IllegalArgumentException("Failed to create engine instance.");
    }

    this.logger.debug(
        "Initializing engine with model: {}, sampleRate: {}, sampleSize: {}, channels: {}",
        model,
        sampleRate,
        sampleSize,
        channels);
    if (!engine.initialize(this.logger, model, sampleRate, sampleSize, channels)) {
      throw new IllegalArgumentException("Engine initialization failed");
    }

    this.CURRENT_ENGINE.set(engine);
    this.logger.debug("Returning engine instance.");
    return engine;
  }

  /**
   * Sets the specified STT engine as the current engine.
   *
   * <p>This method allows for setting a specific {@link SttEngine} instance as the current engine
   * to be used by the factory. The provided engine instance must not be null. Once set, the current
   * engine can be retrieved using the {@link #getCurrentEngine()} method.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * SttEngineFactory factory = SttEngineFactory.getInstance(logger);
   * SttEngine engine = new VoskEngine();
   * factory.useEngine(engine);
   * }</pre>
   *
   * @param engine the {@link SttEngine} instance to set as the current engine; must not be null
   * @throws NullPointerException if the {@code engine} parameter is null
   * @see SttEngine
   * @see #getCurrentEngine()
   */
  public void useEngine(SttEngine engine) {
    Objects.requireNonNull(engine, "engine cannot be null");
    this.logger.debug("Setting current engine to: {}", engine.getClass().getSimpleName());
    this.CURRENT_ENGINE.set(engine);
    this.logger.debug("Current engine set to: {}", engine.getClass().getSimpleName());
  }

  /**
   * Retrieves the current STT engine instance.
   *
   * <p>This method returns the current {@link SttEngine} instance that is set in the factory. The
   * current engine is the one that has been set using the {@link #useEngine(SttEngine)} method or
   * the one that has been retrieved and initialized using the {@link #getEngine(String, String,
   * float, int, int)} method.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * SttEngineFactory factory = SttEngineFactory.getInstance(logger);
   * SttEngine currentEngine = factory.getCurrentEngine();
   * if (currentEngine != null) {
   *     currentEngine.listen();
   * }
   * }</pre>
   *
   * @return the current {@link SttEngine} instance, or {@code null} if no engine is set
   * @see SttEngine
   * @see #useEngine(SttEngine)
   * @see #getEngine(String, String, float, int, int)
   */
  public SttEngine getCurrentEngine() {
    return this.CURRENT_ENGINE.get();
  }

  /**
   * Shuts down the executor service used by the factory.
   *
   * <p>This method initiates an orderly shutdown of the {@link ExecutorService} used for running
   * asynchronous tasks. No new tasks will be accepted, but previously submitted tasks will be
   * executed. This method should be called when the factory is no longer needed to ensure that all
   * resources are properly released.
   *
   * <p>Example usage:
   *
   * <pre>{@code
   * SttEngineFactory factory = SttEngineFactory.getInstance(logger);
   * factory.shutdown();
   * }</pre>
   *
   * @see ExecutorService#shutdown()
   */
  public void shutdown() {
    this.executor.shutdown();
  }
}
