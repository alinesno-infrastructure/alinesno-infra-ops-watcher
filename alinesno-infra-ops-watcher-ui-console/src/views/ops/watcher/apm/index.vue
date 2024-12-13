<template>

    <div class="app-container">
        <el-row :gutter="20">
                    <el-col :span="4">
                        <div class="head-container">
                            <!-- 查询条件 -->
                            <el-form :inline="true" :model="searchForm" class="demo-form-inline">
                                <el-row>
                                    <el-col :span="18">
                                        <el-form-item label="监控项目">
                                            <el-input v-model="searchForm.departmentName"
                                                placeholder="请输入部门名称"></el-input>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="6">
                                        <el-form-item>
                                            <el-button type="primary" text bg @click="onSubmit">查询</el-button>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </el-form>
                        </div>
                        <div class="head-container">
                            <!-- 使用element-plus tree 展示, 并自定义节点内容 -->
                            <el-tree :data="monitorItem" 
                                :props="defaultProps"  
                                @node-click="handleNodeClick">
                                <template #default="{ node, data }">
                                    <span class="custom-tree-node">
                                        <span>{{ node.label }}</span>
                                        <span class="desc">{{ data.desc || '无描述' }}</span>
                                    </span>
                                </template>
                            </el-tree>
                        </div>
                    </el-col>
                <!--用户数据-->
                    <el-col :span="20">
                        <iFrame :src="monitorUrl" />
                    </el-col>
        </el-row>
    </div>

</template>

<script setup>

import iFrame from "@/components/iFrame/index";
import { Splitpanes, Pane } from "splitpanes";
import "splitpanes/dist/splitpanes.css";

// 监控地址
const monitorUrl = ref("http://47.121.178.37:3000/d/5dB6Qz-ik/node-exporter-stats?orgId=1&timezone=browser&refresh=1m&theme=dark&kiosk");

// 搜索表单数据
const searchForm = ref({
    departmentName: ''
});

// 查询函数
const onSubmit = () => {
    console.log('submit!', searchForm.value);
};

// 树形控件默认属性
const defaultProps = {
    children: 'children',
    label: 'label'
};

// 监控列表
const monitorItem = ref([
    {
        "id": 101,
        "label": "监控总览",
        "children": [
            {
                "id": 103,
                "label": "服务器监控",
                "desc": "服务器性能和状态的实时监控"
            },
            {
                "id": 104,
                "label": "网络监控",
                "desc": "网络流量和连接性的实时监控"
            }
        ]
    },
    {
        "id": 103,
        "label": "中间件监控",
        "children": [
            {
                "id": 209,
                "label": "注册中心监控"
            },
            {
                "id": 211,
                "label": "日志监控"
            },
            {
                "id": 210,
                "label": "反向代理"
            },
            {
                "id": 210,
                "label": "分布式配置中心"
            },
            {
                "id": 208,
                "label": "缓存监控"
            },
            {
                "id": 209,
                "label": "消息中间件"
            }
        ]
    }
]);

</script>

<style scoped lang="scss">
</style>