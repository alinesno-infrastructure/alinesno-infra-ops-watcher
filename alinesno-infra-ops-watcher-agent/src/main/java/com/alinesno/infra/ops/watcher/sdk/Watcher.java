package com.alinesno.infra.ops.watcher.sdk;

/**
 * 观察者接口，定义了不同级别消息的通知方法。
 * 用于接收并处理不同紧急程度的消息，提供了一套方法来区分消息的紧急程度和处理方式。
 */
public interface Watcher {

    /**
     * 通知观察者处理低级别的消息。
     *
     * @param var1 消息内容
     */
    void low(String var1);

    /**
     * 通知观察者处理低级别的消息，附带额外信息。
     *
     * @param var1 消息内容
     * @param var2 额外信息
     */
    void low(String var1, Object var2);

    /**
     * 通知观察者处理低级别的消息，附带两个额外信息。
     *
     * @param var1 消息内容
     * @param var2 第一个额外信息
     * @param var3 第二个额外信息
     */
    void low(String var1, Object var2, Object var3);

    /**
     * 通知观察者处理低级别的消息，附带可变数量的额外信息。
     *
     * @param var1 消息内容
     * @param var2 额外信息数组
     */
    void low(String var1, Object... var2);

    /**
     * 通知观察者处理低级别的异常消息。
     *
     * @param var1 消息内容
     * @param var2 异常对象
     */
    void low(String var1, Throwable var2);

    /**
     * 通知观察者处理普通级别的消息。
     *
     * @param var1 消息内容
     */
    void normal(String var1);

    /**
     * 通知观察者处理普通级别的消息，附带额外信息。
     *
     * @param var1 消息内容
     * @param var2 额外信息
     */
    void normal(String var1, Object var2);

    /**
     * 通知观察者处理普通级别的消息，附带两个额外信息。
     *
     * @param var1 消息内容
     * @param var2 第一个额外信息
     * @param var3 第二个额外信息
     */
    void normal(String var1, Object var2, Object var3);

    /**
     * 通知观察者处理普通级别的消息，附带可变数量的额外信息。
     *
     * @param var1 消息内容
     * @param var2 额外信息数组
     */
    void normal(String var1, Object... var2);

    /**
     * 通知观察者处理普通级别的异常消息。
     *
     * @param var1 消息内容
     * @param var2 异常对象
     */
    void normal(String var1, Throwable var2);

    /**
     * 通知观察者处理重要的消息。
     *
     * @param var1 消息内容
     */
    void important(String var1);

    /**
     * 通知观察者处理重要的消息，附带额外信息。
     *
     * @param var1 消息内容
     * @param var2 额外信息
     */
    void important(String var1, Object var2);

    /**
     * 通知观察者处理重要的消息，附带两个额外信息。
     *
     * @param var1 消息内容
     * @param var2 第一个额外信息
     * @param var3 第二个额外信息
     */
    void important(String var1, Object var2, Object var3);

    /**
     * 通知观察者处理重要的消息，附带可变数量的额外信息。
     *
     * @param var1 消息内容
     * @param var2 额外信息数组
     */
    void important(String var1, Object... var2);

    /**
     * 通知观察者处理重要的异常消息。
     *
     * @param var1 消息内容
     * @param var2 异常对象
     */
    void important(String var1, Throwable var2);

    /**
     * 记录紧急级别的日志消息。
     * 此方法用于记录紧急情况下的日志信息，无需提供额外数据。
     * @param var1 日志消息字符串。
     */
    void emergency(String var1);

    /**
     * 记录紧急级别的日志消息，伴随一个对象。
     * 此方法用于记录紧急情况下的日志信息，同时包括一个辅助对象以提供更多的上下文信息。
     * @param var1 日志消息字符串。
     * @param var2 一个辅助对象，用于提供额外的上下文信息。
     */
    void emergency(String var1, Object var2);

    /**
     * 记录紧急级别的日志消息，伴随可变数量的对象。
     * 此方法用于记录紧急情况下的日志信息，允许提供任意数量的辅助对象以提供更多的上下文信息。
     * @param var1 日志消息字符串。
     * @param var2 一个或多个辅助对象，用于提供额外的上下文信息。
     */
    void emergency(String var1, Object... var2);

    /**
     * 记录紧急级别的日志消息，伴随两个对象。
     * 此方法用于记录紧急情况下的日志信息，同时包括两个辅助对象以提供更多的上下文信息。
     * @param var1 日志消息字符串。
     * @param var2 第一个辅助对象，用于提供额外的上下文信息。
     * @param var3 第二个辅助对象，用于提供额外的上下文信息。
     */
    void emergency(String var1, Object var2, Object var3);

    /**
     * 记录紧急级别的日志消息，伴随一个异常。
     * 此方法用于记录紧急情况下的日志信息，同时包括一个异常对象，用于提供关于错误的详细信息。
     * @param var1 日志消息字符串。
     * @param var2 一个异常对象，包含关于发生的错误的详细信息。
     */
    void emergency(String var1, Throwable var2);

    /**
     * 记录关键级别的日志消息。
     * 此方法用于记录关键情况下的日志信息，无需提供额外数据。
     * @param var1 日志消息字符串。
     */
    void critical(String var1);

    /**
     * 记录关键级别的日志消息，伴随一个对象。
     * 此方法用于记录关键情况下的日志信息，同时包括一个辅助对象以提供更多的上下文信息。
     * @param var1 日志消息字符串。
     * @param var2 一个辅助对象，用于提供额外的上下文信息。
     */
    void critical(String var1, Object var2);

    /**
     * 记录关键级别的日志消息，伴随两个对象。
     * 此方法用于记录关键情况下的日志信息，同时包括两个辅助对象以提供更多的上下文信息。
     * @param var1 日志消息字符串。
     * @param var2 第一个辅助对象，用于提供额外的上下文信息。
     * @param var3 第二个辅助对象，用于提供额外的上下文信息。
     */
    void critical(String var1, Object var2, Object var3);

    /**
     * 记录一个关键级别的消息，该消息包含一个字符串参数和可变数量的附加参数。
     * 这些消息通常用于标记应用程序中的严重问题，这些问题需要立即解决。
     *
     * @param var1 一个描述问题的字符串，它可以提供关于问题的上下文信息。
     * @param var2 可变参数，允许传递额外的信息到日志记录器，这些信息可以是任何类型的对象。
     */
    void critical(String var1, Object... var2);

    /**
     * 记录一个关键级别的消息，该消息包含一个字符串参数和一个异常对象。
     * 这种方法特别用于记录导致异常的情况，异常对象提供了关于错误的额外详细信息。
     *
     * @param var1 一个描述问题的字符串，它可以提供关于问题的上下文信息。
     * @param var2 一个 Throwable 对象，它包含了异常的详细信息。这可以帮助
     */
    void critical(String var1, Throwable var2);

}
