package com.mozart.bett.qa.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import static com.mozart.bett.qa.util.LanguageUtil.getLocalizedTxtValue;

public class MatressHardnessPage extends BasePage {
  private WebElement hardnessDegreeBtn() {
    return driver.findElement(
        By.cssSelector("button[data-sentry-source-file='mattress-hardness.tsx']"));
  }

  private WebElement deepSleepFormelBtn() {
    return driver.findElement(
        By.xpath(
            "//button[contains (@class, 'mozart-button')]/span[contains (text(), 'mit Deep-Sleep-Formel berechnen')]"));
  }

  private WebElement heightSlider() {
    return driver.findElement(By.cssSelector("input.quiz-slider[max='220']"));
  }

  private WebElement weightSlider() {
    return driver.findElement(By.cssSelector("input.quiz-slider[max='150']"));
  }

  private WebElement meinePartnerBtn() {
    return driver.findElement(
        By.xpath(
            "//span[contains (text(), 'Meine Empfehlungen für die Partnerseite übernehmen')]/ancestor::button"));
  }

  private WebElement resultScreenLblTxt() {
    return driver.findElement(By.cssSelector("div.result-screen > h2"));
  }

  public void getDeepSleepResults(final int height, final int weight) {
    hardnessDegreeBtn().click();
    waitUntilElementClickable(deepSleepFormelBtn());
    deepSleepFormelBtn().click();
    heightSlider().sendKeys(String.valueOf(height));
    weightSlider().sendKeys(String.valueOf(weight));
    scrollToElementView(meinePartnerBtn());
    meinePartnerBtn().click();

    Assert.assertEquals(resultScreenLblTxt().getText(), getLocalizedTxtValue("result_screen_label"));
  }
}
