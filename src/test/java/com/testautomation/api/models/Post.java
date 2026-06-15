package com.testautomation.api.models;

import org.json.JSONObject;

import java.util.Objects;

/**
 * Represents a JSONPlaceholder "post" as a Java object.
 *
 * This is a POJO (Plain Old Java Object): a simple class, with no complex
 * business logic, whose only purpose is to model data.
 *
 * Design decisions:
 *  - Fields are `private final`: once a Post is created, it cannot be
 *    modified (immutability). If you need a "different" Post, you create a
 *    new one. This avoids bugs where one test mutates an object that
 *    another test is using.
 *  - No setters, only getters: the only way to "build" a Post is through
 *    the constructor or the static factory methods.
 */
public class Post {

    private final int id;
    private final int userId;
    private final String title;
    private final String body;

    public Post(int id, int userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    /**
     * Static factory method: converts a JSONObject (what OkHttp + org.json
     * return when reading a response) into a Post object.
     */
    public static Post fromJson(JSONObject json) {
        return new Post(
                json.getInt("id"),
                json.getInt("userId"),
                json.getString("title"),
                json.getString("body")
        );
    }

    /**
     * Static factory method: creates a NEW Post that doesn't exist
     * on the server yet (that's why it has no real id: we use 0 as a
     * placeholder, since the server will assign the real id on creation).
     *
     */
    public static Post forCreation(int userId, String title, String body) {
        return new Post(0, userId, title, body);
    }

    /**
     * Converts this Post to a JSONObject, so it can be sent as the body of
     * an HTTP request.
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("userId", userId);
        json.put("title", title);
        json.put("body", body);
        return json;
    }

    /**
     * toString(): how a Post gets printed
     */
    @Override
    public String toString() {
        return "Post{id=" + id + ", userId=" + userId
                + ", title='" + title + "', body='" + body + "'}";
    }
}
