package com.alinesno.infra.ops.watcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 集成一个Java开发示例工具
 * @author LuoAnDong
 * @since 2023年8月3日 上午6:23:43
 */
@SpringBootApplication
public class OpsWatcherApplication {

	/**
	 * 程序的入口点。
	 * 使用SpringApplication.run()方法启动Spring Boot应用程序。
	 * 参数:
	 * args - 命令行参数，用于配置应用程序的行为。
	 */
	public static void main(String[] args) {
	    SpringApplication.run(OpsWatcherApplication.class, args);
	}

}