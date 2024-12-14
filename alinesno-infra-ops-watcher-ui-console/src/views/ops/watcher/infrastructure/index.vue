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
import {ref} from "vue";

// 监控地址
const monitorUrl = ref("http://grafana.infra.linesno.com/d/UDdpyzz7z/prometheus-2-0-stats?orgId=1&from=2024-12-13T23:06:26.677Z&to=2024-12-14T00:06:26.677Z&timezone=browser&refresh=1m&theme=dark&kiosk");

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
                "desc": "服务器性能和状态的实时监控" ,
                "link": "http://grafana.infra.linesno.com/d/xfpJB9FGz/node-exporter-for-prometheus-dashboard-en-20201010?orgId=1&refresh=1m&kiosk"
            },
            {
                "id": 104,
                "label": "网络监控",
                "desc": "网络流量和连接性的实时监控" ,
                "link": "http://grafana.infra.linesno.com/d/rYdddlPWk/node-exporter-full?orgId=1&refresh=1m&kiosk "
            }
        ]
    },
    {
        "id": 102,
        "label": "Kubernetes集群",
        "children": [
            {
                "id": 105,
                "label": "容器监控",
                "desc": "Docker、Pod等容器资源的使用情况和健康状态"
            },
            {
                "id": 106,
                "label": "K8S集群监控",
                "desc": "Kubernetes集群的状态、节点健康状况及资源分配"
            },
            {
                "id": 107,
                "label": "服务器监控",
                "desc": "服务器性能和状态的实时监控"
            },
            {
                "id": 108,
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
                "id": 211,
                "label": "分析数据库(clickhouse)",
                "desc": "系统和应用程序日志的收集与分析"
            },
            {
                "id": 212,
                "label": "反向代理(Nginx)",
                "desc": "反向代理服务器的性能和状态",
                "link": "http://grafana.infra.linesno.com/d/MsjffzSZz/nginx3?orgId=1&refresh=1m&kiosk"
            },
            {
                "id": 213,
                "label": "分布式配置中心",
                "desc": "配置管理和服务配置同步的监控"
            },
            {
                "id": 208,
                "label": "分布式索引存储(ES)",
                "desc": "缓存系统的命中率和性能指标",
                "link": "http://grafana.infra.linesno.com/d/n_nxrE_mk/elasticsearch?orgId=1&refresh=1m&kiosk "
            },
            {
                "id": 210,
                "label": "消息缓存(Redis)",
                "desc": "消息队列的吞吐量和延迟",
                "link": "http://grafana.infra.linesno.com/d/JIeHsmmYMk/redis-exporter-dashboard-cn-20221128-starsl-cn?orgId=1&refresh=1m&kiosk "
            }
        ]
    }
]);

/** 打开节点 **/
function handleNodeClick(data){
  if(data.link){
    monitorUrl.value = data.link ;
  }
}

</script>

<style scoped lang="scss">
</style>