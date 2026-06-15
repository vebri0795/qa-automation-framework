package com.testautomation.api.tests;

import com.testautomation.api.base.BaseApiTest;
import com.testautomation.api.models.Post;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import okhttp3.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("User Data Platform")
@Feature("Posts API")
class AllureModelBasedApiTest extends BaseApiTest {

    @ParameterizedTest(name = "Verifies that GET /posts/{0} returns 200")
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("Verifies that GET /posts/{id} returns 200 for several ids")
    @Story("Get a post by id")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that GET /posts/{id} returns 200 for several ids.")
    void getSinglePost_mapsToPostObject(int postId) throws IOException {
        try (Response response = apiClient.getPost(postId)) {
            assertEquals(200, response.code());

            Post post = apiClient.parsePost(response);

            assertEquals(postId, post.getId());
            assertEquals(1, post.getUserId()); // ids 1-5 belong to userId 1
            assertNotEquals("", post.getTitle().trim());
            assertNotEquals("", post.getBody().trim());
        }
    }

    @ParameterizedTest(name = "Verifies that POST /posts returns 201 for: {0}")
    @MethodSource("postsToCreate")
    @DisplayName("Verifies that POST /posts returns 201 for several posts")
    @Story("Create a new post")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that POST /posts returns 201 for several posts.")
    void createPost_mapsResponseToPostObject(Post newPost) throws IOException {
        try (Response response = apiClient.createPost(newPost)) {
            assertEquals(201, response.code());

            Post createdPost = apiClient.parsePost(response);

            assertEquals(newPost.getTitle(), createdPost.getTitle());
            assertEquals(newPost.getBody(), createdPost.getBody());
            assertEquals(newPost.getUserId(), createdPost.getUserId());
            assertNotEquals(newPost.getId(), createdPost.getId());
            assertTrue(createdPost.getId() > 0);
        }
    }

    static Stream<Post> postsToCreate() {
        return Stream.of(
                Post.forCreation(1, "Learning OOP with Posts", "Test body"),
                Post.forCreation(2, "Second post for parameterized Allure test", "Another different body"),
                Post.forCreation(1, "Third post: accents áéíóú ñ ¿?", "Body with accents")
        );
    }
}