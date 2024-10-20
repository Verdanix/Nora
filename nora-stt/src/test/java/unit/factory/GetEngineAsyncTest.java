package unit.factory;

import lombok.SneakyThrows;
import org.acadrix.stt.engines.SttEngineFactory;
import org.acadrix.stt.engines.VoskEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class GetEngineAsyncTest {
  private final SttEngineFactory engine = SttEngineFactory.getInstance(Constants.LOGGER);

  @Test
  public void testNullName() {
    Assertions.assertThrows(
        NullPointerException.class, () -> this.engine.getEngineAsync(null, null, 16000, 16, 1));
  }

  @Test
  @SneakyThrows
  public void testNullEngine() {
    Assertions.assertTrue(
        this.engine.getEngineAsync("INVALID", null, 16000, 16, 1).get().isEmpty());
  }

  @Test
  @SneakyThrows
  public void success() {
    this.engine.registerEngine("VOSK", VoskEngine::new);
    Assertions.assertInstanceOf(
        VoskEngine.class,
        this.engine.getEngineAsync("VOSK", "../model/", 16000, 16, 1).get().get());
  }
}
