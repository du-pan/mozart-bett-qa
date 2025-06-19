package com.mozart.bett.qa.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.mozart.bett.qa.config.BaseTest.messages;
import static com.mozart.bett.qa.constant.env.LanguageConstants.ENGLISH_LANG;
import static com.mozart.bett.qa.constant.env.LanguageConstants.GERMAN_LANG;

@Slf4j
public class LanguageUtil {
    public static void logLanguageWelcomeMessage(final String language) {
        switch (language) {
            case ENGLISH_LANG -> {
                log.info("English language is selected.");
                log.info("\uD83C\uDDEC\uD83C\uDDE7");
                log.info("\uD83C\uDDEC\uD83C\uDDE7");
                log.info("\uD83C\uDDEC\uD83C\uDDE7");
                log.info("Welcome Dear User. \uD83D\uDC4B \uD83D\uDC4B \uD83D\uDC4B");
            }
            case GERMAN_LANG -> {
                log.info("German language is selected.");
                log.info("\uD83C\uDDE9\uD83C\uDDEA");
                log.info("\uD83C\uDDE9\uD83C\uDDEA");
                log.info("\uD83C\uDDE9\uD83C\uDDEA");
                log.info("Willkommen, lieber Benutzer. \uD83D\uDC4B \uD83D\uDC4B \uD83D\uDC4B");
            }
            default -> throw new IllegalArgumentException("Unsupported language selected.");
        }
    }

    public static ResourceBundle getResourceBundle(final String language) {
        final Locale locale = Locale.forLanguageTag(language);
        return ResourceBundle.getBundle("ResourceBundle.messages", locale);
    }

    public static String getLocalizedTxtValue(final String key) {
        return messages.getString(key);
    }
}
