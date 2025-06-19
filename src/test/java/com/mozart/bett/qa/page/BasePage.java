package com.mozart.bett.qa.page;

import com.mozart.bett.qa.config.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

import static com.mozart.bett.qa.constant.env.WebElementConstants.*;

@Slf4j
public class BasePage extends BaseTest {
  public static void waitImplicitMillis(final Integer milliseconds) {
    log.info("Implicit web driver wait started. Waiting for the [{}] milliseconds.", milliseconds);
    try {
      Thread.sleep(milliseconds);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  public static WebDriverWait waiter() {
    log.info("Web driver wait started.");
    waitImplicitMillis(500);
    return new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofMillis(500));
  }

  public static JavascriptExecutor jsExecutor() {
    return (JavascriptExecutor) driver;
  }

  public static void waitUntilPageLoaded() {
    log.info("Wait until page is loaded.");
    log.info("JavaScript execution on page to be loaded");
    waitImplicitMillis(250);
    waiter()
        .until(
            driver ->
                Objects.equals(jsExecutor().executeScript(JS_READY_STATE), JS_COMPLETE_STATE));
    waitImplicitMillis(250);
  }

  public static void waitUntilElementClickable(final WebElement element) {
    log.info("Wait until WebElement is clickable.");
    waiter().until(ExpectedConditions.elementToBeClickable(element));
  }

  /**
   * WebElement displayed check has to be done directly via selector when displayed state is used as
   * e.g. IF condition. Not as a WebElement variable or method. Using this approach
   * NoSuchElementException or StaleElementReferenceException exceptions are handled.
   *
   * @param selector WebElement selector
   * @return boolean
   */
  public static boolean getWebElementDisplayed(final String selector) {
    log.info("Check if given WebElement is displayed.");
    setDriverGlobalTimeouts(250);
    boolean isDisplayed;
    try {
      final WebElement webElement = driver.findElement(By.cssSelector(selector));
      isDisplayed = webElement.isDisplayed();
    } catch (NoSuchElementException | StaleElementReferenceException e) {
      log.error("[{}] exception detected.", e.getClass().getSimpleName());
      log.info("WebElement currently is not displayed.");
      isDisplayed = false;
    }
    setDriverGlobalTimeouts(30000);
    return isDisplayed;
  }

  public static void scrollToElementView(final WebElement element) {
    jsExecutor().executeScript(JS_SCROLL_INTO_VIEW, element);
    waitImplicitMillis(250);
  }
}
