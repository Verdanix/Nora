package unit;

import java.nio.file.Path;
import org.acadrix.logging.LoggerConfiguration;
import org.acadrix.logging.NoraLogger;

public class Constants {
  private static final LoggerConfiguration LOG_FILE_CONFIG =
      new LoggerConfiguration("./logs", "file", 30, "2MB", true, false);
  private static final LoggerConfiguration DEBUG_FILE_CONFIG =
      new LoggerConfiguration("./logs", "debug", 30, "2MB", true, true);
  private static final LoggerConfiguration CONSOLE_ONLY_CONFIG =
      new LoggerConfiguration("./logs", "console", 30, "2MB", false, false);
  public static Path logPath = Path.of("./logs/file/log.log");
  public static Path debugPath = Path.of("./logs/debug/log.log");
  public static NoraLogger LOG_FILE_LOGGER = new NoraLogger(LOG_FILE_CONFIG);
  public static NoraLogger DEBUG_FILE_LOGGER = new NoraLogger(DEBUG_FILE_CONFIG);
  public static NoraLogger CONSOLE_ONLY_LOGGER = new NoraLogger(CONSOLE_ONLY_CONFIG);
}
