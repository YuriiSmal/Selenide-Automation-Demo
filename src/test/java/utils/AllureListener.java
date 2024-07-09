package utils;

import com.automation.utils.AllureAttachmentsUtil;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;

public class AllureListener implements TestLifecycleListener {

    @Override
    public void beforeTestStop(TestResult result) {
        var status = result.getStatus();

        if (WebDriverRunner.hasWebDriverStarted()) {
            AllureAttachmentsUtil.addScreenshot(result.getName());
            if (status == Status.FAILED || status == Status.BROKEN || status == Status.SKIPPED) {
                AllureAttachmentsUtil.addLinkToReport();
                AllureAttachmentsUtil.addCurrentTime();
            }
        }
    }
}