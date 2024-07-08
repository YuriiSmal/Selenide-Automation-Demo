package utils;

import com.automation.report.AllureAttachmentsUtil;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.TestResult;

public class AllureListener implements TestLifecycleListener {

    @Override
    public void beforeTestStop(TestResult result) {
        AllureAttachmentsUtil.addScreenshot(result.getName());
    }
}