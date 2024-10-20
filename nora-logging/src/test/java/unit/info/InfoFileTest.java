package unit.info;

import java.nio.file.Files;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class InfoFileTest {
  @SneakyThrows
  @BeforeEach
  public void setup() {
    Files.writeString(Constants.logPath, "");
  }

  @Test
  @SneakyThrows
  public void testFile() {
    Constants.LOG_FILE_LOGGER.info("HELLO INFO");
    Assertions.assertTrue(Files.exists(Constants.logPath));
    Assertions.assertTrue(Files.readString(Constants.logPath).contains("HELLO INFO"));
  }

  @Test
  @SneakyThrows
  public void testFilePlaceholder() {
    Constants.LOG_FILE_LOGGER.info("{} {}", "HELLO", "PLACEHOLDER");
    Assertions.assertTrue(Files.exists(Constants.logPath));
    Assertions.assertTrue(Files.readString(Constants.logPath).contains("HELLO PLACEHOLDER"));
  }
}
