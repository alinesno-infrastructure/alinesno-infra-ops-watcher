-- telemetry_logs 表
CREATE TABLE IF NOT EXISTS telemetry_logs
(
    `timestamp` DateTime COMMENT '时间戳',
    `trace_id` String COMMENT '跟踪ID',
    `span_id` String COMMENT '跨度ID',
    `trace_flags` UInt8 COMMENT '跟踪标志',
    `severity_text` String COMMENT '严重性文本',
    `severity_number` Int8 COMMENT '严重性数字',
    `service_name` String COMMENT '服务名称',
    `body` String COMMENT '日志主体内容',
    `resource_schema_url` String COMMENT '资源模式URL',
    `resource_attributes` Map(String, String) COMMENT '资源属性',
    `scope_schema_url` String COMMENT '作用域模式URL',
    `scope_name` String COMMENT '作用域名称',
    `scope_version` String COMMENT '作用域版本',
    `scope_attributes` Map(String, String) COMMENT '作用域属性',
    `log_attributes` Map(String, String) COMMENT '日志属性'
) ENGINE = MergeTree()
ORDER BY timestamp
COMMENT '此表用于存储遥测日志信息';

-- telemetry_trace 表
CREATE TABLE IF NOT EXISTS telemetry_trace
(
    `timestamp` DateTime COMMENT '时间戳',
    `trace_id` String COMMENT '跟踪ID',
    `span_id` String COMMENT '跨度ID',
    `parent_span_id` String COMMENT '父跨度ID',
    `trace_state` String COMMENT '跟踪状态',
    `span_name` String COMMENT '跨度名称',
    `span_kind` Enum8('UNSPECIFIED' = 0, 'INTERNAL' = 1, 'SERVER' = 2, 'CLIENT' = 3, 'PRODUCER' = 4, 'CONSUMER' = 5) COMMENT '跨度类型',
    `service_name` String COMMENT '服务名称',
    `resource_attributes` Map(String, String) COMMENT '资源属性',
    `scope_name` String COMMENT '作用域名称',
    `scope_version` String COMMENT '作用域版本',
    `span_attributes` Map(String, String) COMMENT '跨度属性',
    `duration` Float64 COMMENT '持续时间（秒）',
    `status_code` Int8 COMMENT '状态代码',
    `status_message` String COMMENT '状态消息',
    `events` Nested(
        `timestamp` DateTime,
        `name` String,
        `attributes` Map(String, String)
    ),
    `links` Nested(
        `trace_id` String,
        `span_id` String,
        `trace_state` String,
        `attributes` Map(String, String)
    )
) ENGINE = MergeTree()
ORDER BY timestamp
COMMENT '此表用于存储追踪数据';

-- telemetry_metrics_exponential_histogram 表
CREATE TABLE IF NOT EXISTS telemetry_metrics_exponential_histogram
(
    `resource_attributes` Map(String, String) COMMENT '资源属性',
    `resource_schema_url` String COMMENT '资源模式URL',
    `scope_name` String COMMENT '作用域名称',
    `scope_version` String COMMENT '作用域版本',
    `scope_attributes` Map(String, String) COMMENT '作用域属性',
    `scope_dropped_attr_count` UInt32 COMMENT '作用域丢弃的属性数量',
    `scope_schema_url` String COMMENT '作用域模式URL',
    `metric_name` String COMMENT '指标名称',
    `metric_description` String COMMENT '指标描述',
    `metric_unit` String COMMENT '指标单位',
    `attributes` Map(String, String) COMMENT '属性',
    `start_time_unix_nano` Int64 COMMENT '开始时间（Unix纳秒时间戳）',
    `time_unix_nano` Int64 COMMENT '时间（Unix纳秒时间戳）',
    `count` UInt64 COMMENT '计数',
    `sum` Float64 COMMENT '总和',
    `scale` Int8 COMMENT '尺度',
    `zero_count` UInt64 COMMENT '零计数',
    `positive_offset` Int8 COMMENT '正偏移',
    `positive_bucket_counts` Array(UInt64) COMMENT '正桶计数',
    `negative_offset` Int8 COMMENT '负偏移',
    `negative_bucket_counts` Array(UInt64) COMMENT '负桶计数',
    `exemplars` Nested(
        `filtered_attributes` Map(String, String),
        `time_unix_nano` Int64,
        `value` Float64,
        `span_id` String,
        `trace_id` String
    ),
    `flags` UInt8 COMMENT '标记',
    `min` Float64 COMMENT '最小值',
    `max` Float64 COMMENT '最大值'
) ENGINE = MergeTree()
ORDER BY time_unix_nano
COMMENT '此表用于存储指数直方图类型的遥测指标数据';

-- telemetry_metrics_gauge 表
CREATE TABLE IF NOT EXISTS telemetry_metrics_gauge
(
    `resource_attributes` String COMMENT '资源属性',
    `resource_schema_url` String COMMENT '资源模式URL',
    `scope_name` String COMMENT '作用域名称',
    `scope_version` String COMMENT '作用域版本',
    `scope_attributes` String COMMENT '作用域属性',
    `scope_dropped_attr_count` UInt32 COMMENT '作用域丢弃的属性数量',
    `scope_schema_url` String COMMENT '作用域模式URL',
    `metric_name` String COMMENT '指标名称',
    `metric_description` String COMMENT '指标描述',
    `metric_unit` String COMMENT '指标单位',
    `attributes` String COMMENT '属性',
    `start_time_unix_nano` Int64 COMMENT '开始时间（Unix纳秒时间戳）',
    `time_unix_nano` Int64 COMMENT '时间（Unix纳秒时间戳）',
    `value` Float64 COMMENT '值',
--    `exemplars` Nested(
--        `filtered_attributes` String ,
--        `time_unix_nano` Int64,
--        `value` Float64,
--        `span_id` String,
--        `trace_id` String
--    )
    `flags` UInt8 COMMENT '标记',
) ENGINE = MergeTree()
ORDER BY time_unix_nano
COMMENT '此表用于存储仪表盘类型的遥测指标数据';

-- telemetry_metrics_sum 表
CREATE TABLE telemetry_metrics_sum
(
    `resource_attributes` Map(String, String) COMMENT '资源属性', -- 资源属性
    `resource_schema_url` String COMMENT '资源模式URL', -- 资源模式URL
    `scope_name` String COMMENT '作用域名称', -- 作用域名称
    `scope_version` String COMMENT '作用域版本', -- 作用域版本
    `scope_attributes` Map(String, String) COMMENT '作用域属性', -- 作用域属性
    `scope_dropped_attr_count` UInt32 COMMENT '作用域丢弃的属性数量', -- 作用域丢弃的属性数量
    `scope_schema_url` String COMMENT '作用域模式URL', -- 作用域模式URL
    `metric_name` String COMMENT '指标名称', -- 指标名称
    `metric_description` String COMMENT '指标描述', -- 指标描述
    `metric_unit` String COMMENT '指标单位', -- 指标单位
    `attributes` Map(String, String) COMMENT '附加属性', -- 附加属性
    `start_time_unix_nano` Int64 COMMENT '开始时间（纳秒级Unix时间戳）', -- 开始时间（纳秒级Unix时间戳）
    `time_unix_nano` Int64 COMMENT '记录时间（纳秒级Unix时间戳）', -- 记录时间（纳秒级Unix时间戳）
    `value` Float64 COMMENT '值', -- 值
    `flags` UInt8 COMMENT '标记' -- 标记
) ENGINE = MergeTree()
ORDER BY (`time_unix_nano`, `metric_name`)
COMMENT '此表用于存储仪表盘类型的遥测指标数据';
