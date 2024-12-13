# alinesno-infra-ops-watcher

提供系统监控和预警功能，提供应用运营画像服务

整体设计参考[观测云]。

| 基础设施监测           | 日志分析              | 应用分析           | 智能运维             |
|-----------------------|----------------------|-------------------|----------------------|
| 容器监控              | 日志查询              | 应用性能监控       | 事件与告警           |
| 网络性能监控          | 日志管理              | 会话重放           | 智能监控             |
| 设备监控              | 敏感数据扫描          | 错误追踪           | 异常追踪管理         |
| 云平台监控            | Pipeline 数据处理      | 可用性监测         | SRE 套件             |

## 部署配置

> 针对于中小型团队场景，越简单越好
 
- 控制在8C/16G-8C/32G内存服务器上可部署

## 配合平台

- 权限配置平台
- 分布式配置中心
- (可选)大脑推理平台

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