//package com.alinesno.infra.common.web.adapter.login.controller;
//
//import com.alinesno.infra.common.facade.response.AjaxResult;
//import com.alinesno.infra.common.web.adapter.dto.LoginBodyDto;
//import com.alinesno.infra.common.web.adapter.dto.menus.Menu;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.*;
//
//@RestController
//public class CommonLoginController {
//
//    /**
//     * 令牌
//     */
//    public static final String TOKEN = "token";
//
//    /**
//     * 登录方法
//     *
//     * @param loginBody 登录信息
//     * @return 结果
//     */
//    @PostMapping("/login")
//    public AjaxResult login(@RequestBody LoginBodyDto loginBody)
//    {
//        AjaxResult ajax = AjaxResult.success();
//        // 生成令牌
//        String token = UUID.randomUUID().toString() ;
//        ajax.put(TOKEN, token);
//        return ajax;
//    }
//
//    /**
//     * 获取用户信息
//     *
//     * @return 用户信息
//     */
//    @GetMapping("getInfo")
//    public AjaxResult getInfo() {
//
//        Map<String, Object> data = new HashMap<>();
//        // 将数据填充到data中...
//        data.put("permissions", new String[]{"*:*:*"});
//
//        Map<String, Object> user = new HashMap<>();
//        user.put("createBy", "admin");
//        user.put("createTime", "2023-04-23 16:11:38");
//        user.put("updateBy", null);
//        user.put("updateTime", null);
//        user.put("remark", "管理员");
//        user.put("userId", 1);
//        user.put("deptId", 103);
//        user.put("userName", "admin");
//        user.put("nickName", "AIP技术团队");
//        user.put("email", "aip-team@163.com");
//        user.put("phonenumber", "15888888888");
//        user.put("sex", "1");
//        user.put("avatar", "");
//        user.put("password", "");
//        user.put("status", "0");
//        user.put("delFlag", "0");
//        user.put("loginIp", "");
//        user.put("loginDate", "2023-09-21T16:54:12.000+08:00");
//
//        Map<String, Object> dept = new HashMap<>();
//        dept.put("createBy", null);
//        dept.put("createTime", null);
//        dept.put("updateBy", null);
//        dept.put("updateTime", null);
//        dept.put("remark", null);
//        dept.put("deptId", 103);
//        dept.put("parentId", 101);
//        dept.put("ancestors", "0,100,101");
//        dept.put("deptName", "研发部门");
//        dept.put("orderNum", 1);
//        dept.put("leader", "AIP技术团队");
//        dept.put("phone", null);
//        dept.put("email", null);
//        dept.put("status", "0");
//        dept.put("delFlag", null);
//        dept.put("parentName", null);
//        dept.put("children", new Object[]{});
//
//        user.put("dept", dept);
//
//        Map<String, Object> role = new HashMap<>();
//        role.put("createBy", null);
//        role.put("createTime", null);
//        role.put("updateBy", null);
//        role.put("updateTime", null);
//        role.put("remark", null);
//        role.put("roleId", 1);
//        role.put("roleName", "超级管理员");
//        role.put("roleKey", "admin");
//        role.put("roleSort", 1);
//        role.put("dataScope", "1");
//        role.put("menuCheckStrictly", false);
//        role.put("deptCheckStrictly", false);
//        role.put("status", "0");
//        role.put("delFlag", null);
//        role.put("flag", false);
//        role.put("menuIds", null);
//        role.put("deptIds", null);
//        role.put("permissions", null);
//        role.put("admin", true);
//
//        user.put("roles", new Object[]{role});
//
//        AjaxResult ajax = AjaxResult.success();
//        ajax.put("user", user);
//        ajax.put("roles", user.get("roles"));
//        ajax.put("permissions", data.get("permissions"));
//
//        return ajax;
//    }
//
//    /**
//     * 获取路由信息
//     *
//     * @return 路由信息
//     */
//    @GetMapping("getRouters")
//    public AjaxResult getRouters()
//    {
//
//        Menu dashboardMenu = new Menu("Dashboard", "/dashboard", false, "noRedirect", "Layout", true, new Menu.Meta("仪盘表", "dashboard", false, null), List.of(
//                new Menu("Dashboard", "index", false, false , "dashboard", new Menu.Meta("概览", "dashboard", false, null))
//        ));
//
//        Menu projectMenu = new Menu("Project", "/project", false, "noRedirect", "Layout", true, new Menu.Meta("项目管理", "post", false, null),
//                List.of(
//                        new Menu("Project", "ops/watcher/project/index", false,false,  "ops/watcher/project/index", new Menu.Meta("项目管理", "druid", false, null))
//                ));
//
//        Menu systemMenu = new Menu("Alert", "/alert", false, "noRedirect", "Layout", true, new Menu.Meta("告警情况", "post", false, null),
//                List.of(
//                        new Menu("AllMessage", "ops/watcher/message/index", false,false,  "ops/watcher/message/index", new Menu.Meta("所有告警", "form", false, null)),
//                        new Menu("ErrorMessage", "ops/watcher/error/index", false,false,  "ops/watcher/error/index", new Menu.Meta("未处理告警", "tree", false, null)),
//                        new Menu("PersonMessage", "ops/watcher/person/index", false,false,  "ops/watcher/person/index", new Menu.Meta("我的告警", "user", false, null))
//                ));
//
//        List<Menu> menus = getMenus(projectMenu, dashboardMenu, systemMenu);
//
//        return AjaxResult.success(menus) ;
//    }
//
//    @NotNull
//    private static List<Menu> getMenus(Menu projectMenu, Menu dashboardMenu, Menu systemMenu) {
//        Menu serviceMenu = new Menu("Notice", "/notice", false, "noRedirect", "Layout", true, new Menu.Meta("集成通知", "log", false, null),
//                        List.of(
//                                new Menu("ProviderChannel", "ops/watcher/channel/index", false,false,  "ops/watcher/channel/index", new Menu.Meta("接入渠道", "guide", false, null)),
//                                new Menu("AlertChannel", "ops/watcher/alert/index", false,false, "ops/watcher/alert/index", new Menu.Meta("告警集成", "form", false, null))
//                        ));
//
//        Menu monitorMenu = new Menu("Config", "/config", false, "noRedirect", "Layout", true, new Menu.Meta("告警配置", "monitor", false, null),
//                List.of(
//                        new Menu("Template", "ops/watcher/template/index", false,false, "ops/watcher/template/index", new Menu.Meta("通知模板", "form", false, null)),
//                        new Menu("Rule", "ops/watcher/rule/index", false,false, "ops/watcher/rule/index", new Menu.Meta("屏蔽规则", "online", false, null)),
//                        new Menu("AllGroup", "ops/watcher/group/index", false,false, "ops/watcher/group/index", new Menu.Meta("告警分组", "guide", false, null))
//                ));
//
//
//        return List.of(dashboardMenu, projectMenu, systemMenu, serviceMenu , monitorMenu);
//    }
//}