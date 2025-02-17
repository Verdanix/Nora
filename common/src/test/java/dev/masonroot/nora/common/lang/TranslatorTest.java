package dev.masonroot.nora.common.lang;

import java.util.MissingResourceException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TranslatorTest {

  @Test(
      description = "Tests the translation of the word 'english' in US English.",
      groups = {"translator"})
  public void testUsEnglish() {
    Translator.setSystemLocale(NoraLocales.EN_US);
    Assert.assertEquals(Translator.translate("english"), "US");
  }

  @Test(
      description = "Tests the translation of the word 'english' in UK English.",
      groups = {"translator"})
  public void testUkEnglish() {
    Translator.setSystemLocale(NoraLocales.EN_UK);
    Assert.assertEquals(Translator.translate("english"), "UK");
  }

  @Test(
      description = "Tests the translation of a non-existent key.",
      groups = {"translator"},
      expectedExceptions = NullPointerException.class)
  public void testSetInvalidLocale() {
    Translator.setSystemLocale(null);
  }

  @Test(
      description = "Tests the translation of a non-existent key.",
      groups = {"translator"},
      expectedExceptions = MissingResourceException.class)
  public void testNonExistentKey() {
    Translator.setSystemLocale(NoraLocales.EN_US);
    Assert.assertEquals(Translator.translate("non_existent_key"), "non_existent_key");
  }
}
