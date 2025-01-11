package dev.masonroot.audio.stt;

import dev.masonroot.audio.AudioInterface;
import dev.masonroot.common.NoraLogger;
import java.io.IOException;
import java.nio.file.Path;
import lombok.NonNull;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

public class VoskSttEngine implements SttEngine, AutoCloseable {
  private AudioInterface audioInterface;
  private Path modelPath;
  private Model model;
  private Recognizer recognizer;

  @Override
  public void initialize(@NonNull final AudioInterface audio, @NonNull final Path modelPath) {
    LibVosk.setLogLevel(LogLevel.INFO);
    this.audioInterface = audio;
    this.modelPath = modelPath;
    try {
      this.model = new Model(this.modelPath.toString());
      this.recognizer = new Recognizer(this.model, audio.microphone().getFormat().getSampleRate());
    } catch (IOException e) {
      NoraLogger.trace("Failed to load Vosk model.", e);
    }
  }

  @Override
  public String transcribe(long timeoutInMs) {
    NoraLogger.info("Transcribing audio...");
    byte[] bytes = this.audioInterface.read(timeoutInMs);
    this.recognizer.acceptWaveForm(bytes, bytes.length);
    return this.trimFinalResult(this.recognizer.getFinalResult());
  }

  private String trimFinalResult(String result) {
    return result.replaceAll("(\\{\\s+[\"\\w\\s:\\s]+\\s\"|\"\\s+\\})", "");
  }

  @Override
  public void close() throws Exception {
    if (this.model == null) {
      return;
    }
    this.model.close();
  }
}
