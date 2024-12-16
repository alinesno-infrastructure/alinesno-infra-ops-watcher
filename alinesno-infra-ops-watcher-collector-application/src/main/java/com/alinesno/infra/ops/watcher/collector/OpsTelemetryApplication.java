package com.alinesno.infra.ops.watcher.collector;

import com.alinesno.infra.common.web.adapter.config.CORSProperites;
import com.alinesno.infra.common.web.adapter.config.CorsFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * 描述：该类表示一个OTLP OpenTelemetry服务器，用于接收遥测数据。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@Slf4j
@SpringBootApplication
public class OpsTelemetryApplication extends TelemetryServer implements CommandLineRunner  {

    /**
     * 应用程序的入口点。
     *
     * @param args 命令行参数。
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        SpringApplication.run(OpsTelemetryApplication.class, args);
        TelemetryServer otlpServer = new TelemetryServer();
        otlpServer.start();
        otlpServer.blockUntilShutdown();
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("应用收集服务启动 ...");
    }

    @Bean
    public CORSProperites getCORSProperites(){
        return new CORSProperites() ;
    }

    @Bean
    public CorsFilter getCorsFilter(){
        return new CorsFilter() ;
    }
}
