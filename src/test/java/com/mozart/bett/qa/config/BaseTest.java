package com.mozart.bett.qa.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.ResourceBundle;

import static com.mozart.bett.qa.config.WebCapabilities.getWebBaseUrl;
import static com.mozart.bett.qa.config.WebCapabilities.getWebCapabilities;
import static com.mozart.bett.qa.constant.env.BrowserConstants.*;
import static com.mozart.bett.qa.constant.env.CapabilitiesConstants.REST_ASSURED;
import static com.mozart.bett.qa.constant.env.EnvironmentConstants.*;
import static com.mozart.bett.qa.constant.env.LanguageConstants.GERMAN_LANG;
import static com.mozart.bett.qa.constant.env.LanguageConstants.LANGUAGE_PROPERTY;
import static com.mozart.bett.qa.constant.env.ServerConstants.MOZART_BETT_ENV_DEV;
import static com.mozart.bett.qa.constant.env.ServerConstants.SERVER_PROPERTY;
import static com.mozart.bett.qa.util.LanguageUtil.getResourceBundle;
import static java.lang.Boolean.parseBoolean;

@Slf4j
public abstract class BaseTest {
  public static WebDriver driver;
  public static String baseUrl;
  public static ResourceBundle messages;
  public static String TEMP_DOWNLOAD_DIR;
  public static final String browserName =
      StringUtils.isNotBlank(System.getProperty(BROWSER_PROPERTY))
          ? System.getProperty(BROWSER_PROPERTY)
          : GOOGLE_CHROME_BROWSER;
  public static final String environment =
      StringUtils.isNotBlank(System.getProperty(ENV_PROPERTY))
          ? System.getProperty(ENV_PROPERTY)
          : WEB_APP;
  public static final String server =
      StringUtils.isNotBlank(System.getProperty(SERVER_PROPERTY))
          ? System.getProperty(SERVER_PROPERTY)
          : MOZART_BETT_ENV_DEV;
  public static final String language =
      StringUtils.isNotBlank(System.getProperty(LANGUAGE_PROPERTY))
          ? System.getProperty(LANGUAGE_PROPERTY)
          : GERMAN_LANG;
  public static final Boolean jenkinsEnv =
      StringUtils.isNotBlank(System.getProperty(JENKINS_ENV_PROPERTY))
          && parseBoolean(System.getProperty(JENKINS_ENV_PROPERTY));

  @BeforeTest(alwaysRun = true)
  public void setDriver() {
    messages = getResourceBundle(language);
    final Properties props = new Properties();
    try {
      log.info("Load properties file.");
      final FileReader reader = new FileReader("src/test/resources/mozart-bett.qa.properties");
      props.load(reader);
      TEMP_DOWNLOAD_DIR = new File(props.getProperty("downloadDir")).getAbsolutePath();
    } catch (IOException e) {
      log.error("IOException detected. Driver quit.");
      log.error("Properties file could not be loaded.");
      log.error(e.getMessage());
    }

    log.info("Setting up [{}] testing environment.", environment);
    switch (environment) {
      case API_APP -> log.info("API testing env can be implemented [{}]", REST_ASSURED);
      case WEB_APP -> {
        baseUrl = getWebBaseUrl(server);
        getWebCapabilities(browserName);
        log.info("Server [{}] selected which is on [{}] URL", server, baseUrl);
      }
      default ->
          throw new RuntimeException(
              String.format(
                  "Unsupported environment parameters provided: [%s][%s]", environment, server));
    }

    if (environment.equals(WEB_APP)) {
      if (!browserName.contains(MAC_OS_SAFARI_BROWSER)) {
        log.info("Web driver delete all cookies.");
        driver.manage().deleteAllCookies();
      }
      log.info("Maximize window size.");
      driver.manage().window().maximize();
      setDriverGlobalTimeouts(30000);
    }
  }
  protected static void setDriverGlobalTimeouts(final int millis) {
    final String timeout = Double.valueOf((double) millis / 1000).toString();
    log.info("Setting up page load timeout for [{}] seconds.", timeout);
    driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(millis));
    log.info("Setting up script timeout for [{}] seconds.", timeout);
    driver.manage().timeouts().scriptTimeout(Duration.ofMillis(millis));
    log.info("Setting implicitly wait for [{}] seconds.", timeout);
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(millis));
  }

  @AfterTest(alwaysRun = true)
  public void tearDown() {
    log.info("Tear down step. Driver shutdown...");
    log.info("Good bye Dear User :-) \uD83D\uDC4B \uD83D\uDC4B \uD83D\uDC4B");
    if (environment.equals(WEB_APP) && driver != null) {
      log.info("Selenium WebDriver test execution is finished.");
      log.info("WebDriver quit...");
      log.info("WebDriver quit...");
      log.info("WebDriver quit...");
      driver.quit();
    }
  }
}
