package com.automation.utils;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.LogType;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.url;

@Slf4j
public class AllureAttachmentsUtil {

    private AllureAttachmentsUtil() {

    }

    @Attachment(type = "image/png", value = "{screenName}")
    public static byte[] addScreenshot(String screenName) {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public static void addScreenshot() {
        addScreenshot("Screenshot");
    }


    public static void attachFile(File file, String fileName, String extension, String type) {
        try {
            InputStream is = new FileInputStream(file);
            Allure.addAttachment(fileName, type, is, extension);
            log.info("Attach file {}.{} ,type {}, size {}", fileName, extension, type, file.length());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void attachLogs(File file) {
        if (file.exists()) {
            try {
                InputStream is = new FileInputStream(file);
                Allure.addAttachment("Log", "text/plain", is, "log");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void attachBrowserLogs() {
        var sb = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy' 'HH:mm:ss:S");
        var logs = getWebDriver().manage().logs();
        for (var log : logs.get(LogType.BROWSER.toString())) {
            var date = new Date(log.getTimestamp());
            sb.append(String.format("[%s] %s : %s %n", log.getLevel(), simpleDateFormat.format(date), log.getMessage()));
        }
        Allure.addAttachment("Browser Logs", sb.toString());
    }

    public static void addLinkToReport() {
        Allure.addAttachment("Failed Page URL", url());
    }

    public static void addCurrentTime() {
        Allure.addAttachment("Time Stamp", LocalDateTime.now().toString());
    }

    public static void attachSelenoidVideo(String host, String sessionId) {
        URL videoUrl = null;
        try {
            videoUrl = new URL(host + "/video/" + sessionId + ".mp4");
            InputStream is = SelenoidUtil.getSelenoidVideo(videoUrl);
            Allure.addAttachment("Video", "video/mp4", is, "mp4");
            log.info("Attached selenoid video from {}", videoUrl);
            SelenoidUtil.deleteSelenoidVideo(videoUrl);
        } catch (Exception e) {
            log.error("Can't attach video from {}: {}", videoUrl != null ? videoUrl : "No url", e.getMessage());
        }
    }
}
