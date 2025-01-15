// 用于存储日志的表(包括nginx日志/business日志/runing日志/container日志/巡检日志/请求日志)

CREATE TABLE IF NOT EXISTS universal_logs (
    id UUID DEFAULT generateUUIDv4(), -- 唯一ID号
    add_time DateTime,                -- 添加时间
    delete_time Nullable(DateTime),   -- 删除时间
    update_time DateTime DEFAULT now() COMMENT '更新时间', -- 更新时间
    has_status Int8 DEFAULT 0 COMMENT '状态(0启用|1禁用)', -- 状态
    operator_id Nullable(BIGINT) COMMENT '操作员 ID 用户权限: 只能看到自己操作的数据',
    field_prop String COMMENT '字段属性',
    last_update_operator_id Nullable(BIGINT) COMMENT '最后更新操作员 ID 用户权限: 只能看到自己操作的数据',

    has_delete Int8 DEFAULT 0 COMMENT '是否删除(1删除|0正常|null正常)',
    delete_manager Nullable(BIGINT) COMMENT '删除的人',
    application_id Nullable(BIGINT) COMMENT '所属应用 应用权限: 只能看到所属应用的权限【默认】',
    application_name String COMMENT '应用名称，唯一性，用于做应用标识【最好与springboot的application.name对应】',
    org_id Nullable(BIGINT) COMMENT '所属组织 , 组织权限',
    field_id String COMMENT '字段权限：部分字段权限无法加密或者不显示，或者大于某个值',
    department_id Nullable(BIGINT) COMMENT '部门权限: 只能看到自己所在部门的数据',

    log_type Enum8(
        'nginx' = 1,
        'business' = 2,
        'running' = 3,
        'container' = 4,
        'inspection' = 5,
        'request' = 6
    ) COMMENT '日志类型', -- 日志类型
    source String COMMENT '日志来源，例如服务器IP、服务名称等',
    level Enum8('info' = 1, 'warning' = 2, 'error' = 3, 'critical' = 4) COMMENT '日志级别',
    message String COMMENT '日志消息主体',
    extra_fields Map(String, String) COMMENT '用于保存额外字段的Map'

) ENGINE = MergeTree()
ORDER BY (log_type, add_time);