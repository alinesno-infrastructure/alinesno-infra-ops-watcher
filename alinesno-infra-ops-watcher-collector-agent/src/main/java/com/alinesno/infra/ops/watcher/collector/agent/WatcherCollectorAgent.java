package com.alinesno.infra.ops.watcher.collector.agent;

public class WatcherCollectorAgent {
    public static void main(String[] args) {
        String logFilePath = "/path/to/your/logfile.log";
        String httpEndpoint = "http://your.http.endpoint";

        LogCollector collector = new LogCollector(logFilePath, httpEndpoint);
        collector.startCollecting();

        // Keep the application running
        Runtime.getRuntime().addShutdownHook(new Thread(collector.scheduler::shutdownNow));
    }
}
