package org.xl.utils.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xulei
 */
public class LogbackTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogbackTest.class);

    public static void main(String[] args) {
        LOGGER.trace("Hello trace Logback!");
        LOGGER.debug("Hello debug Logback!");
        LOGGER.info("Hello info Logback!");
        LOGGER.warn("Hello warn Logback!");
        LOGGER.error("Hello error Logback!");

        // 打印内部的状态
//        LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
//        StatusPrinter.print(lc);
    }
}
