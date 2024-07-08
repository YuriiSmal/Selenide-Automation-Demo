package common;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.WebDriverRunner;
import com.automation.common.Config;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import utils.DarkMode;

import java.lang.reflect.Method;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

@Slf4j
public class BaseUITest {

    protected Capabilities caps;

    @BeforeMethod
    public void setupBrowser(Method method) {

        boolean darkMode = false;
        var noVideoAnnotation = method.getAnnotation(DarkMode.class);
        if (noVideoAnnotation != null && noVideoAnnotation.enabled()) {
            darkMode = true;
            log.info("Skipping video recording for {}", method.getName());
        }

        initBrowser(darkMode);
    }


    public void initBrowser(boolean useDarkMode) {
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 35000;
        Configuration.pollingInterval = 5000;
        Configuration.pageLoadTimeout = 90000;
        Configuration.downloadsFolder = "build/downloads";
        Configuration.fileDownload = FileDownloadMode.FOLDER;

        if (!Config.USE_LOCAL_BROWSER) {
            System.out.println("All fine");
            return;
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

        Configuration.browserCapabilities = capabilities;

        open();
        WebDriverRunner.getWebDriver().manage().window().maximize();

        caps = ((RemoteWebDriver) WebDriverRunner.getAndCheckWebDriver()).getCapabilities();
    }
}
