package dev.masonroot.nora.exceptions;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigManagerExceptionTest {
  @Test(description = "Test ConfigManagerException")
  public void testConfigManagerExceptionWithMessage() {
    ConfigManagerException exception = new ConfigManagerException("Test message");
    Assert.assertEquals(exception.getMessage(), "Test message");
  }

  @Test(description = "Test ConfigManagerException")
  public void testConfigManagerExceptionWithThrowable() {
    ConfigManagerException exception = new ConfigManagerException("Test message", new Exception());
    Assert.assertEquals(exception.getMessage(), "Test message");
    Assert.assertNotNull(exception.getCause());
  }

  @Test(
      description = "Test ConfigManagerException",
      expectedExceptions = ConfigManagerException.class)
  public void testThrownConfigManagerExceptionWithThrowable() {
    throw new ConfigManagerException("Test message", new Exception());
  }

  @Test(
      description = "Test ConfigManagerException",
      expectedExceptions = ConfigManagerException.class)
  public void testThrownConfigManagerExceptionWithMessage() {
    throw new ConfigManagerException("Test message");
  }
}
