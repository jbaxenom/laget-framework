package com.jbaxenom.laget.domain.core.actors;

import com.jbaxenom.laget.configuration.Configuration;
import hu.meza.aao.Actor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author jbaxenom on 12/1/15.
 */
public abstract class SQLActor extends Actor {

    private String driver;
    private String url;
    private String username;
    private String password;
    private Connection connection;
    private String lastQuery;

    public SQLActor() {
        this.driver = Configuration.sqlDriver.get();
        this.url = Configuration.sqlDatabaseUrl.get();
        this.username = Configuration.sqlUsername.get();
        this.password = Configuration.sqlPassword.get();

        try {
            Class.forName(this.driver);
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public String driver() {
        return driver;
    }

    public SQLActor withDriver(String driver) {
        this.driver = driver;
        return this;
    }

    public String dBUrl() {
        return url;
    }

    public SQLActor withDBUrl(String url) {
        this.url = url;
        return this;
    }

    public String username() {
        return username;
    }

    public SQLActor withUsername(String username) {
        this.username = username;
        return this;
    }

    public String password() {
        return password;
    }

    public SQLActor setPassword(String password) {
        this.password = password;
        return this;
    }

    protected void closeConnection() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

}
