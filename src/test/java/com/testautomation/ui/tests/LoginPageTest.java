package com.testautomation.ui.tests;

import com.testautomation.ui.base.BaseUiTest;
import com.testautomation.ui.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

/**
 * extends BaseUiTest: inherits browser config + the AllureSelenide listener.
 */
@Epic("User Data Platform")
@Feature("Login Page")
class LoginPageTest extends BaseUiTest {

    private LoginPage loginPage;

    @BeforeEach
    void openLoginPage() {
        loginPage = new LoginPage().openPage();
    }

    @Test
    @Story("Log in with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that a valid username/password combination logs the user in successfully.")
    void validCredentials_logsInSuccessfully() {
        loginPage.login("tomsmith", "SuperSecretPassword!");

        loginPage.flashMessage().shouldBe(visible);
        loginPage.flashMessage().shouldHave(text("You logged into a secure area"));
    }

    @Test
    @Story("Log in with invalid credentials")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that an invalid username/password combination shows an error message.")
    void invalidCredentials_showsErrorMessage() {
        loginPage.login("invalid_user", "invalid_password");

        loginPage.flashMessage().shouldBe(visible);
        loginPage.flashMessage().shouldHave(text("Your username is invalid"));
    }
}
