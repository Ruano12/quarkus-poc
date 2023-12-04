package io.github.quarkustreinamento.quarkussocial.rest;

import io.github.quarkustreinamento.quarkussocial.domain.model.Follower;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.UserRepository;
import io.github.quarkustreinamento.quarkussocial.rest.dto.FollowRequest;
import io.github.quarkustreinamento.quarkussocial.rest.dto.FollowersResponse;
import io.github.quarkustreinamento.quarkussocial.service.FollowService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@TestHTTPEndpoint(FollowResource.class)
class FollowResourceTest {

    @InjectMock
    private FollowService followService;

    @InjectMock
    private UserRepository userRepository;

    @Test
    public void followSuccess(){
        FollowRequest follow = new FollowRequest();
        follow.setFollowerId(2L);

        given()
                .contentType(ContentType.JSON)
                .body(follow)
                .pathParams("userId", 1L)
                .when().put()
                .then()
                .statusCode(204);
    }

    @Test
    public void listSuccess(){
        List<FollowersResponse> followers = new ArrayList<>();
        FollowersResponse followerOne = new FollowersResponse();
        FollowersResponse followerTwo = new FollowersResponse();

        followerOne.setId(1L);
        followerOne.setName(RandomStringUtils.random(10));
        followers.add(followerOne);

        followerTwo.setId(2L);
        followerTwo.setName(RandomStringUtils.random(10));
        followers.add(followerTwo);

        Mockito.when(followService.getFollows(any())).thenReturn(followers);

        given()
                .contentType(ContentType.JSON)
                .pathParams("userId", 1L)
                .when().get()
                .then()
                .statusCode(200)
                .body("size()", Matchers.is(2));
    }

    @Test
    public void unfollowTest(){
        given()
                .contentType(ContentType.JSON)
                .pathParams("userId", 1L)
                .when().delete()
                .then()
                .statusCode(204);
    }

}