package com.alinesno.infra.ops.watcher.collector.agent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * LogCollector class
 */
public class LogCollector {

    private final String logFilePath;
    private final String httpEndpoint;
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private long lastOffset = 0; // Track the last read position in the file

    public LogCollector(String logFilePath, String httpEndpoint) {
        this.logFilePath = logFilePath;
        this.httpEndpoint = httpEndpoint;
    }

    // Method to start collecting logs
    public void startCollecting() {
        scheduler.scheduleAtFixedRate(this::collectAndSendLogs, 0, 1, TimeUnit.SECONDS);
    }

    private void collectAndSendLogs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            long currentOffset = 0;
            while ((line = reader.readLine()) != null) {
                currentOffset += line.getBytes().length + System.lineSeparator().getBytes().length;
                if (currentOffset > lastOffset) {
                    sendLogToHttp(line);
                    lastOffset = currentOffset;
                }
            }
        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
        }
    }

    private void sendLogToHttp(String logLine) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(httpEndpoint);
            post.setHeader("Content-Type", "application/json");
            post.setEntity(new StringEntity("{\"log\":\"" + logLine + "\"}"));
            HttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() != 200) {
                // Handle error response
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }
    }

}