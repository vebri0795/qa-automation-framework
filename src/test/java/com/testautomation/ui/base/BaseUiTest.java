package com.testautomation.ui.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;

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

        // CI runners are containerized Linux environments without a real
        // display or normal OS sandboxing. --no-sandbox and --headless=new
        // are required for Chrome to start there
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--headless=new");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        Configuration.browserCapabilities = options;

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(true));
    }
}
