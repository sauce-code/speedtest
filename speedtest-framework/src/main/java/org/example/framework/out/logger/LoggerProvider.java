package org.example.framework.out.logger;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import org.example.application.out.Logger;

public class LoggerProvider {

    @Dependent
    @Produces
    public Logger logger(InjectionPoint injectionPoint) {
        var clazz = injectionPoint.getMember().getDeclaringClass();
        var logger = org.jboss.logging.Logger.getLogger(clazz);
        return new LoggerImpl(logger);
    }

}
