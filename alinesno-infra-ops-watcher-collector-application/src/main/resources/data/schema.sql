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

-- telemetry_metrics_histogram 表
CREATE TABLE telemetry_metrics_histogram
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
    `attributes` Map(String, String) COMMENT '附加属性',
    `start_time_unix_nano` Int64 COMMENT '开始时间（纳秒级Unix时间戳）',
    `time_unix_nano` Int64 COMMENT '记录时间（纳秒级Unix时间戳）',
    `count` UInt64 COMMENT '计数',
    `sum` Float64 COMMENT '总和',
    `bucket_counts` String COMMENT '桶计数',
    `explicit_bounds` Array(Float64) COMMENT '显式边界',
    `exemplars_filtered_attributes` Array(Map(String, String)) COMMENT '示例过滤属性',
    `exemplars_time_unix_nano` Array(Int64) COMMENT '示例时间戳（纳秒级Unix时间戳）',
    `exemplars_value` Array(Float64) COMMENT '示例值',
    `exemplars_span_id` Array(String) COMMENT '示例跨度ID',
    `exemplars_trace_id` Array(String) COMMENT '示例跟踪ID',
    `flags` UInt8 COMMENT '标记',
    `min` Float64 COMMENT '最小值',
    `max` Float64 COMMENT '最大值'
) ENGINE = MergeTree()
ORDER BY (`time_unix_nano`, `metric_name`)
COMMENT '此表用于存储直方图类型的遥测指标数据';

CREATE TABLE telemetry_metrics_exponential_histogram
(
    `resource_attributes` Map(String, String) COMMENT '资源属性',
    `resource_schema_url` String COMMENT '资源模式URL',
    `scope_name` String COMMENT '作用域名称',
    `scope_version` String COMMENT '作用域版本',
    `scope_attributes` Map(String, String) COMMENT '作用域属性',
    `scope_dropped_attr_count` UInt64 COMMENT '作用域丢弃的属性数量',
    `scope_schema_url` String COMMENT '作用域模式URL',
    `metric_name` String COMMENT '指标名称',
    `metric_description` String COMMENT '指标描述',
    `metric_unit` String COMMENT '指标单位',
    `attributes` Map(String, String) COMMENT '附加属性',
    `start_time_unix_nano` Int64 COMMENT '开始时间（纳秒级Unix时间戳）',
    `time_unix_nano` Int64 COMMENT '记录时间（纳秒级Unix时间戳）',
    `count` UInt64 COMMENT '计数',
    `sum` Float64 COMMENT '总和',
    `scale` Int32 COMMENT '比例尺',
    `zero_count` UInt64 COMMENT '零桶计数',
    `positive_offset` Int32 COMMENT '正偏移量',
    `positive_bucket_counts` Array(UInt64) COMMENT '正桶计数',
    `negative_offset` Int32 COMMENT '负偏移量',
    `negative_bucket_counts` Array(UInt64) COMMENT '负桶计数',
    `exemplars_filtered_attributes` Array(Map(String, String)) COMMENT '示例过滤属性',
    `exemplars_time_unix_nano` Array(Int64) COMMENT '示例时间戳（纳秒级Unix时间戳）',
    `exemplars_value` Array(Float64) COMMENT '示例值',
    `exemplars_span_id` Array(String) COMMENT '示例跨度ID',
    `exemplars_trace_id` Array(String) COMMENT '示例跟踪ID',
    `flags` UInt8 COMMENT '标记',
    `min` Float64 COMMENT '最小值',
    `max` Float64 COMMENT '最大值'
) ENGINE = MergeTree()
ORDER BY (`time_unix_nano`, `metric_name`)
COMMENT '此表用于存储指数直方图类型的遥测指标数据';

-- 监控表结构
CREATE TABLE IF NOT EXISTS nginx_access_log (
    -- 客户端信息
    remote_addr String COMMENT '客户端IP地址',
    remote_user String COMMENT '远程用户',
    time_local String COMMENT '请求时间（本地时间）',

    -- 请求信息
    request String COMMENT '完整的请求行',
    status UInt16 COMMENT 'HTTP状态码',
    body_size UInt32 COMMENT '发送的字节数（body部分）',
    referer String COMMENT '来源页面URL',
    agent String COMMENT '用户代理字符串',
    x_forwarded String COMMENT 'X-Forwarded-For头信息',

    -- 额外的日志信息
    request_time String COMMENT '请求处理时间（秒）',
    upstream_response_time String COMMENT '后端响应时间',
    upstream_addr String COMMENT '后端服务器地址',
    ssl_protocol String COMMENT 'SSL协议版本',
    ssl_cipher String COMMENT 'SSL加密算法',
    request_length UInt32 COMMENT '请求长度',
    gzip_ratio String COMMENT 'Gzip压缩比率',
    connection UInt32 COMMENT '连接序列号',
    connection_requests UInt32 COMMENT '当前连接的请求数量',
    msec Float64 COMMENT '日志记录的时间戳（毫秒）',
    pipe String COMMENT '是否通过管道转发',
    server_protocol String COMMENT '服务器使用的协议',
    http_host String COMMENT 'Host头部信息',
    http_cookie String COMMENT 'Cookie头部信息',
    bytes_sent UInt32 COMMENT '发送给客户端的总字节数',
    request_method String COMMENT '请求方法（GET, POST等）',
    scheme String COMMENT '使用的方案（http或https）',
    request_uri String COMMENT '请求的URI路径',
    args String COMMENT '查询字符串参数',
    http_accept_encoding String COMMENT 'Accept-Encoding头部信息',
    http_accept_language String COMMENT 'Accept-Language头部信息',
    http_via String COMMENT 'Via头部信息',
    http_connection String COMMENT 'Connection头部信息',
    http_cache_control String COMMENT 'Cache-Control头部信息'
) ENGINE = MergeTree()
ORDER BY (time_local)
COMMENT '此表存储了来自Nginx访问日志的详细信息，用于分析网站流量、用户行为和其他重要指标。';


