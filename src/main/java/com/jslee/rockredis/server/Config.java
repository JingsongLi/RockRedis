package com.jslee.rockredis.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * config for server
 * Created by jslee on 2016/9/29.
 */
public class Config {

    public static final String DB_PATH = "db.path";
    public static final String DB_NAME = "db.name";
    public static final String SERVER_PORT = "server.port";

    private static Map<String, String> conf = new ConcurrentHashMap<>();

    public static void load() throws IOException {
        InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        properties.load(in);
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            conf.put((String) entry.getKey(), (String) entry.getValue());
        }
    }

    public static boolean getBoolean(String key) throws ConfigMissingException {
        if (conf.containsKey(key)) {
            return Boolean.valueOf(conf.get(key));
        } else {
            throw new ConfigMissingException(key);
        }
    }

    public static String get(String key) throws ConfigMissingException {
        if (conf.containsKey(key)) {
            return conf.get(key);
        } else {
            throw new ConfigMissingException(key);
        }
    }

    public static int getInt(String key) throws ConfigMissingException {
        if (conf.containsKey(key)) {
            return Integer.valueOf(conf.get(key));
        } else {
            throw new ConfigMissingException(key);
        }
    }

    public static long getLong(String key) throws ConfigMissingException {
        if (conf.containsKey(key)) {
            return Long.valueOf(conf.get(key));
        } else {
            throw new ConfigMissingException(key);
        }
    }

    public static double getDouble(String key) throws ConfigMissingException {
        if (conf.containsKey(key)) {
            return Double.valueOf(conf.get(key));
        } else {
            throw new ConfigMissingException(key);
        }
    }

    public static String get(String key, String defaultValue) {
        return conf.containsKey(key) ? conf.get(key) : defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        return conf.containsKey(key) ? Integer.valueOf(conf.get(key)) : defaultValue;
    }

    public static long getLong(String key, long defaultValue) {
        return conf.containsKey(key) ? Long.valueOf(conf.get(key)) : defaultValue;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return conf.containsKey(key) ? Boolean.valueOf(conf.get(key)) : defaultValue;
    }

    public static double getDouble(String key, double defaultValue) {
        return conf.containsKey(key) ? Double.valueOf(conf.get(key)) : defaultValue;
    }

    public static class ConfigMissingException extends IOException {
        public ConfigMissingException(String cause) {
            super(cause);
        }
    }
}
