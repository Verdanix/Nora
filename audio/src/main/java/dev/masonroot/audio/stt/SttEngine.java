package dev.masonroot.audio.stt;

import dev.masonroot.audio.AudioInterface;
import java.nio.file.Path;
import lombok.NonNull;

public interface SttEngine {
  void initialize(@NonNull final AudioInterface audio, final Path modelPath);

  String transcribe(final long timeoutInMs);
}
