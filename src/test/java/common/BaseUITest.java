package common;

import com.automation.utils.AllureAttachmentsUtil;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import utils.DarkMode;
import utils.RecordVideo;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Map;

import static com.automation.common.Config.*;
import static com.automation.utils.AllureReportUtil.createUiEnvProperties;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@Slf4j
public class BaseUITest extends MainBaseTest {

    private Capabilities caps;

    @AfterMethod
    public void attachTestArtifacts() {
        try {
            AllureAttachmentsUtil.attachBrowserLogs();
        } catch (Exception e) {
            log.info("Browser Logs was not attached due the next exception: ");
            log.error(e.getMessage());
            e.printStackTrace();
        }

        closeDriverAttachVideo();
        var logName = String.format("logs/%s%s", org.apache.logging.log4j.ThreadContext.get("testName"), ".log");
        attachLogs(logName);
    }

    @BeforeMethod
    public void setupBrowser(Method method) {

        boolean darkMode = false;
        boolean recordVideo = true;
        var darkThemeAnnotation = method.getAnnotation(DarkMode.class);
        if (darkThemeAnnotation != null) {
            darkMode = darkThemeAnnotation.enabled();
            log.info("Set dark mode on {} for test {}",
                    darkThemeAnnotation.enabled(), method.getName());
        }

        var recordVideoAnnotation = method.getAnnotation(RecordVideo.class);
        if (recordVideoAnnotation != null) {
            recordVideo = recordVideoAnnotation.enabled();
            log.info("Skipping video recording for {}", method.getName());
        }

        initBrowser(method.getName(), recordVideo, darkMode);
    }


    public void initBrowser(String testName, boolean enableVideo, boolean useDarkMode) {
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 35000;
        Configuration.pollingInterval = 5000;
        Configuration.pageLoadTimeout = 90000;
        Configuration.downloadsFolder = "build/downloads";
        Configuration.fileDownload = FileDownloadMode.FOLDER;

        if (!USE_LOCAL_BROWSER) {
            log.info("Setting Up Remote Driver");
            Configuration.remote = SELENOID_HOST_URL;
            Configuration.remoteReadTimeout = Duration.ofSeconds(30).toMillis();
            Configuration.remoteConnectionTimeout = Duration.ofSeconds(30).toMillis();
        }

        var chromeOpt = new ChromeOptions();
        if (useDarkMode)
            chromeOpt.addArguments("--force-dark-mode");
        chromeOpt.setExperimentalOption("prefs",
                Map.of("credentials_enable_service", false,
                        "profile.password_manager_enabled", false));
        chromeOpt.addArguments("--safebrowsing-disable-download-protection");
        chromeOpt.addArguments("--disable-features=InsecureDownloadWarnings");
        chromeOpt.addArguments("safebrowsing-disable-extension-blacklist");
        chromeOpt.addArguments("--allow-running-insecure-content");

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOpt);


        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOpt);

        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", enableVideo,
                "name", testName,
                "browserName", "chrome",
                "sessionTimeout", "10m"
        ));

        Configuration.browserCapabilities = capabilities;

        open();
        WebDriverRunner.getWebDriver().manage().window().maximize();

        caps = ((RemoteWebDriver) WebDriverRunner.getAndCheckWebDriver()).getCapabilities();
    }

    public String getSelenoidSessionId() {
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
    }

    public void closeDriverAttachVideo() {
        String sessionId = "";
        String selenoidHost = "";
        if (Configuration.remote != null) {
            sessionId = getSelenoidSessionId();
            selenoidHost = WD_VIDEO;
            log.info("Get session id: {}", sessionId);
        }
        closeWebDriver();
        if (Configuration.remote != null) {
            AllureAttachmentsUtil.attachSelenoidVideo(selenoidHost, sessionId);
        }
    }

    private void attachLogs(String logName) {
        AllureAttachmentsUtil.attachLogs(new File(logName));
    }

    @AfterSuite
    public void setupAllureEnvVariables() {
        createUiEnvProperties(caps);
    }
}