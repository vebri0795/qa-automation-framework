package com.testautomation.db.models;

/**
 * Represents a row of the "users" table. Immutable,
 * private final fields, no setters.
 */
public class User {

    private final int id;
    private final String username;
    private final String email;
    private final String fullName;
    private final boolean active;

    public User(int id, String username, String email, String fullName, boolean active) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email
                + "', fullName='" + fullName + "', active=" + active + "}";
    }
}
