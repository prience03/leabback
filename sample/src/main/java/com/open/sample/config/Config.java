package com.open.sample.config;

public class Config extends ConfigBase {
    private static Config instance = new Config();

    private Config() {
    }

    public static Config getInstance() {
        return instance;
    }
}
