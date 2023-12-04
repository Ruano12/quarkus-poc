package io.github.quarkustreinamento.quarkussocial.service;

import io.github.quarkustreinamento.quarkussocial.domain.model.User;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.UserRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
class UserServiceTest {

    @InjectMock
    private UserRepository repository;

    @Inject
    private UserService userService;

    @Test
    @DisplayName("Should create an user succesfully")
    public void createUserTest(){
        User user = new User(1L,"Fulano", 22);

        User userResponse = userService.save(user);

        assertNotNull(userResponse.getId());
        assertEquals(user.getId(), userResponse.getId());
    }

    @Test
    public void getInexistUserId(){
        Mockito.when(repository.findById(any())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> userService.findById(1L));
    }
}