--- >>>>>>> 前端监控 >>>>>>>>> -------
-- 表注释：此表用于记录用户的终端信息和页面性能指标，支持详细的行为分析。
CREATE TABLE IF NOT EXISTS font_user_performance_logs (
    -- 终端信息
    resolving_power String COMMENT '终端分辨率',  -- 示例："2560*1440"
    referrer String COMMENT '文档来源页面的URL',  -- 示例："http://localhost/userAccess"
    ua String COMMENT '用户代理字符串',  -- 示例："Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36"
    browser_json String COMMENT '浏览器信息，JSON格式存储',  -- 包含浏览器名称、版本等信息
    engine_json String COMMENT '浏览器引擎信息，JSON格式存储',  -- 包含引擎名称、版本等信息
    os_json String COMMENT '操作系统信息，JSON格式存储',  -- 包含操作系统名称、版本等信息
    device_json String COMMENT '设备信息，JSON格式存储',  -- 包含设备特定信息
    cpu_json String COMMENT 'CPU信息，JSON格式存储',  -- 包含CPU特定信息
    ip String COMMENT 'IP地址',
    city String COMMENT '城市信息',

    -- 性能信息
    fp Int32 COMMENT '首次绘制时间 (FP)，单位：毫秒',  -- 示例：17
    redirect_time Int32 COMMENT '重定向耗时，单位：毫秒',  -- 示例：0
    domain_lookup_time Int32 COMMENT 'DNS查询耗时，单位：毫秒',  -- 示例：0
    connect_time Int32 COMMENT 'TCP连接耗时，单位：毫秒',  -- 示例：0
    response_time Int32 COMMENT 'HTTP请求耗时，单位：毫秒',  -- 示例：1
    dom_complete_time Int32 COMMENT 'DOM解析完成时间，单位：毫秒',  -- 示例：1036
    fcp Float64 COMMENT '首次内容绘制时间 (FCP)，单位：毫秒',  -- 示例：209.19999999995343
    ttfb Float64 COMMENT '第一字节时间 (TTFB)，单位：毫秒',  -- 示例：16.300000000046566
    lcp Float64 COMMENT '最大内容绘制时间 (LCP)，单位：毫秒',  -- 示例：209.19999999995343
    fid Float64 COMMENT '首次输入延迟 (FID)，单位：毫秒',  -- 示例：20.100000000093132

    -- 用户唯一标识符
    uid String COMMENT '用户唯一标识符，由IP与用户代理生成',  -- 示例："cdc818bdd16ca7ca135347e5eced8d37"

    -- 日志配置中的ID标识
    id String COMMENT '日志配置中的ID标识'  -- 示例："logJs"

) ENGINE = MergeTree()
ORDER BY (city, ip, uid);

-- 表注释：此表用于记录用户的点击事件，支持详细的行为分析。
CREATE TABLE IF NOT EXISTS font_click_events (
    -- 点击事件信息
    log_type String COMMENT '日志类型，如 "click"',  -- 示例："click"
    ele_type String COMMENT '元素类型，如 "li"',  -- 示例："li"
    ele_content String COMMENT '元素内容',  -- 示例：""（空字符串）
    time_step Int64 COMMENT '时间戳，单位：毫秒',  -- 示例：1734353167708
    href String COMMENT '链接地址',  -- 示例："http://localhost/pipelines"
    ele_location String COMMENT '元素位置',  -- 示例：""（空字符串）

    -- 用户唯一标识符
    uid String COMMENT '用户唯一标识符，由IP与用户代理生成',  -- 示例："cdc818bdd16ca7ca135347e5eced8d37"

    -- 日志配置中的ID标识
    id String COMMENT '日志配置中的ID标识'  -- 示例："logJs"

) ENGINE = MergeTree()
ORDER BY (uid, time_step);