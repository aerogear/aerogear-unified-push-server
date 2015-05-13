/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.unifiedpush.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    	AeroGearLogger.logger = LoggerFactory.getLogger(clazz);
        return SingletonHolder.instance;
    }

    public void info(String message){
        AeroGearLogger.logger.info(message);
    }

    public void warning(String message){
        AeroGearLogger.logger.warn(message);
    }

    public void severe(String message){
        AeroGearLogger.logger.error(message);
    }

    public void severe(String message, Throwable t){
        AeroGearLogger.logger.error(message, t);
    }

    public void fine(String message){
        AeroGearLogger.logger.trace("[FINE] " + message);
    }

    public void finest(String message){
        AeroGearLogger.logger.trace("[FINEST] " + message);
    }
}