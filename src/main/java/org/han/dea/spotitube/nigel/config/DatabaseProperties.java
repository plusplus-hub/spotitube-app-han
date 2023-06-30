package org.han.dea.spotitube.nigel.config;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties implements IDatabaseProperties {

    private final Properties PROPERTIES;

    public DatabaseProperties() {
        Logger LOGGER = Logger.getLogger(getClass().getName());
        PROPERTIES = new Properties();
        try {
            PROPERTIES.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Can''t access property file database.properties", e);
        }
    }

    @Override
    public String getConnectionString()
    {
        return PROPERTIES.getProperty("connectionString");
    }

}