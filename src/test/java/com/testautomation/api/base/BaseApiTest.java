package com.testautomation.api.base;

import com.testautomation.api.client.PostsApiClient;
import org.junit.jupiter.api.BeforeAll;

/**
 * Base class with shared infrastructure for API tests.
 * Lives in a separate package from "tests" because it isn't a test case
 * itself, but the "mold" that test classes inherit from.
 */
public abstract class BaseApiTest {

    protected static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    protected static PostsApiClient apiClient;

    @BeforeAll
    static void setupClient() {
        apiClient = new PostsApiClient(BASE_URL);
    }
}
