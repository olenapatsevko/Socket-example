package com.robosh.util;

public class StringUtil {

  private final static String REMOVE_SPACES = "\\s+";

  private StringUtil() {

  }

  public static String removeSpaces(String input) {
    return input.replaceAll(REMOVE_SPACES, " ").trim();
  }
}
