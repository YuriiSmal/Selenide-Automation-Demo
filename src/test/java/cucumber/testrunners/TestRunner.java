package cucumber.testrunners;

import common.BaseUITest;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"cucumber/stepdefinitions"},
        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    private BaseUITest baseUITest = new BaseUITest();

    @BeforeClass
    public void beforeClass() {
        baseUITest.initBrowser("Setup Browser", true, false);
    }


    @AfterClass
    public void afterClass() {
        baseUITest.attachTestArtifacts();
    }

    @AfterSuite
    public void pro() {
        baseUITest.setupAllureEnvVariables();
    }

    @BeforeClass
    public void setRetry(ITestContext context) {
        baseUITest.setRetry(context);
    }

    @AfterSuite
    public void addAllureReportCategories() {
        baseUITest.addAllureReportCategories();
    }
}