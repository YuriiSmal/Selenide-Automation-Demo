package com.automation.pages.herokuapp;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Selenide.*;

@Slf4j
public class HerokuappMainPage {

    private SelenideElement basicAuthElement = $("[href='/basic_auth']");

    @Step("Navigate to herokuapp")
    public HerokuappMainPage navigateToMainPage() {
        return open("https://the-internet.herokuapp.com/", this.getClass());
    }

    @Step("Open basic auth page")
    public BasicAuthPage openBasicAuthPage() {
        basicAuthElement.click();
        return page(BasicAuthPage.class);
    }
}
