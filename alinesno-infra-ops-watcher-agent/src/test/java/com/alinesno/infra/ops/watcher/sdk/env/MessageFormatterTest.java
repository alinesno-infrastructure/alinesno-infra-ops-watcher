package com.alinesno.infra.ops.watcher.sdk.env;

import com.alinesno.infra.ops.watcher.sdk.format.FormattingTuple;
import com.alinesno.infra.ops.watcher.sdk.format.MessageFormatter;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MessageFormatterTest {

    @Test
    public void formatWithOneArgument() {
        String messagePattern = "Hello, {}!";
        Object arg = "World";
        FormattingTuple tuple = MessageFormatter.format(messagePattern, arg);
        assertEquals("Hello, World!", tuple.getMessage());
    }

    @Test
    public void formatWithTwoArguments() {
        String messagePattern = "Today is {} and the temperature is {} degrees";
        Object arg1 = "Sunday";
        Object arg2 = 25;
        FormattingTuple tuple = MessageFormatter.format(messagePattern, arg1, arg2);
        assertEquals("Today is Sunday and the temperature is 25 degrees", tuple.getMessage());
    }

    @Test
    public void formatWithNullMessagePattern() {
        String messagePattern = null;
        Object arg = "Test";
        FormattingTuple tuple = MessageFormatter.format(messagePattern, arg);
        assertEquals(null, tuple.getMessage());
    }

    @Test
    public void formatWithNullArguments() {
        String messagePattern = "No arguments";
        FormattingTuple tuple = MessageFormatter.format(messagePattern , "");
        assertEquals("No arguments", tuple.getMessage());
    }

    @Test
    public void formatWithEscapedDelimiters() {
        String messagePattern = "This is a { test } message { with { escaped } delimiters }";
        FormattingTuple tuple = MessageFormatter.format(messagePattern , "");
        assertEquals("This is a { test } message { with { escaped } delimiters }", tuple.getMessage());
    }

    @Test
    public void formatWithThrowable() {
        String messagePattern = "An error occurred";
        Throwable throwable = new Exception("Test exception");
        FormattingTuple tuple = MessageFormatter.format(messagePattern, throwable);
        assertEquals("An error occurred", tuple.getMessage());
        assertEquals(throwable, tuple.getThrowable());
    }
}
