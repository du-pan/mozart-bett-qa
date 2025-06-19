package com.mozart.bett.qa.page;

import com.mozart.bett.qa.constant.enums.NavBarOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class NavigationBarPage extends BasePage {
  private WebElement getNavigationBarBtn(final String text) {
    return driver.findElement(
        By.xpath("//div/a[@data-sentry-component='NavbarLink'][normalize-space()='" + text + "']"));
  }

  public void selectNavigationOption(final NavBarOption navBarOpt) {
    getNavigationBarBtn(navBarOpt.getGetValue()).click();
  }
}
