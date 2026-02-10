package org.example.framework.out.logger;

import org.example.application.out.Logger;

public class LoggerImpl implements Logger {

    private final org.jboss.logging.Logger logger;

    public LoggerImpl(org.jboss.logging.Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(Object message) {
        logger.info(message);
    }

    @Override
    public void info(Object message, Throwable t) {
        logger.info(message, t);
    }

    @Override
    public void infov(String format, Object... params) {
        logger.infov(format, params);
    }

    @Override
    public void warn(Object message) {
        logger.warn(message);
    }

    @Override
    public void warn(Object message, Throwable t) {
        logger.warn(message, t);
    }

    @Override
    public void error(Object message) {
        logger.error(message);
    }

    @Override
    public void error(Object message, Throwable t) {
        logger.error(message, t);
    }

}
