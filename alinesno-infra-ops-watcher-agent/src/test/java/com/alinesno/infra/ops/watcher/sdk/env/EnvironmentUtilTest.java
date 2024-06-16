package com.alinesno.infra.ops.watcher.sdk.env;

import com.alinesno.infra.ops.watcher.sdk.dto.Env;
import com.alinesno.infra.ops.watcher.sdk.dto.JvmInfo;
import com.google.gson.Gson;
import org.junit.Test;

public class EnvironmentUtilTest {

    @Test
    public void testGetEnvironmentDetails() {
        Env env =  EnvironmentUtil.getEnvironmentDetails() ;
        System.out.println("env = " + env);
    }

    @Test
    public void testGetJvmInfo() {
        JvmInfo env =  EnvironmentUtil.getJvmInfo() ;
        System.out.println("env = " + new Gson().toJson(env));
    }

}
