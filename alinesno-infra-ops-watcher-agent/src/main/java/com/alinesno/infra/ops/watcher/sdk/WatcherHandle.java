package com.alinesno.infra.ops.watcher.sdk;

public class WatcherHandle implements Watcher {

    private static WatcherConfiguration configuration = null ;

    public static WatcherConfiguration config() {
        if(configuration == null){
            configuration = new WatcherConfiguration() ;
        }
        return configuration;
    }

    @Override
    public void low(String var1) {

    }

    @Override
    public void low(String var1, Object var2) {

    }

    @Override
    public void low(String var1, Object var2, Object var3) {

    }

    @Override
    public void low(String var1, Object... var2) {

    }

    @Override
    public void low(String var1, Throwable var2) {

    }

    @Override
    public void normal(String var1) {

    }

    @Override
    public void normal(String var1, Object var2) {

    }

    @Override
    public void normal(String var1, Object var2, Object var3) {

    }

    @Override
    public void normal(String var1, Object... var2) {

    }

    @Override
    public void normal(String var1, Throwable var2) {

    }

    @Override
    public void important(String var1) {

    }

    @Override
    public void important(String var1, Object var2) {

    }

    @Override
    public void important(String var1, Object var2, Object var3) {

    }

    @Override
    public void important(String var1, Object... var2) {

    }

    @Override
    public void important(String var1, Throwable var2) {

    }

    @Override
    public void emergency(String var1) {

    }

    @Override
    public void emergency(String var1, Object var2) {

    }

    @Override
    public void emergency(String var1, Object... var2) {

    }

    @Override
    public void emergency(String var1, Object var2, Object var3) {

    }

    @Override
    public void emergency(String var1, Throwable var2) {

    }

    @Override
    public void critical(String var1) {

    }

    @Override
    public void critical(String var1, Object var2) {

    }

    @Override
    public void critical(String var1, Object var2, Object var3) {

    }

    @Override
    public void critical(String var1, Object... var2) {

    }

    @Override
    public void critical(String var1, Throwable var2) {

    }
}
