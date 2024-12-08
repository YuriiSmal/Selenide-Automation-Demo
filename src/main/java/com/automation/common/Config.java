package com.automation.common;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

@Slf4j
public class Config {
    protected static Properties localDevProps = new Properties();
    public static final boolean USE_LOCAL_BROWSER;
    public static final boolean DISABLE_RETRY_ANALYZER;
    public static final String SELENOID_HOST_URL;
    public static final String WD_VIDEO;

    private Config() {
    }

    static {
        localDevPropsPath = "dev.properties";
        loadProperties(localDevProps, localDevPropsPath);
        String useLocalBrowser = Optional
                .ofNullable((localDevProps.getProperty("use_local_browser")))
                .orElse("false");

        USE_LOCAL_BROWSER = System.getProperty("LocalBrowser") == null
                ? Boolean.parseBoolean(useLocalBrowser)
                : Boolean.parseBoolean(System.getProperty("LocalBrowser"));

        SELENOID_HOST_URL = "http://localhost:4444/wd/hub";
        WD_VIDEO = SELENOID_HOST_URL.replace("/wd/hub", "");

        DISABLE_RETRY_ANALYZER = Boolean
                .parseBoolean(Optional
                        .ofNullable((localDevProps.getProperty("disable_retry_analyzer")))
                        .orElse("false"));
    }

    public static void loadProperties(Properties properties, String propertiesFilePath) {
        log.info("Load the test properties from properties file: {}", propertiesFilePath);

        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(propertiesFilePath)) {
            properties.load(inputStream);
        } catch (Exception e) {
            log.error("Cannot read property file " + propertiesFilePath);
            log.error(e.getMessage());
        }
    }
}
