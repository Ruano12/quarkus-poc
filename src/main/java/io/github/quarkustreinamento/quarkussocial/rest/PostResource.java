package io.github.quarkustreinamento.quarkussocial.rest;

import io.github.quarkustreinamento.quarkussocial.domain.model.PostUser;
import io.github.quarkustreinamento.quarkussocial.domain.model.User;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.FollowerRepository;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.PostRepository;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.UserRepository;
import io.github.quarkustreinamento.quarkussocial.rest.dto.CreatePostRequest;
import io.github.quarkustreinamento.quarkussocial.rest.dto.PostResponse;
import io.github.quarkustreinamento.quarkussocial.service.PostService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/users/{userId}/posts")
@Transactional
public class PostResource {

    @Inject
    private UserRepository userRepository;

    @Inject
    private PostService postService;

    @Inject
    private FollowerRepository followerRepository;


    @POST
    public Response post(@PathParam("userId") Long userId, @Valid CreatePostRequest postRequest){
        PostUser postUser = postService.save(userId, postRequest.getText());

        return Response.status(Response.Status.CREATED).entity(postUser).build();
    }

    @GET
    public Response listPost(@PathParam("userId") Long userId, @HeaderParam("followerId") Long followerId){
        List<PostUser> posts = postService.getPostByUserId(userId, followerId);
        List<PostResponse> response = posts
                .stream()
                .map(PostResponse::fromEntity)
                .collect(Collectors.toList());

        return Response.ok(response).build();
    }
}
