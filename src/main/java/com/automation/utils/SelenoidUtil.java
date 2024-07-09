package com.automation.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalTime;

@Slf4j
public class SelenoidUtil {

    private SelenoidUtil() {
    }

    public static InputStream getSelenoidVideo(URL url) {
        int lastSize = 0;
        int exit = 2;
        String errorMessage = "";

        var endTime = LocalTime.now().plusMinutes(1);
        do {
            try {
                int size = Integer.parseInt(url.openConnection().getHeaderField("Content-Length"));
                if (size > lastSize) {
                    lastSize = size;
                    Thread.sleep(1500);
                } else if (size == lastSize) {
                    exit--;
                    Thread.sleep(1000);
                }
                if (exit < 0) {
                    return url.openStream();
                }
            } catch (Exception e) {
                errorMessage = e.getMessage();
            }
        } while (LocalTime.now().isBefore(endTime));

        log.error("Cannot save the test video: {}", errorMessage);
        return null;
    }

    public static void deleteSelenoidVideo(URL url) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete(url.toString());
            httpDelete.addHeader(new BasicHeader("Content-Type", "application/json; charset=UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpDelete);
            response.close();
        } catch (IOException e) {
            log.error("Cannot remove the test video: {}", e.getMessage());
        }
    }
}
