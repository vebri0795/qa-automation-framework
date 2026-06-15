package com.testautomation.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Page Object for the login page (https://the-internet.herokuapp.com/login).
 *
 * Encapsulates selectors and actions for this page. Tests interact with
 * these methods instead of raw CSS selectors — if the page markup changes,
 * only this class needs updating, not every test.
 */
public class LoginPage {

    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement flashMessage = $("#flash");

    public LoginPage openPage() {
        open("/login");
        return this;
    }

    public void login(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        submitButton.click();
    }

    public SelenideElement flashMessage() {
        return flashMessage;
    }
}