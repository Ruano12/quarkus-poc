package io.github.quarkustreinamento.quarkussocial.rest;

import io.github.quarkustreinamento.quarkussocial.domain.model.User;
import io.github.quarkustreinamento.quarkussocial.rest.dto.CreateUserRequest;
import io.github.quarkustreinamento.quarkussocial.service.UserService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@QuarkusTest
class UserResouceTest {

    @InjectMock
    private UserService userService;



    @Test
    @DisplayName("Should create an user succesfully")
    public void createUserTest(){
        var user = new CreateUserRequest();
        user.setAge(22);
        user.setName("Fulano");

        var responseMock = new User(1L,user.getName(), user.getAge());

        Mockito.when(userService.save(any())).thenReturn(responseMock);

        Response response = given()
                .when()
                    .contentType(ContentType.JSON)
                    .body(user)
                    .post("/users")
                    .then()
                    .statusCode(201)
                    .extract().response();

        assertNotNull(response.jsonPath().getString("id"));
        assertEquals(responseMock.getId(), Long.parseLong(response.jsonPath().getString("id")));
    }

    @Test
    @DisplayName("Should list users succesfully")
    public void listUserTest(){
        List<User> users = new ArrayList<>();
        users.add(new User(1L, RandomStringUtils.random(6), Integer.parseInt(
                RandomStringUtils.random(2, false, true))));
        users.add(new User(2L, RandomStringUtils.random(6), Integer.parseInt(
                RandomStringUtils.random(2, false, true))));
        users.add(new User(3L, RandomStringUtils.random(6), Integer.parseInt(
                RandomStringUtils.random(2, false, true))));

        Mockito.when(userService.findAll()).thenReturn(users);

        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("size()", Matchers.is(3));
    }

    @Test
    @DisplayName("Should create an user bad request")
    public void createUserErrorTest(){
        var user = new CreateUserRequest();
        user.setAge(22);

        var responseMock = new User(1L,user.getName(), user.getAge());

        Mockito.when(userService.save(any())).thenReturn(responseMock);

        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(user)
                .post("/users")
                .then()
                .statusCode(400)
                .extract().response();

        List<Map<String, String>> errors = response.jsonPath().getList("violations");
        assertNotNull(errors.get(0).get("message"));
        assertEquals("Campo nome é obrigatório", errors.get(0).get("message"));
    }
}