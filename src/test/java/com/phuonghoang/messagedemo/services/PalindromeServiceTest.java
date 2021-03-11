package com.phuonghoang.messagedemo.services;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class PalindromeServiceTest {
    private final PalindromeService palindromeService = new PalindromeService();

    @Test
    void emptyString() {
        then(palindromeService.isPalindrome("")).isTrue();
    }

    @Test
    void nullString() {
        then(palindromeService.isPalindrome(null)).isTrue();
    }

    @Test
    void singleItemString() {
        then(palindromeService.isPalindrome("a")).isTrue();
    }

    @Test
    void palindromeStringWithDifferentCases() {
        then(palindromeService.isPalindrome("tacoCat")).isTrue();
    }

    @Test
    void singleWordNonPalindrome() {
        then(palindromeService.isPalindrome("hello")).isFalse();
    }

    @Test
    void sentenceLengthPalindrome_ignoreWordBoundary() {
        then(palindromeService.isPalindrome("taco Cat")).isTrue();
    }

    @Test
    void sentenceLengthPalindrome_ignorePunctuationsAndWordBoundary() {
        then(palindromeService.isPalindrome("Go hang a salami, I'm a lasagna hog")).isTrue();
    }

    @Test
    void sentenceLengthNonPalindrome() {
        then(palindromeService.isPalindrome("momma made me eat my m&ms")).isFalse();
    }
}