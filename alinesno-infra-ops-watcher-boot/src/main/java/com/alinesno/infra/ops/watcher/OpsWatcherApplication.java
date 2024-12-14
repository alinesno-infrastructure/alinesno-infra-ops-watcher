package com.alinesno.infra.ops.watcher;

import com.alinesno.infra.ops.watcher.init.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**al
 * 集成一个Java开发示例工具
 * @author luoxiaodong
 * @since 1.0.0
 */
@SpringBootApplication
public class OpsWatcherApplication {

	public static void main(String[] args) {
	    SpringApplication.run(OpsWatcherApplication.class, args);
	}

}