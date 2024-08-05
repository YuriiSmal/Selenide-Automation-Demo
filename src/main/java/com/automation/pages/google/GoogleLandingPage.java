package com.automation.pages.google;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

@Slf4j
public class GoogleLandingPage {

    public SelenideElement searchFieldElement = $("textarea[role='combobox']");

    @Step("Search for: {text}")
    public GoogleLandingPage searchFor(String text) {
        log.info("Search for %s".formatted(text));
        searchFieldElement.setValue(text).pressEnter();
        sleep(Duration.ofSeconds(3).toMillis());
        return this;
    }

    @Step("Open google.com")
    public GoogleLandingPage navigateToLandingPage() {
        log.info("Open google.com");
        open("https://google.com");
        return this;
    }
}
