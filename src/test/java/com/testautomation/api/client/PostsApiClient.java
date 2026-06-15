package com.testautomation.api.client;

import com.testautomation.api.models.Post;
import io.qameta.allure.Step;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class PostsApiClient {

    private final OkHttpClient client;
    private final String baseUrl;

    public PostsApiClient(String baseUrl) {
        this.client = new OkHttpClient();
        this.baseUrl = baseUrl;
    }

    @Step("Send GET to /posts/{0}")
    public Response getPost(int postId) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "/posts/" + postId)
                .get()
                .build();

        return client.newCall(request).execute();
    }

    @Step("Send POST to /posts with post: {0}")
    public Response createPost(Post post) throws IOException {
        RequestBody body = RequestBody.create(
                post.toJson().toString(),
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(baseUrl + "/posts")
                .post(body)
                .build();

        return client.newCall(request).execute();
    }

    @Step("Map the JSON response to a Post object")
    public Post parsePost(Response response) throws IOException {
        JSONObject json = new JSONObject(response.body().string());
        return Post.fromJson(json);
    }
}
