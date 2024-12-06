package dev.masonroot.nora.config;

import java.util.Properties;
import lombok.NonNull;

public class ConfigModel implements Config {
  private String name;
  private String description;
  private byte age;
  private double salary;
  private boolean eats;
  private boolean sleeps;
  private boolean codes;
  private boolean repeats;

  @Override
  public void handleValidity() {
    if (this.codes && this.sleeps) {
      this.sleeps = false;
    }
  }

  @Override
  public void setFields(@NonNull Properties properties) {
    this.name = properties.getProperty("name", "John Doe");
    this.description = properties.getProperty("description", "A person");
    this.age = Byte.parseByte(properties.getProperty("age", "28"));
    this.salary = Double.parseDouble(properties.getProperty("salary", "100000.0"));
    this.eats = Boolean.parseBoolean(properties.getProperty("eats", "true"));
    this.sleeps = Boolean.parseBoolean(properties.getProperty("sleeps", "false"));
    this.codes = Boolean.parseBoolean(properties.getProperty("codes", "true"));
    this.repeats = Boolean.parseBoolean(properties.getProperty("repeats", "true"));
  }

  @Override
  public @NonNull Properties toProperties() {
    Properties properties = new Properties();
    properties.setProperty("name", this.name);
    properties.setProperty("description", this.description);
    properties.setProperty("age", Byte.toString(this.age));
    properties.setProperty("salary", Double.toString(this.salary));
    properties.setProperty("eats", Boolean.toString(this.eats));
    properties.setProperty("sleeps", Boolean.toString(this.sleeps));
    properties.setProperty("codes", Boolean.toString(this.codes));
    properties.setProperty("repeats", Boolean.toString(this.repeats));
    return properties;
  }
}
