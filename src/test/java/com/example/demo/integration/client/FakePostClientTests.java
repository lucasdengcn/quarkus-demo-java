package com.example.demo.integration.client;

import com.example.demo.exception.RemoteAPICallException;
import com.example.demo.integration.model.FakePost;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@QuarkusTest
class FakePostClientTests {

    @RestClient
    FakePostClient fakePostClient;

    @Test
    void get_posts_successful() {
        List<FakePost> posts = fakePostClient.getPosts();
        Assertions.assertFalse(posts.isEmpty());
    }

    @Test
    void get_post_detail_successful() {
        FakePost postDetail = fakePostClient.getPostDetail(1);
        Assertions.assertNotNull(postDetail);
    }

    @Test
    void get_post_detail_404() {
        Assertions.assertThrows(RemoteAPICallException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                fakePostClient.getPostDetail(10_000);
            }
        });
    }

    @Test
    void createPost() {
    }

}