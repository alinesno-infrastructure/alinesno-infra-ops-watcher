package com.alinesno.infra.ops.watcher.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应用程序的主类，负责启动Spring Boot应用。
 * 排除了DataSourceAutoConfiguration，意味着这个应用可能不依赖于数据库。
 */
@Slf4j
@RestController
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application implements CommandLineRunner {

    /**
     * 首页路由方法，返回简单的欢迎信息。
     *
     * @return 欢迎信息字符串
     */
    @GetMapping("/")
    String index(){
        log.debug("hello world") ;
        return "hello application" ;
    }

    /**
     * 应用程序的入口点。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * CommandLineRunner接口的实现方法。
     * 在应用启动后，此方法会执行。用于配置和演示Watcher的使用。
     *
     * @param args 命令行参数
     * @throws Exception 如果运行过程中出现异常
     */
    @Override
    public void run(String... args) throws Exception {
//        // 配置Watcher的项目代码和类别
//        Watcher.config().setProjectCode("86Rf07");
//        Watcher.config().setCategory("kACkA8xvHerxKf");
//
//        // 使用Watcher记录不同级别的消息，演示各种级别的使用场景
//        // 重要：记录账户余额不足的交易异常
//        Watcher.important("交易异常：账户余额不足，无法完成交易。账户号：123456，交易金额：$500，当前余额：$100");
//        // 严重：记录交易金额超出限额的异常
//        Watcher.critical("交易异常：交易金额超出限额，风险较高。账户号：789012，交易金额：$10000，限额：$5000");
//        // 低级：记录交易延迟，可能需要手动处理
//        Watcher.low("交易异常：交易延迟，可能需要手动处理。账户号：345678，交易金额：$200");
//        // 普通：记录交易成功的消息
//        Watcher.normal("交易异常：交易成功，金额：$500，账户号：234567");
//        // 紧急：记录因系统故障导致的交易失败
//        Watcher.emergency("交易异常：系统故障导致交易失败。请尽快处理。账户号：456789，交易金额：$1000");
    }
}
