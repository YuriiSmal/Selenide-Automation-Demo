package com.automation.pages.letcode;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Selenide.$;
@Slf4j
public class LetCodeAlertsPage {


    private SelenideElement simpleAlertElement = $("#accept");
    private SelenideElement confirmAlertElement = $("#confirm");
    private SelenideElement promptAlertElement = $("#prompt");
    private SelenideElement modernAlertElement = $("#modern");

    @Step("Open simple Alert")
    public void openSimpleAlert() {
        simpleAlertElement.click();
    }

    @Step("Open confirm Alert")
    public void openConfirmAlert() {
        confirmAlertElement.click();
    }

    @Step("Open prompt Alert")
    public void openPromptAlert() {
        promptAlertElement.click();
    }

    @Step("Open modern Alert")
    public void openModernAlert() {
        modernAlertElement.click();
    }

    @Step("Close Modern Alert")
    public void closeModernAlert() {
        $("[aria-label='close']").click(ClickOptions.usingJavaScript());
    }
}
