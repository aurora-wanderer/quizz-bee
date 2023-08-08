package app.quizbee.controller;

import java.sql.*;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ConnectionUtils {

    private static final String LOCAL_HOST_SQL = "localhost:1433";

    private static final String DEFAULT_USERNAME = "sa";

    private static final String DEFAULT_PASSWORD = "123456";

    private static final String DEFAULT_DATABASENAME = "master";

    private final String user;
    private final String pass;
    private final String databaseName;

    public ConnectionUtils() {
        this.user = ConnectionUtils.DEFAULT_USERNAME;
        this.pass = ConnectionUtils.DEFAULT_PASSWORD;
        this.databaseName = ConnectionUtils.DEFAULT_DATABASENAME;
    }

    public ConnectionUtils(String user, String pass, String databaseName) {
        this.user = user;
        this.pass = pass;
        this.databaseName = databaseName;
    }

    public Connection openConnection() {
        try {

            final String connectionURL = "jdbc:sqlserver://" + LOCAL_HOST_SQL + ';'
                    + "databaseName= " + this.databaseName + ';'
                    + "encrypt=true;"
                    + "trustServerCertificate=true";

            DriverManager.registerDriver(new SQLServerDriver());
            return DriverManager.getConnection(connectionURL, this.user, this.pass);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
