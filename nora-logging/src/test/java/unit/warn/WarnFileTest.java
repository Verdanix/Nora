package unit.warn;

import java.nio.file.Files;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class WarnFileTest {
  @SneakyThrows
  @BeforeEach
  public void setup() {
    Files.writeString(Constants.logPath, "");
  }

  @Test
  @SneakyThrows
  public void testFile() {
    Constants.LOG_FILE_LOGGER.warn("HELLO WARN");
    Assertions.assertTrue(Files.exists(Constants.logPath));
    Assertions.assertTrue(Files.readString(Constants.logPath).contains("HELLO WARN"));
  }

  @Test
  @SneakyThrows
  public void testFilePlaceholder() {
    Constants.LOG_FILE_LOGGER.warn("{} {}", "HELLO", "PLACEHOLDER");
    Assertions.assertTrue(Files.exists(Constants.logPath));
    Assertions.assertTrue(Files.readString(Constants.logPath).contains("HELLO PLACEHOLDER"));
  }
}
