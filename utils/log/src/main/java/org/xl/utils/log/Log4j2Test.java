package org.xl.utils.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author xulei
 */
public class Log4j2Test {

    private static final Logger LOGGER = LogManager.getLogger(Log4j2Test.class);

    public static void main(String[] args) {
        LOGGER.trace("Hello trace Log4j2!");
        LOGGER.debug("Hello debug Log4j2!");
        LOGGER.info("Hello info Log4j2!");
        LOGGER.warn("Hello warn Log4j2!");
        LOGGER.error("Hello error Log4j2!");
    }
}
