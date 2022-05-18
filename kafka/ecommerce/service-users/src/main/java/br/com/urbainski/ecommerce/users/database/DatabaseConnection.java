package br.com.urbainski.ecommerce.users.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static DatabaseConnection INSTANCE;
    private final Connection connection;

    private DatabaseConnection() {
        this.connection = createConnection();
    }

    private Connection createConnection() {

        var url = "jdbc:sqlite:target/users_database.db";

        try {
            var connection = DriverManager.getConnection(url);

            migrateDatabaseToVersion1(connection);

            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void migrateDatabaseToVersion1(Connection connection) throws SQLException {

        var sql = "create table if not exists Users (uuid varchar(200) primary key, email varchar(200) not null)";

        connection.createStatement().execute(sql);
    }

    public Connection getConnection() {
        return connection;
    }

    public static DatabaseConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseConnection();
        }
        return INSTANCE;
    }

}
