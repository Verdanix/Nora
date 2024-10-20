package unit.factory;

import org.acadrix.stt.engines.SttEngineFactory;
import org.acadrix.stt.engines.VoskEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class GetCurrentEngineTest {
  private final SttEngineFactory factory = SttEngineFactory.getInstance(Constants.LOGGER);

  @Test
  public void testSuccess() {
    Assertions.assertNull(this.factory.getCurrentEngine());
    this.factory.useEngine(new VoskEngine());
    Assertions.assertInstanceOf(VoskEngine.class, this.factory.getCurrentEngine());
  }

  @Test
  public void testNullEngine() {
    Assertions.assertNull(this.factory.getCurrentEngine());
  }
}
