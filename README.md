# alinesno-infra-ops-watcher

提供系统监控和预警功能，提供应用运营画像服务

整体设计参考[观测云]。

| 基础设施监测           | 日志分析              | 应用分析           | 智能运维             |
|-----------------------|----------------------|-------------------|----------------------|
| 容器监控              | 日志查询              | 应用性能监控       | 事件与告警           |
| 网络性能监控          | 日志管理              | 会话重放           | 智能监控             |
| 设备监控              | 敏感数据扫描          | 错误追踪           | 异常追踪管理         |
| 云平台监控            | Pipeline 数据处理      | 可用性监测         | SRE 套件             |

// 菜单列表
- 仪盘表：显示当前收藏的列表，还有分类链接
- 告警事件：告警类型 + 告警事项列表
- 自动化流水线（定义还有日志）：流水线列表，还有执行情况（点击任务之后，查看到执行记录）
- 基础设施：服务器+容器（k8s）+网络+存储+中间件（中间件监控）
- 运行指标：指标定义 + Groovy脚本定义
- 日志监控：服务器日志 + 应用日志 + 中间件日志（clickhouse存储） 以树列表展示
- 应用性能监控：应用性能监控采集，使用OpenTelemetry+Clickhouse+Grafana，以树形展示列表
- 用户访问监控：访问应用列表，事件IO列表
- 巡检监控：应用巡检还有业务巡检 还有各项巡检的结果（通过自动化流水线获取到结果）
- 集成：三方集成的方式

## 测试

```shell
java \
    -javaagent:~/m2/devtool/openTelemetry/opentelemetry-javaagent-1.29.0.jar \
    -Dotel.resource.attributes=service.name=demo-watcher \
    -Dotel.exporter.otlp.headers=Authentication=123456 \
    -Dotel.traces.exporter=otlp \
    -Dotel.metrics.exporter=otlp \
    -Dotel.logs.exporter=otlp \
    -Dotel.exporter.otlp.endpoint=http://localhost:4316 \
    -jar target/demo-watcher-0.0.1-SNAPSHOT.jar
```

## 部署配置

> 针对于中小型团队场景，越简单越好
 
- 控制在8C/16G-8C/32G内存服务器上可部署

## 配合平台

- 权限配置平台
- 分布式配置中心
- (可选)大脑推理平台

