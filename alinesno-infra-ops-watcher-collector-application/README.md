# 日志数据采集器

提供对应的接口用于采集日志数据。

## 日志采集接口及方式

OpenTelemetry SDK采集:

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

巡检日志采集:

```shell
curl -X PUT 'http://localhost:5316/api/v2/inspectData' \
     -H 'User-Key: YOUR_API_KEY' \
     -F 'pluginFile=@path/to/your/file.json' \
     -F 'algorithm={"name":"ProjectName","runStatus":"SUCCESS","nameCode":"PROJECT123","totalTime":"00:10:05","busType":"TYPE"}'
```