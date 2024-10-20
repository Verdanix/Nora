package unit.info;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class InfoConsoleTest {
  private final ByteArrayOutputStream out = new ByteArrayOutputStream();

  @Test
  public void testLogInfo() {
    PrintStream ps = new PrintStream(out);
    PrintStream old = System.out;
    System.setOut(ps);
    Constants.CONSOLE_ONLY_LOGGER.info("HELLO INFO");
    String output = this.out.toString();
    Assertions.assertTrue(output.contains("HELLO INFO"));
    System.out.flush();
    System.setOut(old);
  }

  @Test
  public void testPlaceholder() {
    PrintStream ps = new PrintStream(out);
    PrintStream old = System.out;
    System.setOut(ps);
    Constants.CONSOLE_ONLY_LOGGER.info("{} {}", "HELLO", "PLACEHOLDER");
    String output = this.out.toString();
    Assertions.assertTrue(output.contains("HELLO PLACEHOLDER"));
    System.out.flush();
    System.setOut(old);
  }
}
