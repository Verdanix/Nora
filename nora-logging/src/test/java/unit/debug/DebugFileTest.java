package unit.debug;

import java.nio.file.Files;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class DebugFileTest {
  @SneakyThrows
  @BeforeEach
  public void setup() {
    Files.writeString(Constants.debugPath, "");
  }

  @Test
  @SneakyThrows
  public void testFilePlaceholder() {
    Constants.DEBUG_FILE_LOGGER.debug("{} {}", "HELLO", "PLACEHOLDER");
    Assertions.assertTrue(Files.exists(Constants.debugPath));
    Assertions.assertTrue(Files.readString(Constants.debugPath).contains("HELLO PLACEHOLDER"));
  }

  @Test
  @SneakyThrows
  public void testFile() {
    Constants.DEBUG_FILE_LOGGER.debug("HELLO DEBUG");
    Assertions.assertTrue(Files.exists(Constants.debugPath));
    Assertions.assertTrue(Files.readString(Constants.debugPath).contains("HELLO DEBUG"));
  }
}
