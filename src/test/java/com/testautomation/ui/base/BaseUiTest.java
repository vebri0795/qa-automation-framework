package com.testautomation.ui.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;

/**
 * Shared setup for UI tests: configures Selenide and registers the
 * AllureSelenide listener, which attaches a screenshot + page source to
 * Allure for each step — most useful when a test fails.
 */
public abstract class BaseUiTest {

    @BeforeAll
    static void setupBrowser() {
        Configuration.baseUrl = "https://the-internet.herokuapp.com";
        Configuration.browser = "chrome";

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(true));
    }
}