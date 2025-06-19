package com.mozart.bett.qa.test.web;

import com.mozart.bett.qa.config.BaseTest;
import com.mozart.bett.qa.page.LoginPage;
import org.testng.annotations.Test;

import static com.mozart.bett.qa.constant.main.ResourceConstants.ADMIN_LOGIN_JSON;
import static com.mozart.bett.qa.constant.main.ResourceConstants.WRONG_PASSWORD_LOGIN_JSON;

public class UserLoginTest extends BaseTest {
  LoginPage loginPage = new LoginPage();

  @Test
  void shouldUserLoginWeb() {
    loginPage.userLogin(ADMIN_LOGIN_JSON);
  }

  @Test
  void shouldNotUserLoginWrongPassword() {
    loginPage.userLogin(WRONG_PASSWORD_LOGIN_JSON, false);
  }
}
