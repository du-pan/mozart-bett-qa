package com.mozart.bett.qa.config;

import com.mozart.bett.qa.constant.env.CapabilitiesConstants;
import com.mozart.bett.qa.constant.env.ServerConstants;
import com.mozart.bett.qa.model.WebDriverParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import static com.mozart.bett.qa.constant.env.BrowserConstants.*;
import static com.mozart.bett.qa.constant.env.CapabilitiesConstants.*;
import static org.apache.commons.lang3.BooleanUtils.FALSE;
import static org.apache.commons.lang3.BooleanUtils.TRUE;
import static org.openqa.selenium.PageLoadStrategy.NORMAL;
import static org.openqa.selenium.UnexpectedAlertBehaviour.ACCEPT_AND_NOTIFY;

@Slf4j
public class WebCapabilities extends BaseTest {
  public static String getWebBaseUrl(final String server) {
    final Map<String, String> mozartWebServers = new LinkedHashMap<>();

    mozartWebServers.put(
        ServerConstants.MOZART_BETT_ENV_DEV, ServerConstants.MOZART_BETT_ENV_DEV_URL_);
    mozartWebServers.put(
        ServerConstants.MOZART_BETT_ENV_STAG, ServerConstants.MOZART_BETT_ENV_STAG_URL_);
    mozartWebServers.put(
        ServerConstants.MOZART_BETT_ENV_PROD, ServerConstants.MOZART_BETT_ENV_PROD_URL_);

    log.info(CapabilitiesConstants.WEB_BASE_URL_MSG, server);
    return mozartWebServers.get(server);
  }

  public static void getWebCapabilities(final String browserName) {
    switch (browserName) {
      case GOOGLE_CHROME_BROWSER -> getChromeWebCapabilities();
      case MICROSOFT_EDGE_BROWSER -> getEdgeWebCapabilities();
      case MOZILLA_FIREFOX_BROWSER -> getFirefoxWebCapabilities();
      case MAC_OS_SAFARI_BROWSER -> getSafariWebCapabilities();
      default -> throw new RuntimeException("Unsupported browser type provided.");
    }
  }

  protected static void getChromeWebCapabilities() {
    log.info("Google Chrome browser is selected.");
    LoggingPreferences logPrefs = new LoggingPreferences();
    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

    HashMap<String, Object> chromePrefs = new LinkedHashMap<>();
    chromePrefs.put(WEBDRIVER_CHROME_LOGFILE, WEBDRIVER_CHROME_LOGFILE_NAME);
    chromePrefs.put(WEBDRIVER_CHROME_VERBOSE_LOGGING, TRUE);
    chromePrefs.put(DOWNLOAD_DEFAULT_DIRECTORY, TEMP_DOWNLOAD_DIR);

    ChromeOptions chromeOpts = new ChromeOptions();
    chromeOpts.setExperimentalOption(CHROMIUM_PREFS, chromePrefs);
    chromeOpts.setCapability(CHROMIUM_LOGGING_PREFS, logPrefs);
    chromeOpts.setPageLoadStrategy(NORMAL);

    driver = getWebDriver(WebDriverParams.builder().chromeOpts(chromeOpts).build());
  }

  protected static void getEdgeWebCapabilities() {
    log.info("Microsoft Edge browser is selected.");
    HashMap<String, Object> edgePrefs = new LinkedHashMap<>();
    edgePrefs.put(ALLOW_OFFICE_VIEWER_FOR_DOWNLOAD, FALSE);
    edgePrefs.put(DOWNLOAD_DEFAULT_DIRECTORY, TEMP_DOWNLOAD_DIR);

    EdgeOptions edgeOpts = new EdgeOptions();
    edgeOpts.setExperimentalOption(CHROMIUM_PREFS, edgePrefs);

    driver = getWebDriver(WebDriverParams.builder().edgeOpts(edgeOpts).build());
  }

  protected static void getFirefoxWebCapabilities() {
    log.info("Mozilla Firefox browser is selected.");
    FirefoxOptions firefoxOpts = new FirefoxOptions();
    firefoxOpts.setPlatformName(WINDOWS_PLATFORM_NAME);
    firefoxOpts.setPageLoadStrategy(NORMAL);
    firefoxOpts.setEnableDownloads(true);
    firefoxOpts.setAcceptInsecureCerts(true);

    driver = getWebDriver(WebDriverParams.builder().firefoxOpts(firefoxOpts).build());
  }

  protected static void getSafariWebCapabilities() {
    log.info("MacOS Safari browser is selected.");
    if (SystemUtils.IS_OS_MAC) {
      SafariOptions safariOpts = new SafariOptions();
      safariOpts.setPageLoadStrategy(NORMAL);
      safariOpts.setUnhandledPromptBehaviour(ACCEPT_AND_NOTIFY);
      safariOpts.setPlatformName(MAC_OS_PLATFORM_NAME);
      safariOpts.setBrowserVersion(MAC_OS_SAFARI_VERSION);
      safariOpts.setEnableDownloads(true);
      safariOpts.setAcceptInsecureCerts(true);

      driver = getWebDriver(WebDriverParams.builder().safariOpts(safariOpts).build());
    } else {
      throw new RuntimeException("Unsupported browser. Safari can be used only with macOS.");
    }
  }

  private static WebDriver getWebDriver(final WebDriverParams params) {
    try {
      if (jenkinsEnv) {
        return getRemoteWebDriver(params);
      } else {
        return getLocalWebDriver(params);
      }
    } catch (MalformedURLException | URISyntaxException e) {
      log.error(DRIVER_EXCEPTION_MSG, e.getClass().getSimpleName());
      e.printStackTrace();
      return null;
    }
  }

  private static WebDriver getRemoteWebDriver(final WebDriverParams params)
      throws MalformedURLException, URISyntaxException {
    // todo if CI/CD implemented
    switch (browserName) {
      case GOOGLE_CHROME_BROWSER -> {
        return new RemoteWebDriver(
            new URI(WEB_APP_SELENIUM_HUB_URL).toURL(), params.getChromeOpts());
      }
      case MICROSOFT_EDGE_BROWSER -> {
        return new RemoteWebDriver(new URI(WEB_APP_SELENIUM_HUB_URL).toURL(), params.getEdgeOpts());
      }
      default -> {
        return null;
      }
    }
  }

  private static WebDriver getLocalWebDriver(final WebDriverParams params) {
    switch (browserName) {
      case GOOGLE_CHROME_BROWSER -> {
        return new ChromeDriver(params.getChromeOpts());
      }
      case MICROSOFT_EDGE_BROWSER -> {
        return new EdgeDriver(params.getEdgeOpts());
      }
      case MOZILLA_FIREFOX_BROWSER -> {
        return new FirefoxDriver(params.getFirefoxOpts());
      }
      case MAC_OS_SAFARI_BROWSER -> {
        return new SafariDriver(params.getSafariOpts());
      }
      default -> {
        return null;
      }
    }
  }
}
