package com.github.jx4e.minecode;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Minecode implements ModInitializer {
    public static final String MOD_NAME = "Minecode";
    public static final String MOD_VER = "Beta-1.0";
    private static Minecode instance;
    private final Logger logger;

    public Minecode() {
        instance = this;
        logger = LoggerFactory.getLogger(MOD_NAME.toLowerCase() + "-logger");
    }

    @Override
    public void onInitialize() {
        logger.info("Initialising " + MOD_NAME + " " + MOD_VER);
    }

    public static Minecode getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }
}
