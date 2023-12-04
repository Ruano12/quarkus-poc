package io.github.quarkustreinamento.quarkussocial.rest;

import io.github.quarkustreinamento.quarkussocial.domain.model.PostUser;
import io.github.quarkustreinamento.quarkussocial.domain.model.User;
import io.github.quarkustreinamento.quarkussocial.rest.dto.CreatePostRequest;
import io.github.quarkustreinamento.quarkussocial.rest.dto.CreateUserRequest;
import io.github.quarkustreinamento.quarkussocial.service.PostService;
import io.github.quarkustreinamento.quarkussocial.service.UserService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
class PostResourceTest {

    @InjectMock
    private PostService postService;

    @Test
    public void createPostTest(){
        PostUser post = new PostUser();
        post.setText(RandomStringUtils.random(20));
        post.setUser(new User(1L,
                RandomStringUtils.random(20, true, false),
                Integer.parseInt(RandomStringUtils.random(2, false, true))));

        Mockito.when(postService.save(post.getId(), post.getText())).thenReturn(post);

        CreatePostRequest postRequest = new CreatePostRequest();
        postRequest.setText(post.getText());


        given()
                .contentType(ContentType.JSON)
                .body(postRequest)
                .pathParams("userId", post.getUser().getId())
                .when().post()
                .then()
                .statusCode(201);
    }
}