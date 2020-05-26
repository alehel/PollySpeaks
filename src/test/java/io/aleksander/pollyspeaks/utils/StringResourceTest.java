package io.aleksander.pollyspeaks.utils;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.util.MissingResourceException;

import static org.junit.jupiter.api.Assertions.*;

class StringResourceTest {

  @Test
  void getString_validPropertyKey() {
    String testString = StringResource.getString("testString");
    Assert.assertEquals(testString, "Test String");
  }

  @Test
  void getString_invalidPropertyKey() {
    assertThrows(MissingResourceException.class, () -> StringResource.getString("invalidPropertyKey"));
  }
}