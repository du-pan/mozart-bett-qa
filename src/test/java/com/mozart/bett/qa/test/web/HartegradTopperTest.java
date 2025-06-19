package com.mozart.bett.qa.test.web;

import com.mozart.bett.qa.config.BaseTest;
import com.mozart.bett.qa.constant.enums.NavBarOption;
import com.mozart.bett.qa.page.MatressHardnessPage;
import com.mozart.bett.qa.page.NavigationBarPage;
import com.mozart.bett.qa.page.NavigationPage;
import org.testng.annotations.Test;

import static com.mozart.bett.qa.constant.main.WebTestConstants.BOX_SPRING_BETT_SERIES_URL;

public class HartegradTopperTest extends BaseTest {
  private final NavigationPage navigationPage = new NavigationPage();
  private final NavigationBarPage navigationBarPage = new NavigationBarPage();
  private final MatressHardnessPage matressHardnessPage = new MatressHardnessPage();

  @Test
  void shouldOpenHartegradTopperPage() {
    navigationPage.getBrowserUrl(BOX_SPRING_BETT_SERIES_URL);
    navigationPage.selectPostalCodeNumber(2);
    navigationPage.acceptAllCookies();
    navigationBarPage.selectNavigationOption(NavBarOption.HARTGEGARD_AND_TOPPER);

    matressHardnessPage.getDeepSleepResults(190, 95);
  }
}
