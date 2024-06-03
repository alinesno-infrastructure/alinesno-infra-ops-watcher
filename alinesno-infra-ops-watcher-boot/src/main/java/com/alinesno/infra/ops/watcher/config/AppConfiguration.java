package com.alinesno.infra.ops.watcher.config;

import com.alinesno.infra.common.web.adapter.sso.enable.EnableInfraSsoApi;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 应用配置类，用于定义和配置应用程序的各个方面的设置。
 * 通过使用不同的注解，来启用和配置应用程序的功能模块。
 */
@EnableActable
@EnableInfraSsoApi
@MapperScan("com.alinesno.infra.ops.watcher.mapper")
@Configuration
public class AppConfiguration {

}
