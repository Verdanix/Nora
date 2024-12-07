package dev.masonroot.nora.config;

public interface ConfigModel extends Config<ConfigModel> {
  String getName();

  void setName(String name);

  String getDescription();

  void setDescription(String description);

  byte getAge();

  void setAge(byte age);

  double getSalary();

  void setSalary(double salary);

  boolean isEats();

  void setEats(boolean eats);

  boolean isSleeps();

  void setSleeps(boolean sleeps);

  boolean isCodes();

  void setCodes(boolean codes);

  boolean isRepeats();

  void setRepeats(boolean repeats);
}
