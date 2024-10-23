package com.example.demo.integration.client;

import com.example.demo.integration.model.FakePost;
import io.quarkus.cache.CacheResult;
import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.lang.reflect.Method;
import java.util.List;


@RegisterRestClient(configKey = "fake-post-client")
public interface FakePostClient {

    @GET
    @Path("/posts")
    List<FakePost> getPosts();

    @GET
    @Path("/posts/{id}")
    // @CacheResult(cacheName = "posts-cache")
    FakePost getPostDetail(@PathParam("id") Integer id);

    @POST
    @Path("/posts")
    FakePost createPost(FakePost post);

}
