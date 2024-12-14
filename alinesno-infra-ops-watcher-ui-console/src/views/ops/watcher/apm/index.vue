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
                            <el-tree :data="systemServices" 
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
const monitorUrl = ref("http://grafana.infra.linesno.com/d/xfpJB9FGz/node-exporter-for-prometheus-dashboard-en-20201010?orgId=1&var-interval=10m&from=now-1h&to=now&timezone=browser&var-origin_prometheus=&var-job=prometheus&var-hostname=$__all&var-node=localhost:9100&var-device=$__all&var-maxmount=%2F&var-show_hostname=izm5e9pfu150pegnyjwluaz&var-total=2&var-Filters=&kiosk");

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

const systemServices = ref([
    {
        "id": 101,
        "label": "监控总览",
        "children": [
            {
                "id": 103,
                "label": "应用健康状态",
                "desc": "服务器性能和状态的实时监控"
            }
        ]
    },
    {
        "id": 1,
        "label": "基础技术服务",
        "children": [
            { "id": 101, "label": "权限配置服务", "desc": "alinesno-infra-base-authority", "link": "" },
            { "id": 102, "label": "代码生成器", "desc": "alinesno-infra-base-starter", "link": "" },
            { "id": 103, "label": "分布式配置服务", "desc": "alinesno-infra-base-config", "link": "" },
            { "id": 104, "label": "事务消息服务", "desc": "alinesno-infra-base-message", "link": "" },
            { "id": 105, "label": "存储管理服务", "desc": "alinesno-infra-base-storage", "link": "" },
            { "id": 106, "label": "单点登陆服务", "desc": "alinesno-infra-base-identity", "link": "" },
            { "id": 107, "label": "网关配置服务", "desc": "alinesno-infra-base-gateway", "link": "" },
            { "id": 108, "label": "文档搜索服务", "desc": "alinesno-infra-base-document", "link": "" },
            { "id": 109, "label": "敏感词过滤服务", "desc": "alinesno-infra-base-sensitive", "link": "" },
            { "id": 110, "label": "支付服务", "desc": "alinesno-infra-base-pay", "link": "" },
            { "id": 111, "label": "内容服务", "desc": "alinesno-infra-base-cms", "link": "" }
        ]
    },
    {
        "id": 2,
        "label": "大模型推理服务",
        "children": [
            { "id": 201, "label": "OCR视觉识别服务", "desc": "alinesno-infra-smart-ocr", "link": "" },
            { "id": 202, "label": "自然语言识别服务", "desc": "alinesno-infra-smart-nlp", "link": "" },
            { "id": 203, "label": "GPT推理服务", "desc": "alinesno-infra-smart-brain", "link": "" },
            { "id": 205, "label": "智能助手服务", "desc": "alinesno-infra-smart-assistant", "link": "" },
            { "id": 206, "label": "目标检测识别服务", "desc": "alinesno-infra-smart-detection", "link": "" }
        ]
    },
    {
        "id": 3,
        "label": "运维维护服务",
        "children": [
            { "id": 301, "label": "自动化任务服务", "desc": "alinesno-infra-ops-scheduler", "link": "" },
            { "id": 304, "label": "容器管理服务", "desc": "alinesno-infra-ops-container", "link": "" },
            { "id": 305, "label": "监控预警服务", "desc": "alinesno-infra-ops-watcher", "link": "" }
        ]
    },
    {
        "id": 4,
        "label": "数据业务服务",
        "children": [
            { "id": 402, "label": "实时推荐服务", "desc": "alinesno-infra-bus-recommend", "link": "" },
            { "id": 403, "label": "实时画像服务", "desc": "alinesno-infra-bus-profiling", "link": "" }
        ]
    },
    {
        "id": 5,
        "label": "运营服务",
        "children": [
            { "id": 501, "label": "基设平台管理服务", "desc": "alinesno-infra-plat-console", "link": "" },
            { "id": 502, "label": "安全感触服务", "desc": "alinesno-infra-plat-security", "link": "" }
        ]
    },
    {
        "id": 6,
        "label": "数据治理服务",
        "children": [
            { "id": 601, "label": "主数据服务", "desc": "alinesno-infra-data-mdm", "link": "" },
            { "id": 602, "label": "数据上报服务", "desc": "alinesno-infra-data-report", "link": "" },
            { "id": 603, "label": "数据集成服务", "desc": "alinesno-infra-data-pipeline", "link": "" },
            { "id": 604, "label": "数据开发服务", "desc": "alinesno-infra-data-dolphinscheduler", "link": "" },
            { "id": 605, "label": "实时计算服务", "desc": "alinesno-infra-data-stream", "link": "" },
            { "id": 606, "label": "数据资产服务", "desc": "alinesno-infra-data-assets", "link": "" }
        ]
    },
]);

</script>

<style scoped lang="scss">
</style>