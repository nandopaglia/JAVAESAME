package com.azienda.utils;

import org.apache.log4j.PropertyConfigurator;

public class LogConfig {

    public static void init() {
        // Log4j si aspetta un file chiamato "log4j.properties" nel classpath.
        // Qui lo carichiamo esplicitamente dalle risorse di src/main/resources.
        PropertyConfigurator.configure(LogConfig.class.getResource("/log4j.properties"));
    }
}
