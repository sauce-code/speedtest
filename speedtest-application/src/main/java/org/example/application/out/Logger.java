package org.example.application.out;

public interface Logger {

    void info(Object message);

    void info(Object message, Throwable t);

    void infov(String format, Object... params);

    void warn(Object message);

    void warn(Object message, Throwable t);

    void error(Object message);

    void error(Object message, Throwable t);

}
