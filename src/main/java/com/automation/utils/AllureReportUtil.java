package com.automation.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

@Slf4j
public class AllureReportUtil {

    private final static char delimiterChar = ' ';
    private final static String pathToAllureResults = "build/allure-results/environment.properties";
    private final static String categoriesFilePath = "src/test/resources/categories.json";
    private final static String allureCategoriesPath = "build/allure-results/categories.json";

    @SneakyThrows
    public static void addCategoriesToReport() {
        Path to = Path.of(allureCategoriesPath);
        if (!Files.exists(to)) {
            Files.copy(Paths.get(categoriesFilePath), to, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static void createFodUiEnv(Capabilities caps) {
        log.info("Setup {} file for UI allure environment, browser: {} ",
                pathToAllureResults, caps.getBrowserName());

        Properties props = new Properties();
        props.setProperty("Browser", caps.getBrowserName().toUpperCase());
        props.setProperty("Browser Version", caps.getBrowserVersion());

        saveToFile(props);
    }

    private static void saveToFile(Properties props) {
        SortedProperties properties = new SortedProperties();

        if (Files.exists(Paths.get(pathToAllureResults))) {
            try (FileInputStream existingProps = new FileInputStream(pathToAllureResults)) {
                properties.load(existingProps);
                properties.putAll(props);
            } catch (Exception e) {
                log.error("IO problem when reading allure properties file", e);
            }

        } else {
            properties.putAll(props);
        }

        try (FileOutputStream fos = new FileOutputStream(pathToAllureResults)) {
            properties.store(fos, "Environment properties");
        } catch (IOException e) {
            log.error("IO problem when writing allure properties file", e);
        }
    }
}
