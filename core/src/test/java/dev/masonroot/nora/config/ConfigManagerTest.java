package dev.masonroot.nora.config;

import dev.masonroot.nora.exceptions.ConfigManagerException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ConfigManagerTest {
  private final Path filePath = Paths.get("").toAbsolutePath().resolve("./nora.properties");
  private final Path invalidFilePath = Paths.get("");
  private final ConfigModel model = new ConfigModel();

  @SneakyThrows
  @BeforeTest(alwaysRun = true)
  @AfterTest(alwaysRun = true)
  public void beforeAndAfterFileDeletion() {
    Files.deleteIfExists(this.filePath);
  }

  // Constructor Tests
  @Test(
      description = "Passing an invalid model to the ConfigManager constructor",
      expectedExceptions = NullPointerException.class,
      groups = {"config_constructor"})
  public void testNullModelConstructorCall() {
    new ConfigManager<>(null, this.invalidFilePath);
  }

  @Test(
      description = "Passing an invalid path to the ConfigManager constructor",
      expectedExceptions = NullPointerException.class,
      groups = {"config_constructor"})
  public void testNullPathConstructorCall() {
    new ConfigManager<>(this.model, null);
  }

  @Test(
      description = "Passing an invalid path to the ConfigManager constructor",
      expectedExceptions = IllegalArgumentException.class,
      groups = {"config_constructor"})
  public void testPathConstructorCall() {
    new ConfigManager<>(this.model, this.invalidFilePath);
  }

  @Test(
      description = "Passing an invalid path to the ConfigManager constructor",
      expectedExceptions = IllegalArgumentException.class,
      groups = {"config_constructor"})
  public void testUnReadablePathConstructorCall() {
    new ConfigManager<>(this.model, this.invalidFilePath);
  }

  // Overloaded Constructor Tests
  @Test(
      description = "Passing an invalid model to the ConfigManager constructor",
      expectedExceptions = NullPointerException.class,
      groups = {"config__overloaded_constructor"})
  public void testNullModelOverloadedConstructorCall() {
    new ConfigManager<>(null, this.invalidFilePath, false);
  }

  @Test(
      description = "Passing an invalid path to the ConfigManager constructor",
      expectedExceptions = NullPointerException.class,
      groups = {"config__overloaded_constructor"})
  public void testNullPathOverloadedConstructorCall() {
    new ConfigManager<>(this.model, null, false);
  }

  @Test(
      description = "Passing an invalid path to the ConfigManager constructor",
      expectedExceptions = IllegalArgumentException.class,
      groups = {"config__overloaded_constructor"})
  public void testUnReadablePathOverloadedConstructorCall() {
    new ConfigManager<>(this.model, this.invalidFilePath, false);
  }

  // Create Tests
  @SneakyThrows
  @Test(
      description = "Creating a new valid configuration file",
      groups = {"config_creation"})
  public void testCreateConfigWithReloadFalse() {
    Files.deleteIfExists(this.filePath);
    Assert.assertFalse(Files.exists(this.filePath));
    ConfigManager<ConfigModel> manager = new ConfigManager<>(this.model, this.filePath);
    Assert.assertTrue(manager.exists());
  }

  @Test(
      description = "Creating a new valid configuration file",
      groups = {"config_creation"})
  public void testCreateOverloadedConfigWithReloadFalse() {
    ConfigManager<ConfigModel> manager = new ConfigManager<>(this.model, this.filePath, true);
    Assert.assertTrue(manager.exists());
  }

  @Test(
      description = "Creating a new configuration file",
      expectedExceptions = IllegalArgumentException.class,
      groups = {"config_creation"})
  public void testUnReadableAndWritablePathCreate() {
    // Automatically creates upon constructor call
    new ConfigManager<>(this.model, this.invalidFilePath);
  }

  // Saving tests
  @SneakyThrows
  @Test(
      description = "Saving a configuration file",
      expectedExceptions = ConfigManagerException.class,
      groups = {"config_saving"})
  public void testNonExistentFileSave() {
    ConfigManager<ConfigModel> manager = new ConfigManager<>(this.model, this.filePath);
    Files.deleteIfExists(this.filePath);
    manager.save();
  }

  @SneakyThrows
  @Test(
      description = "Testing the catch part of saving the configuration file",
      expectedExceptions = ConfigManagerException.class,
      groups = {"config_saving"})
  public void testSaveCatch() {
    ConfigManager<ConfigModel> manager = new ConfigManager<>(this.model, this.filePath);
    Field field = manager.getClass().getDeclaredField("configPath");
    field.setAccessible(true);
    field.set(manager, this.invalidFilePath);
    manager.save();
  }

  @SneakyThrows
  @Test(
      description = "Saving a configuration file",
      groups = {"config_saving"})
  public void testSuccessfulSave() {
    ConfigManager<ConfigModel> manager = new ConfigManager<>(this.model, this.filePath);
    manager.save();
    Assert.assertTrue(Files.exists(this.filePath));
    Assert.assertFalse(Files.readString(this.filePath).isEmpty());
  }
}
