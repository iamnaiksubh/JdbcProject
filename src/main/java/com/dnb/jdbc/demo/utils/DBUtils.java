package com.dnb.jdbc.demo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class DBUtils {

    private static Properties getProperties() {
        //accessing the class and converting it to stream
        InputStream inputStream = DBUtils.class.getClassLoader().getResourceAsStream("application.properties");

        try {
            // if application file is not there, then don't create the object
            if (inputStream != null) {
                Properties properties = new Properties();
                properties.load(inputStream);
                return properties;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Optional<Connection> getConnection() {
        Properties properties = getProperties();
        try {
            Connection connection = DriverManager.getConnection(properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
            return Optional.of(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
