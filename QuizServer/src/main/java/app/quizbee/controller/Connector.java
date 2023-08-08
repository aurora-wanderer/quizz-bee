package app.quizbee.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Connector {

    public final Connection connection
            = new ConnectionUtils(SQLConfig.username,
                    SQLConfig.password,
                    SQLConfig.database)
                    .openConnection();

    public PreparedStatement prepareStatement(String query, Object... args)
            throws SQLException {
        PreparedStatement pstmt;
        if (query.trim().startsWith("{")) {
            pstmt = connection.prepareCall(query);
        } else {
            pstmt = connection.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }

    public void executeUpdate(String query, Object... args) throws SQLException {
        PreparedStatement stmt = prepareStatement(query, args);
        stmt.executeUpdate();
    }

    public ResultSet executeQuery(String query, Object... args) {
        try {
            PreparedStatement stmt = prepareStatement(query, args);
            return stmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    protected class SQLConfig {

        public static String username = readConfigFile().getProperty("sql.username");
        public static String password = readConfigFile().getProperty("sql.password");
        public static String database = readConfigFile().getProperty("sql.database");

        private static Properties readConfigFile() {
            File cfFile = getConfigFile();

            Properties props = new Properties();

            try {
                props.load(new FileInputStream(cfFile));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Connector.class.getName())
                        .log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Connector.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            return props;
        }

        private static File getConfigFile() {
            File f = Paths.get(System.getProperty("user.dir"))
                    .toFile();
            File[] listFiles = new File[1];
            do {
                listFiles = f.listFiles((File dir, String name) -> {
                    return name.equalsIgnoreCase("config.txt");
                });
                f = f.getParentFile();
                if (listFiles.length > 0) {
                    break;
                }
            } while (listFiles.length == 0);
            return listFiles[0];
        }
    }
}
