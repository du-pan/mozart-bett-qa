package com.mozart.bett.qa.page;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.mozart.bett.qa.constant.main.WebTestConstants.EMAIL;
import static com.mozart.bett.qa.constant.main.WebTestConstants.PASSWORD;
import static com.mozart.bett.qa.util.ObjectConverterUtil.getEntityJsonObject;

public class LoginPage extends BasePage {
    private WebElement getEmailInputField() {
        return driver.findElement(By.cssSelector(""));
    }

    private WebElement getPasswordInputField() {
        return driver.findElement(By.cssSelector(""));
    }

    private WebElement getLoginBtn() {
        return driver.findElement(By.cssSelector(""));
    }

    public void userLogin(final String jsonPath, final boolean successLogin) {
        final JSONObject jsonObject = getEntityJsonObject(jsonPath);
        getEmailInputField().sendKeys(jsonObject.getString(EMAIL));
        getPasswordInputField().sendKeys(jsonObject.getString(PASSWORD));
        getLoginBtn().click();
    }

    public void userLogin(final String jsonPath) {
        userLogin(jsonPath, true);
    }
}
