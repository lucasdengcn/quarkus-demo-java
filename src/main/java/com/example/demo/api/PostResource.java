package com.example.demo.api;

import com.example.demo.annotation.APICommonResponse;
import com.example.demo.annotation.ResponseWithPost;
import com.example.demo.integration.client.FakePostClient;
import com.example.demo.integration.model.FakePost;
import com.example.demo.model.Order;
import com.example.demo.model.common.PageableOutput;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Tag(name = "Posts API")
@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@APICommonResponse
@Slf4j
public class PostResource {

    @RestClient
    private FakePostClient fakePostClient;

    @Schema
    public static class PageablePost extends PageableOutput<FakePost> {
        // just for OpenAPI specification generation.
    }

    @GET
    @Path("/v1/{id}")
    @ResponseWithPost
    public FakePost getPost(@PathParam("id") Integer id){
        return fakePostClient.getPostDetail(id);
    }


    @GET
    @Path("/v1/{size}/{index}")
    @APIResponse(responseCode = "200", description = "Successful",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageablePost.class)))
    public PageableOutput<FakePost> getPostPageable(@PathParam("size") Integer size, @PathParam("index") Integer index){
        List<FakePost> posts = fakePostClient.getPosts();
        return new PageableOutput<FakePost>(posts, size, index);
    }

}
