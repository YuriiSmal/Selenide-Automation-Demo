package com.automation.pages.herokuapp;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

@Slf4j
public class BasicAuthPage {

    @Step("Accepp basic auth with url")
    public void acceptBasicAuth(String name, String password) {
        var url = url();
        var split = url.split("//");

        var builder = new StringBuilder();
        builder
                .append(split[0])
                .append("//")
                .append(name)
                .append(":")
                .append(password)
                .append("@")
                .append(split[1]);
        open(builder.toString());
    }
}
