package com.phuonghoang.messagedemo.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PalindromeService {
  private static final Set<Character> PUNCTUATIONS = Set.of(',', '.', '!', '?', ':', ';', '\'');

  public boolean isPalindrome(String string) {
    if (StringUtils.isBlank(string) || string.length() == 1) {
      return true;
    }
    final int length = string.length();
    int leftIndex = 0;
    int rightIndex = length - 1;
    while (leftIndex < length && rightIndex >= 0) {
      final char leftCharacter = Character.toLowerCase(string.charAt(leftIndex));
      if (isIgnoredCharacter(leftCharacter)) {
        leftIndex++;
        continue;
      }
      final char rightCharacter = Character.toLowerCase(string.charAt(rightIndex));
      if (isIgnoredCharacter(rightCharacter)) {
        rightIndex--;
        continue;
      }
      if (leftCharacter != rightCharacter) {
        return false;
      }
      leftIndex++;
      rightIndex--;
    }
    return true;
  }

  private boolean isIgnoredCharacter(char character) {
    if (Character.isWhitespace(character)) {
      return true;
    }
    return PUNCTUATIONS.contains(character);
  }
}
