package unit.factory;

import org.acadrix.stt.engines.SttEngineFactory;
import org.acadrix.stt.engines.VoskEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class RegisterEngineTest {
  private final SttEngineFactory engine = SttEngineFactory.getInstance(Constants.LOGGER);

  @Test
  public void testNullName() {
    Assertions.assertThrows(
        NullPointerException.class, () -> this.engine.registerEngine(null, VoskEngine::new));
  }

  @Test
  public void testNullSupplier() {
    Assertions.assertThrows(
        NullPointerException.class, () -> this.engine.registerEngine("VOSK", null));
  }
}
