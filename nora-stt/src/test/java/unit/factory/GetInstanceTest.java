package unit.factory;

import org.acadrix.stt.engines.SttEngineFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import unit.Constants;

public class GetInstanceTest {
  private final SttEngineFactory engine = SttEngineFactory.getInstance(Constants.LOGGER);

  @Test
  public void test() {
    Assertions.assertNotNull(this.engine);
  }
}
