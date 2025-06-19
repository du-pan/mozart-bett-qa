package com.mozart.bett.qa.page;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@Slf4j
public class NavigationPage extends BasePage {
  private List<WebElement> getPostalCodeNumberBtn() {
    return driver.findElements(By.xpath("//button[@type='button'][contains (text(), 'PLZ')]"));
  }

  private WebElement enterModalDialog() {
      return driver.findElement(By.cssSelector("div[role='alertdialog'] div.brlbs-cmpnt-dialog-box-entrance"));
  }

  private WebElement acceptCookiesBtn() {
      return driver.findElement(By.cssSelector("button.brlbs-btn-accept-all"));
  }

  public void getBrowserUrl(final String uri) {
    log.info("Try get web browser URL. Establishing connection.");
    if (uri.contains(baseUrl)) {
      handleBrowserUrlException(uri);
    } else {
      handleBrowserUrlException(baseUrl + uri);
    }
    log.info("Wait until new open page is loaded.");
  }

  private void handleBrowserUrlException(final String url) {
    try {
      driver.navigate().to(url);
      waitUntilPageLoaded();
    } catch (Exception e) {
      log.error("[{}] exception detected.", e.getClass().getSimpleName());
      log.info("Handling the exception.");
      reloadBrowserPage();
      driver.navigate().to(url);
      waitUntilPageLoaded();
    }
  }

  public void acceptAllCookies() {
    log.info("Accept all cookies on the landing screen.");
    if (enterModalDialog().isDisplayed()) {
        acceptCookiesBtn().click();
    }
  }

  public void reloadBrowserPage() {
    log.info("Reload web browser page");
    driver.navigate().refresh();
    waitUntilPageLoaded();
  }

  public void selectPostalCodeNumber(int number) {
    if (number != 0) {
      number--;
    }
    getPostalCodeNumberBtn().get(number).click();
  }
}
