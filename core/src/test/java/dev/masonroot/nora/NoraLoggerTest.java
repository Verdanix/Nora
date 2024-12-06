package dev.masonroot.nora;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NoraLoggerTest {
  private final Path filePath = Paths.get("").toAbsolutePath().resolve("./logs/nora.log");

  @SneakyThrows
  @AfterSuite(alwaysRun = true)
  public void testSetup() {
    Files.delete(this.filePath);
  }

  @BeforeTest(alwaysRun = true)
  public void testLogFileExists() {
    Assert.assertTrue(Files.exists(this.filePath));
  }

  // INFO TESTS
  @SneakyThrows
  @Test(
      description = "Non-formatted info log message",
      groups = {"info"})
  public void testInfoLog() {
    String message = "This is an info log";
    NoraLogger.info(message);
    AssertJUnit.assertTrue(Files.readString(this.filePath).contains(message));
  }

  @SneakyThrows
  @Test(
      description = "Formatted info log message",
      groups = {"info"})
  public void testFormattedInfoLog() {
    String message = "This is a ";
    String formattedMessage = "{}{} {} {}";
    NoraLogger.info(formattedMessage, message, "formatted", "info", "log");
    AssertJUnit.assertTrue(
        Files.readString(this.filePath).contains(message + "formatted info log"));
  }

  @SneakyThrows
  @Test(
      description = "An info log with throwable message",
      groups = {"info"})
  public void testThrowableInfoLog() {
    String message = "This is a throwable log message";
    NoraLogger.info(message, new Exception("Test Throwable"));
    String fileContents = Files.readString(this.filePath);
    AssertJUnit.assertTrue(
        fileContents.contains(message)
            && fileContents.contains("Exception")
            && fileContents.contains("Test Throwable"));
  }

  @Test(
      description = "Passing null as the message for info log",
      expectedExceptions = NullPointerException.class,
      groups = {"info"})
  public void testInfoLogNullMessage() {
    NoraLogger.info(null);
  }

  // WARN TESTS
  @SneakyThrows
  @Test(
      description = "Non-formatted warn log message",
      groups = {"warn"})
  public void testWarnLog() {
    String message = "This is a warn log";
    NoraLogger.warn(message);
    AssertJUnit.assertTrue(Files.readString(this.filePath).contains(message));
  }

  @SneakyThrows
  @Test(
      description = "Formatted warn log message",
      groups = {"warn"})
  public void testFormattedWarnLog() {
    String message = "This is a ";
    String formattedMessage = "{}{} {} {}";
    NoraLogger.warn(formattedMessage, message, "formatted", "warn", "log");
    AssertJUnit.assertTrue(
        Files.readString(this.filePath).contains(message + "formatted warn log"));
  }

  @SneakyThrows
  @Test(
      description = "A warn log with throwable message",
      groups = {"warn"})
  public void testThrowableWarnLog() {
    String message = "This is a throwable warn log message";
    NoraLogger.warn(message, new Exception("Test Throwable"));
    String fileContents = Files.readString(this.filePath);
    AssertJUnit.assertTrue(
        fileContents.contains(message)
            && fileContents.contains("Exception")
            && fileContents.contains("Test Throwable"));
  }

  @Test(
      description = "Passing null as the message for warn log",
      expectedExceptions = NullPointerException.class,
      groups = {"warn"})
  public void testWarnLogNullMessage() {
    NoraLogger.warn(null);
  }

  // ERROR TESTS
  @SneakyThrows
  @Test(
      description = "Non-formatted error log message",
      groups = {"error"})
  public void testErrorLog() {
    String message = "This is an error log";
    NoraLogger.error(message);
    AssertJUnit.assertTrue(Files.readString(this.filePath).contains(message));
  }

  @SneakyThrows
  @Test(
      description = "Formatted error log message",
      groups = {"error"})
  public void testFormattedErrorLog() {
    String message = "This is a ";
    String formattedMessage = "{}{} {} {}";
    NoraLogger.error(formattedMessage, message, "formatted", "error", "log");
    AssertJUnit.assertTrue(
        Files.readString(this.filePath).contains(message + "formatted error log"));
  }

  @SneakyThrows
  @Test(
      description = "An error log with throwable message",
      groups = {"error"})
  public void testThrowableErrorLog() {
    String message = "This is a throwable error log message";
    NoraLogger.error(message, new Exception("Test Throwable"));
    String fileContents = Files.readString(this.filePath);
    AssertJUnit.assertTrue(
        fileContents.contains(message)
            && fileContents.contains("Exception")
            && fileContents.contains("Test Throwable"));
  }

  @Test(
      description = "Passing null as the message for error log",
      expectedExceptions = NullPointerException.class,
      groups = {"error"})
  public void testErrorLogNullMessage() {
    NoraLogger.error(null);
  }

  // DEBUG TESTS
  @SneakyThrows
  @Test(
      description = "Non-formatted debug log message",
      groups = {"debug"})
  public void testDebugLog() {
    String message = "This is a debug log";
    NoraLogger.debug(message);
    AssertJUnit.assertTrue(Files.readString(this.filePath).contains(message));
  }

  @SneakyThrows
  @Test(
      description = "Formatted debug log message",
      groups = {"debug"})
  public void testFormattedDebugLog() {
    String message = "This is a ";
    String formattedMessage = "{}{} {} {}";
    NoraLogger.debug(formattedMessage, message, "formatted", "debug", "log");
    AssertJUnit.assertTrue(
        Files.readString(this.filePath).contains(message + "formatted debug log"));
  }

  @SneakyThrows
  @Test(
      description = "A debug log with throwable message",
      groups = {"debug"})
  public void testThrowableDebugLog() {
    String message = "This is a throwable debug log message";
    NoraLogger.debug(message, new Exception("Test Throwable"));
    String fileContents = Files.readString(this.filePath);
    AssertJUnit.assertTrue(
        fileContents.contains(message)
            && fileContents.contains("Exception")
            && fileContents.contains("Test Throwable"));
  }

  @Test(
      description = "Passing null as the message for debug log",
      expectedExceptions = NullPointerException.class,
      groups = {"debug"})
  public void testDebugLogNullMessage() {
    NoraLogger.debug(null);
  }

  // TRACE TESTS
  @SneakyThrows
  @Test(
      description = "Non-formatted trace log message",
      groups = {"trace"})
  public void testTraceLog() {
    String message = "This is a trace log";
    NoraLogger.trace(message);
    AssertJUnit.assertTrue(Files.readString(this.filePath).contains(message));
  }

  @SneakyThrows
  @Test(
      description = "Formatted trace log message",
      groups = {"trace"})
  public void testFormattedTraceLog() {
    String message = "This is a ";
    String formattedMessage = "{}{} {} {}";
    NoraLogger.trace(formattedMessage, message, "formatted", "trace", "log");
    AssertJUnit.assertTrue(
        Files.readString(this.filePath).contains(message + "formatted trace log"));
  }

  @SneakyThrows
  @Test(
      description = "A trace log with throwable message",
      groups = {"trace"})
  public void testThrowableTraceLog() {
    String message = "This is a throwable trace log message";
    NoraLogger.trace(message, new Exception("Test Throwable"));
    String fileContents = Files.readString(this.filePath);
    AssertJUnit.assertTrue(
        fileContents.contains(message)
            && fileContents.contains("Exception")
            && fileContents.contains("Test Throwable"));
  }

  @Test(
      description = "Passing null as the message for trace log",
      expectedExceptions = NullPointerException.class,
      groups = {"trace"})
  public void testTraceLogNullMessage() {
    NoraLogger.trace(null);
  }
}