```json
{
  "menus": [
    {
      "name": "Dashboard",
      "path": "/dashboard",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {
        "title": "仪盘表",
        "icon": "dashboard",
        "noCache": false
      },
      "children": [
        {
          "name": "Dashboard",
          "path": "index",
          "hidden": false,
          "component": "dashboard",
          "meta": {
            "title": "概览",
            "icon": "dashboard",
            "noCache": false
          }
        }
      ]
    },
    {
      "name": "Project",
      "path": "/project",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {
        "title": "项目管理",
        "icon": "post",
        "noCache": false
      },
      "children": [
        {
          "name": "Project",
          "path": "ops/logback/project/index",
          "hidden": false,
          "component": "ops/logback/project/index",
          "meta": {
            "title": "项目管理",
            "icon": "druid",
            "noCache": false
          }
        }
      ]
    },
    {
      "name": "Logger",
      "path": "/logger",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {
        "title": "日志分析",
        "icon": "log",
        "noCache": false
      },
      "children": [
        {
          "name": "LogStorage",
          "path": "ops/logback/logStorage/index",
          "hidden": false,
          "component": "ops/logback/logStorage/index",
          "meta": {
            "title": "运行日志",
            "icon": "form",
            "noCache": false
          }
        },
        {
          "name": "BusinessLog",
          "path": "ops/logback/businessLog/index",
          "hidden": false,
          "component": "ops/logback/businessLog/index",
          "meta": {
            "title": "业务日志",
            "icon": "tree",
            "noCache": false
          }
        },
        {
          "name": "DatabaseLog",
          "path": "ops/logback/databaseLog/index",
          "hidden": false,
          "component": "ops/logback/databaseLog/index",
          "meta": {
            "title": "数据日志",
            "icon": "user",
            "noCache": false
          }
        },
        {
          "name": "ContainerLog",
          "path": "ops/logback/containerLog/index",
          "hidden": false,
          "component": "ops/logback/containerLog/index",
          "meta": {
            "title": "容器日志",
            "icon": "form",
            "noCache": false
          }
        },
        {
          "name": "FrontLog",
          "path": "ops/logback/frontLog/index",
          "hidden": false,
          "component": "ops/logback/frontLog/index",
          "meta": {
            "title": "前端监控",
            "icon": "guide",
            "noCache": false
          }
        },
        {
          "name": "Inspection",
          "path": "ops/logback/nginx/index",
          "hidden": false,
          "component": "ops/logback/nginx/index",
          "meta": {
            "title": "代理日志",
            "icon": "druid",
            "noCache": false
          }
        },
        {
          "name": "Account",
          "path": "ops/logback/inspection/index",
          "hidden": false,
          "component": "ops/logback/inspection/index",
          "meta": {
            "title": "巡检日志",
            "icon": "online",
            "noCache": false
          }
        }
      ]
    },
    {
      "name": "Monitor",
      "path": "/monitor",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {
        "title": "系统配置",
        "icon": "monitor",
        "noCache": false
      },
      "children": [
        {
          "name": "Analyse",
          "path": "ops/logback/analyse/index",
          "hidden": false,
          "component": "ops/logback/analyse/index",
          "meta": {
            "title": "请求监控",
            "icon": "form",
            "noCache": false
          }
        },
        {
          "name": "Config",
          "path": "ops/logback/config/index",
          "hidden": false,
          "component": "ops/logback/config/index",
          "meta": {
            "title": "日志配置",
            "icon": "form",
            "noCache": false
          }
        }
      ]
    },
    {
      "name": "Alert",
      "path": "/alert",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {
        "title": "告警情况",
        "icon": "post",
        "noCache": false
      },
      "children": [
        {
          "name": "AllMessage",
          "path": "ops/watcher/message/index",
          "hidden": false,
          "component": "ops/watcher/message/index",
          "meta": {
            "title": "所有告警",
            "icon": "form",
            "noCache": false
          }
        },
        {
          "name": "ErrorMessage",
          "path": "ops/watcher/error/index",
          "hidden": false,
          "component": "ops/watcher/error/index",
          "meta": {
            "title": "未处理告警",
            "icon": "tree",
            "noCache": false
          }
        },
        {
          "name": "PersonMessage",
          "path": "ops/watcher/person/index",
          "hidden": false,
          "component": "ops/watcher/person/index",
          "meta": {
            "title": "我的告警",
            "icon": "user",
            "noCache": false
          }
        }
      ]
    },
    {
      "name": "Notice",
      "path": "/notice",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {
        "title": "集成通知",
        "icon": "log",
        "noCache": false
      },
      "children": [
        {
          "name": "ProviderChannel",
          "path": "ops/watcher/channel/index",
          "hidden": false,
          "component": "ops/watcher/channel/index",
          "meta": {
            "title": "接入渠道",
            "icon": "guide",
            "noCache": false
          }
        },
        {
          "name": "AlertChannel",
          "path": "ops/watcher/alert/index",
          "hidden": false,
          "component": "ops/watcher/alert/index",
          "meta": {
            "title": "告警集成",
            "icon": "form",
            "noCache": false
          }
        }
      ]
    },
    {
      "name": "Config",
      "path": "/config",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {
        "title": "告警配置",
        "icon": "monitor",
        "noCache": false
      },
      "children": [
        {
          "name": "Template",
          "path": "ops/watcher/template/index",
          "hidden": false,
          "component": "ops/watcher/template/index",
          "meta": {
            "title": "通知模板",
            "icon": "form",
            "noCache": false
          }
        },
        {
          "name": "Rule",
          "path": "ops/watcher/rule/index",
          "hidden": false,
          "component": "ops/watcher/rule/index",
          "meta": {
            "title": "屏蔽规则",
            "icon": "online",
            "noCache": false
          }
        },
        {
          "name": "AllGroup",
          "path": "ops/watcher/group/index",
          "hidden": false,
          "component": "ops/watcher/group/index",
          "meta": {
            "title": "告警分组",
            "icon": "guide",
            "noCache": false
          }
        }
      ]
    },
    {
      "name": "ProcessManagement",
      "path": "/process",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {
        "title": "自动化流程管理",
        "icon": "process",
        "noCache": false
      },
      "children": [
        {
          "name": "ProcessCategory",
          "path": "process/category",
          "hidden": false,
          "component": "process/category",
          "meta": {
            "title": "流程分类",
            "icon": "category",
            "noCache": false
          }
        },
        {
          "name": "ProcessInstance",
          "path": "process/instance",
          "hidden": false,
          "component": "process/instance",
          "meta": {
            "title": "流程实例",
            "icon": "instance",
            "noCache": false
          }
        }
      ]
    },
    {
      "name": "MonitoringManagement",
      "path": "/monitoring",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {
        "title": "监控管理",
        "icon": "monitoring",
        "noCache": false
      },
      "children": [
        {
          "name": "MonitoringType",
          "path": "monitoring/type",
          "hidden": false,
          "component": "monitoring/type",
          "meta": {
            "title": "监控类型",
            "icon": "type",
            "noCache": false
          }
        },
        {
          "name": "MonitoringLink",
          "path": "monitoring/link",
          "hidden": false,
          "component": "monitoring/link",
          "meta": {
            "title": "监控链接",
            "icon": "link",
            "noCache": false
          }
        }
      ]
    },
    {
      "name": "MetricsManagement",
      "path": "/metrics",
      "hidden": false,
      "redirect": "noRedirect",
      "component": "Layout",
      "alwaysShow": true,
      "meta": {
        "title": "指标管理",
        "icon": "metrics",
        "noCache": false
      },
      "children": [
        {
          "name": "BusinessMetrics",
          "path": "metrics/business",
          "hidden": false,
          "component": "metrics/business",
          "meta": {
            "title": "业务指标",
            "icon": "business",
            "noCache": false
          }
        },
        {
          "name": "SystemMetrics",
          "path": "metrics/system",
          "hidden": false,
          "component": "metrics/system",
          "meta": {
            "title": "系统指标",
            "icon": "system",
            "noCache": false
          }
        },
        {
          "name": "MetricsInterface",
          "path": "metrics/interface",
          "hidden": false,
          "component": "metrics/interface",
          "meta": {
            "title": "指标接口",
            "icon": "interface",
            "noCache": false
          }
        }
      ]
    }
  ]
}
```

## 模块说明

- ops-monitor-agent 监控采集agent
- ops-monitor-scheduler 自动化操作 
- ops-monitor-logger 日志处理 
- ops-monitor-collector 数据接收

## 技术

- 日志存储: clickhouse
- 指标存储: prometheus 
- 展示: grafana 

## 数据处理流

数据采集

- openTelemetry 监控采集 
- prometheus 指标采集
- filebeat 日志采集

## 鸣谢

- [openTelemetry](https://opentelemetry.io/)
- [clickhouse](https://clickhouse.com/)
- [prometheus](https://prometheus.io/)
- [grafana](https://grafana.com/)
- [filebeat](https://www.elastic.co/cn/products/beats/filebeat)
- [spring-boot](https://spring.io/projects/spring-boot)
- [log.js](https://gitee.com/clark-fl/log.js)
- [wf_monitor](https://www.webfunny.cn/wf_monitor/home.html)