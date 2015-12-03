package com.jbaxenom.laget.domain.core.actions;

import hu.meza.aao.Action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author jbaxenom on 12/2/15.
 */
public abstract class SQLAction implements Action {

    private final Connection connection;
    private final Statement lastStatement;
    private final String lastQuery;
    private final boolean persistentConnection;
    private ResultSet result;

    public SQLAction(Connection connection, String sqlQuery) {
        this.connection = connection;
        this.lastQuery = sqlQuery;
        persistentConnection = false;

        try {
            this.lastStatement = this.connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR: there was a problem when creating the SQL statement. Please check " +
                    "the stacktrace above.");
        }
    }

    public SQLAction(Connection connection, String sqlQuery, boolean persistentConnection) {
        this.connection = connection;
        this.lastQuery = sqlQuery;
        this.persistentConnection = persistentConnection;

        try {
            this.lastStatement = this.connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR: there was a problem when creating the SQL statement. Please check " +
                    "the stacktrace above.");
        }
    }

    /**
     * Executes the action, which implies sending the SQL query {@code lastQuery} to the DB in the {@code endpointUrl}
     * address. The resulting data will be saved in the {@code result} attribute.
     */
    @Override
    public void execute() {
        this.result = sendQuery(this.lastQuery);
        if (!persistentConnection) {
            closeConnection();
        }
    }

    /**
     * @return true if the SQL query returned values, false otherwise
     */
    public boolean wasSuccessful() {
        try {
            return !this.result.wasNull();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public SQLAction copyOf() {
        return this;
    }

    private ResultSet sendQuery(String query) {
        try {
            return this.lastStatement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("There was an error in the SQL query (see stacktrace above)");
        }
    }

    private void closeConnection() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
