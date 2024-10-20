package org.acadrix.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.util.FileSize;
import lombok.Getter;
import org.slf4j.LoggerFactory;

/**
 * The {@code LoggerConfiguration} class is responsible for configuring the logging behavior for an
 * application. It sets up logging to the console and optionally to rolling log files, with support
 * for separate debug logging. The class uses Logback as the underlying logging framework.
 *
 * <p>The configuration includes:
 *
 * <ul>
 *   <li>Console logging with colored output.
 *   <li>File logging with rolling policies based on size and time.
 *   <li>Separate file logging for debug messages.
 *   <li>Automatic creation of log directories.
 * </ul>
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * LoggerConfiguration config = new LoggerConfiguration(
 *     "/var/logs/myapp", "myappLogger", 30, "10MB", true, true);
 * Logger logger = config.getLogger();
 * logger.info("This is an info message");
 * logger.info("This is a debug message");
 *
 * }</pre>
 *
 * <p>Dependencies:
 *
 * <ul>
 *   <li>Logback Classic
 *   <li>SLF4J API
 * </ul>
 *
 * <p>This class is thread-safe and can be used in a multi-threaded environment.
 *
 * <p>See also:
 *
 * <ul>
 *   <li>{@link Logger}
 *   <li>{@link LoggerContext}
 *   <li>{@link RollingFileAppender}
 *   <li>{@link SizeAndTimeBasedRollingPolicy}
 * </ul>
 *
 * @see ch.qos.logback.classic.Logger
 * @see ch.qos.logback.classic.LoggerContext
 * @see ch.qos.logback.core.rolling.RollingFileAppender
 * @see ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy
 * @see org.slf4j.LoggerFactory
 */
public class LoggerConfiguration {
  /**
   * The directory where the log files will be stored. The log files will be stored in a
   * subdirectory of this directory with the name of the logger.
   */
  private final String logDirectory;

  /**
   * The name of the logger. This will be used as the name of the subdirectory in the log directory
   * where the log files will be stored.
   */
  private final String loggerName;

  /**
   * The maximum number of log files to keep. When the number of log files exceeds this value, the
   * oldest log files will be deleted.
   */
  private final int maxFileHistory;

  /** The maximum size of each log file. When a log file reaches this size, it will compress. */
  private final String maxFileSize;

  /**
   * Whether to log to non-debug messages to a file. This is useful for logging messages that are
   * not debug messages.
   */
  private final boolean logFiles;

  /** Whether to include log debug messages. This is useful for logging debug messages */
  private final boolean includeDebugLogs;

  /**
   * The logger that this configuration is for.
   *
   * @see Logger
   * @see LoggerFactory
   * @see LoggerContext
   */
  @Getter private final Logger logger;

  /**
   * Creates a new LoggerConfiguration. This will configure the logger to log to the console and to
   * files if logFiles or debugLogFiles is true.
   *
   * @param logDirectory The directory where the log files will be stored.
   * @param loggerName The name of the logger.
   * @param maxFileHistory The maximum number of log files to keep.
   * @param maxFileSize The maximum size of each log file.
   * @param logFiles Whether to log to non-debug messages to a file.
   * @param includeDebugLogs Whether to include debug messages in the output..
   * @see Logger
   * @see LoggerFactory
   */
  public LoggerConfiguration(
      String logDirectory,
      String loggerName,
      int maxFileHistory,
      String maxFileSize,
      boolean logFiles,
      boolean includeDebugLogs) {
    this.logDirectory = logDirectory;
    this.loggerName = loggerName;
    this.maxFileHistory = maxFileHistory;
    this.maxFileSize = maxFileSize;
    this.logFiles = logFiles;
    this.includeDebugLogs = includeDebugLogs;
    this.logger = this.configureLogger();
  }

  /**
   * Configures a rolling file appender for the log files. The rolling file appender will log
   * messages to a file.
   *
   * @param context The logger context.
   * @return The configured rolling file appender.
   * @see RollingFileAppender
   * @see SizeAndTimeBasedRollingPolicy
   * @see LoggerContext
   * @see PatternLayoutEncoder
   */
  private RollingFileAppender<ILoggingEvent> configureLogFileAppender(LoggerContext context) {
    PatternLayoutEncoder encoder = this.configurePatternLayoutEncoder(context);
    encoder.start();

    LevelFilter filter = this.configureFilter(context);

    RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<>();
    appender.setContext(context);
    appender.setFile(String.format("%s/%s/log.log", this.logDirectory, this.loggerName));
    appender.setEncoder(encoder);
    appender.setAppend(true);
    appender.addFilter(filter);

    SizeAndTimeBasedRollingPolicy<ILoggingEvent> policy = this.configureRollingPolicy(context);
    policy.setParent(appender);
    policy.start();

    appender.setTriggeringPolicy(policy);
    appender.setRollingPolicy(policy);
    appender.start();
    return appender;
  }

