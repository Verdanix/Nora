package unit.factory;

import lombok.SneakyThrows;
import org.acadrix.stt.engines.SttEngineFactory;
import org.acadrix.stt.engines.VoskEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class GetEngineTest {
  private final SttEngineFactory engine = SttEngineFactory.getInstance(Constants.LOGGER);

  @Test
  public void testNullName() {
    Assertions.assertThrows(
        NullPointerException.class, () -> this.engine.getEngine(null, null, 16000, 16, 1));
  }

  @Test
  public void testNullEngine() {
    Assertions.assertThrows(
        IllegalArgumentException.class, () -> this.engine.getEngine("INVALID", null, 16000, 16, 1));
  }

  @Test
  @SneakyThrows
  public void success() {
    this.engine.registerEngine("VOSK", VoskEngine::new);
    Assertions.assertInstanceOf(
        VoskEngine.class, this.engine.getEngine("VOSK", "../model/", 16000, 16, 1));
  }
}
