package com.testautomation.db.tests;

import com.testautomation.db.models.User;
import com.testautomation.db.repository.UserRepository;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Requires the MySQL container from docker-compose.yml to be running
 * (docker compose up -d) before these tests, since they connect to
 * localhost:3306.
 */
@Epic("User Data Platform")
@Feature("Users Database")
class UserRepositoryTest {

    private static UserRepository userRepository;

    @BeforeAll
    static void setup() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/testdb";
        userRepository = new UserRepository(jdbcUrl, "testuser", "testpass");
    }

    @Test
    @Story("Find a user by username")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that an existing username returns the matching user data.")
    void findByUsername_existingUser_returnsUser() throws SQLException {
        Optional<User> result = userRepository.findByUsername("jdoe");

        assertTrue(result.isPresent());

        User user = result.get();
        assertEquals("jdoe", user.getUsername());
        assertEquals("jdoe@example.com", user.getEmail());
        assertEquals("John Doe", user.getFullName());
        assertTrue(user.isActive());
    }

    @Test
    @Story("Find a user by username")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that a non-existent username returns an empty result.")
    void findByUsername_nonExistentUser_returnsEmpty() throws SQLException {
        Optional<User> result = userRepository.findByUsername("does_not_exist");

        assertFalse(result.isPresent());
    }

    @Test
    @Story("Count active users")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that the active user count matches the seed data.")
    void countActiveUsers_matchesSeedData() throws SQLException {
        // Seed data (db/init.sql) has 2 active users (jdoe, asmith) and
        // 1 inactive (bwhite).
        int activeUsers = userRepository.countActiveUsers();

        assertEquals(2, activeUsers);
    }
}
