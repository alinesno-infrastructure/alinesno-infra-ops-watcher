package com.alinesno.infra.ops.watcher.collector.receiver.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NginxAccessLog extends GenericLogEntry {
    private String remoteAddr; // 客户端IP地址
    private String remoteUser; // 远程用户
    private String timeLocal;  // 请求时间（本地时间）
    private String request;    // 完整的请求行
    private String status;     // HTTP状态码
    private String bodySize;   // 发送的字节数（body部分）
    private String referer;    // 来源页面URL
    private String agent;      // 用户代理字符串
    private String xForwarded; // X-Forwarded-For头信息

    // 添加更多字段以匹配JSON格式
    private String requestTime; // 请求处理时间（秒）
    private String upstreamResponseTime; // 后端响应时间
    private String upstreamAddr; // 后端服务器地址
    private String sslProtocol;  // SSL协议版本
    private String sslCipher;    // SSL加密算法
    private Integer requestLength; // 请求长度
    private String gzipRatio;    // Gzip压缩比率
    private Long connection;     // 连接序列号
    private Integer connectionRequests; // 当前连接的请求数量
    private Long msec;           // 日志记录的时间戳（毫秒）
    private String pipe;         // 是否通过管道转发
    private String serverProtocol; // 服务器使用的协议
    private String httpHost;     // Host头部信息
    private String httpCookie;   // Cookie头部信息
    private Integer bytesSent;   // 发送给客户端的总字节数
    private String requestMethod;// 请求方法（GET, POST等）
    private String scheme;       // 使用的方案（http或https）
    private String requestUri;   // 请求的URI路径
    private String args;         // 查询字符串参数
    private String httpAcceptEncoding; // Accept-Encoding头部信息
    private String httpAcceptLanguage; // Accept-Language头部信息
    private String httpVia;      // Via头部信息
    private String httpConnection; // Connection头部信息
    private String httpCacheControl; // Cache-Control头部信息
}