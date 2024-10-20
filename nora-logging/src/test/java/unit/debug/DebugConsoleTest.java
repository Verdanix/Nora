package unit.debug;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class DebugConsoleTest {
  private final ByteArrayOutputStream out = new ByteArrayOutputStream();

  @Test
  public void testLogInfo() {
    PrintStream ps = new PrintStream(out);
    PrintStream old = System.out;
    System.setOut(ps);
    Constants.CONSOLE_ONLY_LOGGER.debug("HELLO DEBUG");
    String output = this.out.toString().trim();
    Assertions.assertTrue(output.isEmpty());
    System.out.flush();
    System.setOut(old);
  }

  @Test
  public void testPlaceholder() {
    PrintStream ps = new PrintStream(out);
    PrintStream old = System.out;
    System.setOut(ps);
    Constants.CONSOLE_ONLY_LOGGER.debug("{} {}", "HELLO", "PLACEHOLDER");
    String output = this.out.toString().trim();
    Assertions.assertTrue(output.isEmpty());
    System.out.flush();
    System.setOut(old);
  }
}
