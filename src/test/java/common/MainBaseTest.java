package common;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import utils.RetryAnalyzer;

import static com.automation.utils.AllureReportUtil.addCategoriesToReport;

public class MainBaseTest {
    @BeforeClass
    public void setRetry(ITestContext context) {
        for (ITestNGMethod method : context.getAllTestMethods()) {
            method.setRetryAnalyzerClass(RetryAnalyzer.class);
        }
    }

    @AfterSuite
    public void addAllureReportCategories() {
        addCategoriesToReport();
    }
}
