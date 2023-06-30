package org.han.dea.spotitube.nigel.config;

import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Default
public class ConnectionFactory implements IConnectionFactory {

    @Getter
    private String connectionString;

    @Inject
    public ConnectionFactory(DatabaseProperties properties) {
        connectionString = properties.getConnectionString();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString);
    }
}