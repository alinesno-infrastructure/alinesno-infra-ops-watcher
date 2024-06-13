package com.alinesno.infra.ops.watcher.sdk;

/**
 * Initializer 类用于管理 WatcherConfiguration 的单例实例。
 * 它提供了一个方法来访问配置实例，确保配置只被实例化一次。
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
public class Initializer {

    // 保存 WatcherConfiguration 的单例实例
    private static WatcherConfiguration configuration = null ;

    /**
     * 提供访问 WatcherConfiguration 单例实例的方法。
     * 如果该实例尚未被创建，则新建一个实例；否则，返回已存在的实例。
     *
     * @return WatcherConfiguration 的单例实例
     */
    public static WatcherConfiguration config() {
        // 检查配置实例是否已经创建，如果没有，则创建新实例
        if(configuration == null){
            configuration = new WatcherConfiguration() ;
        }
        // 返回配置实例
        return configuration;
    }

}
