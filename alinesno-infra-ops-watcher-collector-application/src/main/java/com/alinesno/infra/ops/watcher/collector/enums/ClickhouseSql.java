package com.alinesno.infra.ops.watcher.collector.enums;

public class ClickhouseSql {

    // 日志服务
    public static final String logsSql = "INSERT INTO telemetry_logs (" +
            "timestamp," +
            "trace_id," +
            "span_id," +
            "trace_flags," +
            "severity_text," +
            "severity_number," +
            "service_name," +
            "body," +
            "resource_schema_url," +
            "resource_attributes," +
            "scope_schema_url," +
            "scope_name," +
            "scope_version," +
            "scope_attributes," +
            "log_attributes" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // 链路跟踪服务
    public static final String traceSql = "INSERT INTO telemetry_trace (" +
            "timestamp," +
            "trace_id," +
            "span_id," +
            "parent_span_id," +
            "trace_state," +
            "span_name," +
            "span_kind," +
            "service_name," +
            "resource_attributes," +
            "scope_name," +
            "scope_version," +
            "span_attributes," +
            "duration," +
            "status_code," +
            "status_message," +
            "events.timestamp," +
            "events.name," +
            "events.attributes," +
            "links.trace_id," +
            "links.span_id," +
            "links.trace_state," +
            "links.attributes" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // 监控服务 histogram
    public static final String metricsExponentialHistogramSql = "INSERT INTO telemetry_metrics_exponential_histogram (" +
            "resource_attributes," +
            "resource_schema_url," +
            "scope_name," +
            "scope_version," +
            "scope_attributes," +
            "scope_dropped_attr_count," +
            "scope_schema_url," +
            "metric_name," +
            "metric_description," +
            "metric_unit," +
            "attributes," +
            "start_time_unix_nano," +
            "time_unix_nano," +
            "count," +
            "sum," +
            "scale," +
            "zero_count," +
            "positive_offset," +
            "positive_bucket_counts," +
            "negative_offset," +
            "negative_bucket_counts," +
            "exemplars.filtered_attributes," +
            "exemplars.time_unix_nano," +
            "exemplars.value," +
            "exemplars.span_id," +
            "exemplars.trace_id," +
            "flags," +
            "min," +
            "max" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // 监控服务 gauge
    public static final String metricsGaugeSql = "INSERT INTO telemetry_metrics_gauge (" +
            "resource_attributes," +
            "resource_schema_url," +
            "scope_name," +
            "scope_version," +
            "scope_attributes," +
            "scope_dropped_attr_count," +
            "scope_schema_url," +
            "metric_name," +
            "metric_description," +
            "metric_unit," +
            "attributes," +
            "start_time_unix_nano," +
            "time_unix_nano," +
            "value," +
            "flags," +
//            "exemplars.filtered_attributes," +
//            "exemplars.time_unix_nano," +
//            "exemplars.value," +
//            "exemplars.span_id," +
//            "exemplars.trace_id" +
//            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // 监控服务 histogram
    public static final String metricsHistogramSql = "INSERT INTO telemetry_metrics_histogram (" +
            "resource_attributes," +
            "resource_schema_url," +
            "scope_name," +
            "scope_version," +
            "scope_attributes," +
            "scope_dropped_attr_count," +
            "scope_schema_url," +
            "metric_name," +
            "metric_description," +
            "metric_unit," +
            "attributes," +
            "start_time_unix_nano," +
            "time_unix_nano," +
            "count," +
            "sum," +
            "bucket_counts," +
            "explicit_bounds," +
            "exemplars.filtered_attributes," +
            "exemplars.time_unix_nano," +
            "exemplars.value," +
            "exemplars.span_id," +
            "exemplars.trace_id," +
            "flags," +
            "min," +
            "max" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // 监控服务 summary
    public static final String metricsSummarySql = "INSERT INTO telemetry_metrics_summary (" +
            "resource_attributes," +
            "resource_schema_url," +
            "scope_name," +
            "scope_version," +
            "scope_attributes," +
            "scope_dropped_attr_count," +
            "scope_schema_url," +
            "metric_name," +
            "metric_description," +
            "metric_unit," +
            "attributes," +
            "start_time_unix_nano," +
            "time_unix_nano," +
            "count," +
            "sum," +
            "value_at_quantiles.quantile," +
            "value_at_quantiles.value," +
            "flags" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // 监控服务 sum
    public static final String metricsSumSql = "INSERT INTO telemetry_metrics_sum (" +
            "resource_attributes," +
            "resource_schema_url," +
            "scope_name," +
            "scope_version," +
            "scope_attributes," +
            "scope_dropped_attr_count," +
            "scope_schema_url," +
            "metric_name," +
            "metric_description," +
            "metric_unit," +
            "attributes," +
            "start_time_unix_nano," +
            "time_unix_nano," +
            "value," +
            "flags" +
//            "exemplars.filtered_attributes," +
//            "exemplars.time_unix_nano," +
//            "exemplars.value," +
//            "exemplars.span_id," +
//            "exemplars.trace_id," +
//            "agg_temp," +
//            "is_monotonic" +
//            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
}