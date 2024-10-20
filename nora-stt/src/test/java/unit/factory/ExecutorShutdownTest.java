package unit.factory;

import org.acadrix.stt.engines.SttEngineFactory;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class ExecutorShutdownTest {
  private final SttEngineFactory factory = SttEngineFactory.getInstance(Constants.LOGGER);

  @Test
  public void testShutdown() {
    factory.shutdown();
  }
}
