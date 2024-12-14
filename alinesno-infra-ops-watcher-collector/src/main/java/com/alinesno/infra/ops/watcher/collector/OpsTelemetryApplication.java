package com.alinesno.infra.ops.watcher.collector;

import java.io.IOException;

/**
 * 描述：该类表示一个OTLP OpenTelemetry服务器，用于接收遥测数据。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
public class OpsTelemetryApplication extends TelemetryServer {

    /**
     * 应用程序的入口点。
     *
     * @param args 命令行参数。
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        TelemetryServer otlpServer = new TelemetryServer();
        otlpServer.start();
        otlpServer.blockUntilShutdown();
    }

}
