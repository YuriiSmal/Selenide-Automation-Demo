package com.automation.pages.zero;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class ZeroLandingPage {
    @Getter
    private SelenideElement signInBtn = $("#signin_button");

    @Step("Click sign in button")
    public ZeroLoginPage clickSignInButton() {
        signInBtn.click();
        return page(ZeroLoginPage.class);
    }
}
