# 日志数据采集器

根据您提供的SQL插入语句，可以推断出ClickHouse表的大致结构。请注意，实际的表设计可能会根据具体需求有所不同，例如数据类型的选取、是否使用默认值等。以下为基于给定信息推测出的ClickHouse表结构，并添加了中文注释：

### 日志服务 (telemetry_logs)

```sql
CREATE TABLE telemetry_logs
(
    `Timestamp` DateTime, -- 时间戳
    `TraceId` String, -- 跟踪ID
    `SpanId` String, -- 跨度ID
    `TraceFlags` UInt8, -- 跟踪标志
    `SeverityText` String, -- 严重性文本
    `SeverityNumber` Int8, -- 严重性数字
    `ServiceName` String, -- 服务名称
    `Body` String, -- 日志主体内容
    `ResourceSchemaUrl` String, -- 资源模式URL
    `ResourceAttributes` Map(String, String), -- 资源属性
    `ScopeSchemaUrl` String, -- 作用域模式URL
    `ScopeName` String, -- 作用域名称
    `ScopeVersion` String, -- 作用域版本
    `ScopeAttributes` Map(String, String), -- 作用域属性
    `LogAttributes` Map(String, String) -- 日志属性
) ENGINE = MergeTree()
ORDER BY Timestamp;
```

### 链路跟踪服务 (telemetry_trace)

```sql
CREATE TABLE telemetry_trace
(
    `Timestamp` DateTime, -- 时间戳
    `TraceId` String, -- 跟踪ID
    `SpanId` String, -- 跨度ID
    `ParentSpanId` String, -- 父跨度ID
    `TraceState` String, -- 跟踪状态
    `SpanName` String, -- 跨度名称
    `SpanKind` Enum8('UNSPECIFIED' = 0, 'INTERNAL' = 1, 'SERVER' = 2, 'CLIENT' = 3, 'PRODUCER' = 4, 'CONSUMER' = 5), -- 跨度类型
    `ServiceName` String, -- 服务名称
    `ResourceAttributes` Map(String, String), -- 资源属性
    `ScopeName` String, -- 作用域名称
    `ScopeVersion` String, -- 作用域版本
    `SpanAttributes` Map(String, String), -- 跨度属性
    `Duration` Float64, -- 持续时间（秒）
    `StatusCode` Int8, -- 状态代码
    `StatusMessage` String, -- 状态消息
    `Events` Nested(
        `Timestamp` DateTime, -- 事件时间戳
        `Name` String, -- 事件名称
        `Attributes` Map(String, String) -- 事件属性
    ),
    `Links` Nested(
        `TraceId` String, -- 关联跟踪ID
        `SpanId` String, -- 关联跨度ID
        `TraceState` String, -- 关联跟踪状态
        `Attributes` Map(String, String) -- 关联属性
    )
) ENGINE = MergeTree()
ORDER BY Timestamp;
```

### 监控服务

对于监控服务，由于有多种不同的指标类型（exponential_histogram, gauge, histogram, summary, sum），每个类型的表结构会有所不同。以下是其中两个示例：

#### 监控服务 - Exponential Histogram (telemetry_metrics_exponential_histogram)

```sql
CREATE TABLE telemetry_metrics_exponential_histogram
(
    `ResourceAttributes` Map(String, String), -- 资源属性
    `ResourceSchemaUrl` String, -- 资源模式URL
    `ScopeName` String, -- 作用域名称
    `ScopeVersion` String, -- 作用域版本
    `ScopeAttributes` Map(String, String), -- 作用域属性
    `ScopeDroppedAttrCount` UInt32, -- 作用域丢弃的属性数量
    `ScopeSchemaUrl` String, -- 作用域模式URL
    `MetricName` String, -- 指标名称
    `MetricDescription` String, -- 指标描述
    `MetricUnit` String, -- 指标单位
    `Attributes` Map(String, String), -- 属性
    `StartTimeUnix` Int64, -- 开始时间（Unix时间戳）
    `TimeUnix` Int64, -- 时间（Unix时间戳）
    `Count` UInt64, -- 计数
    `Sum` Float64, -- 总和
    `Scale` Int8, -- 尺度
    `ZeroCount` UInt64, -- 零计数
    `PositiveOffset` Int8, -- 正偏移
    `PositiveBucketCounts` Array(UInt64), -- 正桶计数
    `NegativeOffset` Int8, -- 负偏移
    `NegativeBucketCounts` Array(UInt64), -- 负桶计数
    `Exemplars` Nested(
        `FilteredAttributes` Map(String, String), -- 示例过滤后的属性
        `TimeUnix` Int64, -- 示例时间（Unix时间戳）
        `Value` Float64, -- 示例值
        `SpanId` String, -- 示例跨度ID
        `TraceId` String -- 示例跟踪ID
    ),
    `Flags` UInt8, -- 标记
    `Min` Float64, -- 最小值
    `Max` Float64 -- 最大值
) ENGINE = MergeTree()
ORDER BY TimeUnix;
```

#### 监控服务 - Gauge (telemetry_metrics_gauge)

```sql
CREATE TABLE telemetry_metrics_gauge
(
    `ResourceAttributes` Map(String, String), -- 资源属性
    `ResourceSchemaUrl` String, -- 资源模式URL
    `ScopeName` String, -- 作用域名称
    `ScopeVersion` String, -- 作用域版本
    `ScopeAttributes` Map(String, String), -- 作用域属性
    `ScopeDroppedAttrCount` UInt32, -- 作用域丢弃的属性数量
    `ScopeSchemaUrl` String, -- 作用域模式URL
    `MetricName` String, -- 指标名称
    `MetricDescription` String, -- 指标描述
    `MetricUnit` String, -- 指标单位
    `Attributes` Map(String, String), -- 属性
    `StartTimeUnix` Int64, -- 开始时间（Unix时间戳）
    `TimeUnix` Int64, -- 时间（Unix时间戳）
    `Value` Float64, -- 值
    `Flags` UInt8, -- 标记
    `Exemplars` Nested(
        `FilteredAttributes` Map(String, String), -- 示例过滤后的属性
        `TimeUnix` Int64, -- 示例时间（Unix时间戳）
        `Value` Float64, -- 示例值
        `SpanId` String, -- 示例跨度ID
        `TraceId` String -- 示例跟踪ID
    )
) ENGINE = MergeTree()
ORDER BY TimeUnix;
```

其余的监控服务表（如Histogram, Summary, Sum）可以按照类似的模式创建。每个表都应根据其特定的数据结构进行调整，以确保最佳性能和数据准确性。

### 主要采集

- openTelemetry的collector
- front.log.js前端日志