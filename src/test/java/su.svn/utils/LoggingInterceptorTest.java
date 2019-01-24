package su.svn.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoggingInterceptorTest
{
    private TestAppender appender;

    @Before
    public void setUp() {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
        appender = (TestAppender) config.getAppenders().get("TestAppender");
    }

    @Test
    public void test_wrapping() {
        // helperComponent.doStuff("437");
        assertEquals(appender.getMessages().size(), 2);
    }
}
