package org.jboss.aerogear.unifiedpush.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for Logging on UPS and prevent log forgery
 * This class could be easily replaced by Log4j or SLF4J or any other logging framework
 * The motivation behind this implementation is to fix a security issue while
 * <a href="https://issues.jboss.org/browse/AGPUSH-1086">AGPUSH-1086</a> is not solved.
 */
public class AeroGearLogger {

    private static Logger logger;

    private AeroGearLogger() {
    }

    private final static class SingletonHolder {
        private final static AeroGearLogger instance = new AeroGearLogger();
    }

    public static AeroGearLogger getInstance(Class clazz) {
        AeroGearLogger.logger = Logger.getLogger(clazz.getSimpleName());
        return SingletonHolder.instance;
    }

    public void info(String message){
        AeroGearLogger.logger.info(format(message));
    }

    public void warning(String message){
        AeroGearLogger.logger.log(Level.WARNING, format(message));
    }

    public void severe(String message){
        AeroGearLogger.logger.log(Level.SEVERE, format(message));
    }

    public void severe(String message, Throwable t){
        AeroGearLogger.logger.log(Level.SEVERE, format(message), t);
    }

    public void fine(String message){
        AeroGearLogger.logger.log(Level.FINE, format(message));
    }

    public void finest(String message){
        AeroGearLogger.logger.log(Level.FINEST, format(message));
    }

    /**
     * Taken with some modifications from Log4j
     * @see <a href="https://github.com/apache/logging-log4j2/blob/master/log4j-core/src/main/java/org/apache/logging/log4j/core/pattern/EncodingPatternConverter.java">logging-log4j2</a>
     * @param logMessage
     * @return Encoded string
     */
    private String format(final String logMessage) {
        final StringBuilder message = new StringBuilder(logMessage);
        final StringBuilder str = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            final char c = message.charAt(i);
            switch (c) {
                case '\r':
                    str.append("\\r");
                    break;
                case '\n':
                    str.append("\\n");
                    break;
                case '&':
                    str.append("&amp;");
                    break;
                case '<':
                    str.append("&lt;");
                    break;
                case '>':
                    str.append("&gt;");
                    break;
                case '"':
                    str.append("&quot;");
                    break;
                case '\'':
                    str.append("&apos;");
                    break;
                case '/':
                    str.append("&#x2F;");
                    break;
                default:
                    str.append(c);
                    break;
            }
        }
        return str.toString();
    }
}