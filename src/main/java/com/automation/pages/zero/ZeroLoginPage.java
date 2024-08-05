package com.automation.pages.zero;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;

public class ZeroLoginPage {

    @Getter
    private SelenideElement loginField = $("#user_login");
    @Getter
    private SelenideElement passwordField = $("#user_password");
    @Getter
    private SelenideElement signInBtn = $("[name='submit']");

    @Step("Set login {login}")
    public ZeroLoginPage setLogin(String login) {
        loginField.setValue(login);
        return this;
    }

    @Step("Set password {password}")
    public ZeroLoginPage setPassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    @Step("Click sign in")
    public void clickSignIn() {
        signInBtn.click();
    }

}
