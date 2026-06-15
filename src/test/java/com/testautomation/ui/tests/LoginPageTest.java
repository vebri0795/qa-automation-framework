package com.testautomation.ui.tests;

import com.testautomation.ui.base.BaseUiTest;
import com.testautomation.ui.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

/**
 * extends BaseUiTest: inherits browser config + the AllureSelenide listener.
 */
class LoginPageTest extends BaseUiTest {

    private LoginPage loginPage;

    @BeforeEach
    void openLoginPage() {
        loginPage = new LoginPage().openPage();
    }

    @Test
    void validCredentials_logsInSuccessfully() {
        loginPage.login("tomsmith", "SuperSecretPassword!");

        loginPage.flashMessage().shouldBe(visible);
        loginPage.flashMessage().shouldHave(text("You logged into a secure area"));
    }

    @Test
    void invalidCredentials_showsErrorMessage() {
        loginPage.login("invalid_user", "invalid_password");

        loginPage.flashMessage().shouldBe(visible);
        loginPage.flashMessage().shouldHave(text("Your username is invalid"));
    }
}