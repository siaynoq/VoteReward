package com.tregele.bukkit.votereward;

public class ConfigurationException extends Exception {

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable e) {
        super(message, e);
    }

}
