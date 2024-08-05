package com.automation.pages.letcode;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Selenide.*;

@Slf4j
public class LetCodeTestPage {

    @Getter
    private SelenideElement alertsLinkElement = $("[href='/alert']");

    @Step("Open letcode.in")
    public LetCodeTestPage navigateToTestPage() {
        log.info("Open google.com");
        open("https://letcode.in/test");
        return this;
    }

    @Step("Open Alerts page")
    public LetCodeAlertsPage goToAlertsPage() {
        alertsLinkElement.click();
        return page(LetCodeAlertsPage.class);
    }
}