  /**
   * Configures a size and time based rolling policy for the log files. The rolling policy will roll
   * the log files based on size and time.
   *
   * @param context The logger context.
   * @return The configured size and time based rolling policy.
   * @see SizeAndTimeBasedRollingPolicy
   * @see LoggerContext
   */
  private SizeAndTimeBasedRollingPolicy<ILoggingEvent> configureRollingPolicy(
      LoggerContext context) {
    SizeAndTimeBasedRollingPolicy<ILoggingEvent> rollingPolicy =
        new SizeAndTimeBasedRollingPolicy<>();
    rollingPolicy.setContext(context);
    rollingPolicy.setMaxFileSize(FileSize.valueOf(this.maxFileSize));
    rollingPolicy.setFileNamePattern(
        String.format(
            "%s/%s/compressed/%%d{yyyy-MM-dd}.%%i.gz", this.logDirectory, this.loggerName));
    rollingPolicy.setMaxHistory(this.maxFileHistory);
    return rollingPolicy;
  }

  /**
   * Configures the logger to log to the console and to files if logFiles is true. By default, the
   * logger will log non-debug messages to the console. If includeDebugLogs is true, debug messages
   * will also be logged to the console.
   *
   * @see Logger
   * @see LoggerFactory
   * @see LoggerContext
   * @see ConsoleAppender
   * @see RollingFileAppender
   * @see SizeAndTimeBasedRollingPolicy
   * @see PatternLayoutEncoder
   * @see LevelFilter
   */
  private Logger configureLogger() {
    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    Logger logger = context.getLogger(this.loggerName);
    logger.setLevel(Level.TRACE);
    logger.setAdditive(false);

    if (this.logFiles) {
      RollingFileAppender<ILoggingEvent> logFileAppender = this.configureLogFileAppender(context);
      logger.addAppender(logFileAppender);
    }
    ConsoleAppender<ILoggingEvent> consoleAppender = this.configureConsole(context);
    logger.addAppender(consoleAppender);
    return logger;
  }

  /**
   * Configures a console appender for the logger. The console appender will log messages to the
   * console.
   *
   * @param context The logger context.
   * @return The configured console appender.
   * @see LoggerContext
   * @see ConsoleAppender
   * @see PatternLayoutEncoder
   */
  private ConsoleAppender<ILoggingEvent> configureConsole(LoggerContext context) {
    ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
    consoleAppender.setContext(context);
    consoleAppender.setName("console");
    consoleAppender.setEncoder(this.configureColoredPattern(context));
    consoleAppender.addFilter(this.configureFilter(context));
    consoleAppender.start();
    return consoleAppender;
  }

  /**
   * Configures a colored pattern layout encoder for the console logs. The colored pattern layout
   * encoder will color the log messages.
   *
   * @param context The logger context.
   * @return The configured colored pattern layout encoder.
   * @see LoggerContext
   * @see PatternLayoutEncoder
   */
  private PatternLayoutEncoder configureColoredPattern(LoggerContext context) {
    PatternLayoutEncoder encoder = new PatternLayoutEncoder();
    encoder.setContext(context);
    encoder.setPattern(
        "%cyan(%d{YYYY-MM-dd HH:mm:ss}) %magenta([%thread]) [%highlight(%level)] [%green(%logger{0})]: %msg%n%throwable{full}");
    encoder.start();
    return encoder;
  }

  /**
   * Configures a non-colored pattern layout encoder for the log files. The pattern layout encoder
   * will format the log messages.
   *
   * @param context The logger context.
   * @return The configured pattern layout encoder.
   * @see LoggerContext
   * @see PatternLayoutEncoder
   */
  private PatternLayoutEncoder configurePatternLayoutEncoder(LoggerContext context) {
    PatternLayoutEncoder encoder = new PatternLayoutEncoder();
    encoder.setContext(context);
    encoder.setPattern(
        "%d{YYYY-MM-dd HH:mm:ss} [%thread] [%level] [%logger{0}]: %msg%n%throwable{full}");
    return encoder;
  }

  /**
   * Configures a level filter for the log files. The level filter will filter the log messages
   * based on the log level.
   *
   * @param context The logger context.
   * @return The configured level filter.
   * @see LoggerContext
   * @see LevelFilter
   */
  private LevelFilter configureFilter(LoggerContext context) {
    LevelFilter filter = new LevelFilter();
    filter.setContext(context);
    filter.setLevel(Level.DEBUG);
    if (!this.includeDebugLogs) {
      filter.setOnMatch(FilterReply.DENY);
      filter.setOnMismatch(FilterReply.ACCEPT);
    }
    filter.start();
    return filter;
  }
}
