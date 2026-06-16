package com.testautomation.db.repository;

import com.testautomation.db.models.User;
import io.qameta.allure.Step;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Encapsulates SQL queries against the "users" table.
 *
 * JDBC concepts:
 *  - Connection: a single network connection to the database. Created with
 *    DriverManager.getConnection(url, user, password).
 *  - PreparedStatement: a SQL query with placeholders ("?"), filled in
 *    afterward. ALWAYS prefer this over building SQL strings by hand with
 *    string concatenation, which is vulnerable to SQL injection.
 *  - ResultSet: the table of rows returned by a SELECT. You call next() to
 *    move to each row, then getX(columnName) to read a value from it.
 *  - try-with-resources: Connection, PreparedStatement and ResultSet all implement
 *    AutoCloseable, so they get closed automatically even if an exception
 *    is thrown.
 */
public class UserRepository {

    private final String jdbcUrl;
    private final String user;
    private final String password;

    public UserRepository(String jdbcUrl, String user, String password) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }

    @Step("Find user by username: {0}")
    public Optional<User> findByUsername(String username) throws SQLException {
        String sql = "SELECT id, username, email, full_name, active FROM users WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    //System.out.println("Result:" + Optional.of(mapRow(resultSet)));
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    @Step("Count active users")
    public int countActiveUsers() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM users WHERE active = TRUE";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            resultSet.next();
            return resultSet.getInt("total");
        }
    }

    private User mapRow(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("full_name"),
                resultSet.getBoolean("active")
        );
    }
}
