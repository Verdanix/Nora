package unit.error;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class ErrorConsoleTest {
  private final ByteArrayOutputStream out = new ByteArrayOutputStream();

  @Test
  public void testLogInfo() {
    PrintStream ps = new PrintStream(out);
    PrintStream old = System.out;
    System.setOut(ps);
    Constants.CONSOLE_ONLY_LOGGER.error("HELLO ERROR");
    String output = this.out.toString();
    Assertions.assertTrue(output.contains("HELLO ERROR"));
    System.out.flush();
    System.setOut(old);
  }

  @Test
  public void testPlaceholder() {
    PrintStream ps = new PrintStream(out);
    PrintStream old = System.out;
    System.setOut(ps);
    Constants.CONSOLE_ONLY_LOGGER.error("{} {}", "HELLO", "PLACEHOLDER");
    String output = this.out.toString();
    Assertions.assertTrue(output.contains("HELLO PLACEHOLDER"));
    System.out.flush();
    System.setOut(old);
  }
}
