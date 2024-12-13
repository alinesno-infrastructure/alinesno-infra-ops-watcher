package com.alinesno.infra.ops.watcher.config;

import com.alinesno.infra.common.extend.datasource.enable.EnableInfraDataScope;
import com.alinesno.infra.common.facade.enable.EnableActable;
import com.alinesno.infra.common.web.adapter.sso.enable.EnableInfraSsoApi;
import com.dtflys.forest.springboot.annotation.ForestScan;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * 应用配置类，用于定义和配置应用程序的各个方面的设置。
 * 通过使用不同的注解，来启用和配置应用程序的功能模块。
 */
@Slf4j
@EnableActable
@EnableInfraDataScope
@EnableInfraSsoApi
@MapperScan("com.alinesno.infra.ops.watcher.mapper")
@ForestScan({
        "com.alinesno.infra.common.web.adapter.base.consumer" ,
        "com.alinesno.infra.data.scheduler.adapter"
})
@Configuration
public class AppConfiguration implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.debug("应用启动......") ;
    }
}
