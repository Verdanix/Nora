package unit;

import org.acadrix.logging.LoggerConfiguration;
import org.acadrix.logging.NoraLogger;

public class Constants {
  private static final LoggerConfiguration LOGGER_CONFIGURATION =
      new LoggerConfiguration("logs", "nora-stt", 10, "10MB", false, false);

  public static NoraLogger LOGGER = new NoraLogger(LOGGER_CONFIGURATION);
}
