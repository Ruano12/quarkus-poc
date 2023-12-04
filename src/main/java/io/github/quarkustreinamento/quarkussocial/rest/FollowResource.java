package io.github.quarkustreinamento.quarkussocial.rest;

import io.github.quarkustreinamento.quarkussocial.domain.model.Follower;
import io.github.quarkustreinamento.quarkussocial.domain.model.User;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.FollowerRepository;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.UserRepository;
import io.github.quarkustreinamento.quarkussocial.rest.dto.FollowRequest;
import io.github.quarkustreinamento.quarkussocial.rest.dto.FollowersPerUserResponse;
import io.github.quarkustreinamento.quarkussocial.rest.dto.FollowersResponse;
import io.github.quarkustreinamento.quarkussocial.service.FollowService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("/users/{userId}/followers")
@Transactional
public class FollowResource {

    @Inject
    private FollowService followService;

    @Inject
    private UserRepository userRepository;


    @PUT
    public Response follow(@PathParam("userId") Long userId, @Valid FollowRequest request){
        Follower follower = new Follower();
        follower.setUser(new User(userId, null, null));
        follower.setFollower(new User(request.getFollowerId(), null, null));

        followService.follow(follower);

        return Response.status(Response.Status.NO_CONTENT).build();
    }


    @GET
    public Response followersGets(@PathParam("userId") Long userId){
        List<FollowersResponse> followersResponses= followService.getFollows(userId);

        FollowersPerUserResponse response = new FollowersPerUserResponse();
        response.setFollowersCount(followersResponses.size());
        response.setContent(followersResponses);

        return Response.ok(response).build();
    }


    @DELETE
    public Response unfollowUser(@PathParam("userId") Long userId, @QueryParam("followerId") Long followerId){
        followService.unfollow(userId, followerId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
