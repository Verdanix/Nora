package unit.warn;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class WarnConsoleTest {
  private final ByteArrayOutputStream out = new ByteArrayOutputStream();

  @Test
  public void testLogInfo() {
    PrintStream ps = new PrintStream(out);
    PrintStream old = System.out;
    System.setOut(ps);
    Constants.CONSOLE_ONLY_LOGGER.warn("HELLO WARN");
    String output = this.out.toString();
    Assertions.assertTrue(output.contains("HELLO WARN"));
    System.out.flush();
    System.setOut(old);
  }

  @Test
  public void testPlaceholder() {
    PrintStream ps = new PrintStream(out);
    PrintStream old = System.out;
    System.setOut(ps);
    Constants.CONSOLE_ONLY_LOGGER.warn("{} {}", "HELLO", "PLACEHOLDER");
    String output = this.out.toString();
    Assertions.assertTrue(output.contains("HELLO PLACEHOLDER"));
    System.out.flush();
    System.setOut(old);
  }
}
