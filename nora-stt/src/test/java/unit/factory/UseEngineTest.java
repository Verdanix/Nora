package unit.factory;

import org.acadrix.stt.engines.SttEngineFactory;
import org.acadrix.stt.engines.VoskEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class UseEngineTest {
  private final SttEngineFactory factory = SttEngineFactory.getInstance(Constants.LOGGER);

  @Test
  public void testNullEngine() {
    Assertions.assertThrows(NullPointerException.class, () -> this.factory.useEngine(null));
  }

  @Test
  public void testSuccess() {
    this.factory.useEngine(new VoskEngine());
    Assertions.assertInstanceOf(VoskEngine.class, this.factory.getCurrentEngine());
  }
}
