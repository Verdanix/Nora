package dev.masonroot.nora.config;

import java.util.Properties;
import lombok.NonNull;

public class ConfigImpl implements ConfigModel {
  private String name;
  private String description;
  private byte age;
  private double salary;
  private boolean eats;
  private boolean sleeps;
  private boolean codes;
  private boolean repeats;

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getDescription() {
    return this.description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public byte getAge() {
    return this.age;
  }

  @Override
  public void setAge(byte age) {
    this.age = age;
  }

  @Override
  public double getSalary() {
    return this.salary;
  }

  @Override
  public void setSalary(double salary) {
    this.salary = salary;
  }

  @Override
  public boolean isEats() {
    return this.eats;
  }

  @Override
  public void setEats(boolean eats) {
    this.eats = eats;
  }

  @Override
  public boolean isSleeps() {
    return this.sleeps;
  }

  @Override
  public void setSleeps(boolean sleeps) {
    this.sleeps = sleeps;
  }

  @Override
  public boolean isCodes() {
    return this.codes;
  }

  @Override
  public void setCodes(boolean codes) {
    this.codes = codes;
  }

  @Override
  public boolean isRepeats() {
    return this.repeats;
  }

  @Override
  public void setRepeats(boolean repeats) {
    this.repeats = repeats;
  }

  @Override
  @NonNull
  public ConfigModel load(@NonNull Properties properties) {
    this.name = properties.getProperty("name", "John Doe");
    this.description = properties.getProperty("description", "A person");
    this.age = Byte.parseByte(properties.getProperty("age", "28"));
    this.salary = Double.parseDouble(properties.getProperty("salary", "100000.0"));
    this.eats = Boolean.parseBoolean(properties.getProperty("eats", "true"));
    this.sleeps = !this.codes || !this.repeats;
    this.codes = Boolean.parseBoolean(properties.getProperty("codes", "true"));
    this.repeats = Boolean.parseBoolean(properties.getProperty("repeats", "true"));
    return this;
  }

  @Override
  @NonNull
  public Properties toProperties() {
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
