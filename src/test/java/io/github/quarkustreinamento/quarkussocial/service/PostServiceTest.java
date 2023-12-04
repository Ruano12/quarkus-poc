package io.github.quarkustreinamento.quarkussocial.service;

import io.github.quarkustreinamento.quarkussocial.domain.model.PostUser;
import io.github.quarkustreinamento.quarkussocial.domain.model.User;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.PostRepository;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.UserRepository;
import io.quarkus.security.ForbiddenException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
class PostServiceTest {

    @InjectMock
    private UserService userService;

    @InjectMock
    private PostRepository postRepository;

    @InjectMock
    private FollowService followService;

    @Inject
    private PostService postService;

    private User user;

    @BeforeEach
    public void setup(){
        user = new User(1L,"Fulano", 22);
    }

    @Test
    public void createPostSucess(){
        Mockito.when(userService.findById(user.getId())).thenReturn(user);

        PostUser postResponse =
                postService.save(user.getId(), RandomStringUtils.random(30));

        assertEquals(user.getId(), postResponse.getUser().getId());
    }

    @Test
    public void listPostSuccess(){
        Mockito.when(followService.isFollow(any(), any())).thenReturn(true);
        PostUser post = new PostUser();
        PostUser post2 = new PostUser();
        post.setUser(user);
        post2.setUser(user);

        post.setText(RandomStringUtils.random(20));
        post2.setText(RandomStringUtils.random(20));

        List<PostUser> posts = new ArrayList<>();
        posts.add(post);
        posts.add(post2);

        Mockito.when(postRepository.findByUserId(any())).thenReturn(posts);

        List postsRespose = postService.getPostByUserId(1L, 2L);

        assertEquals(posts.size(), postsRespose.size());
    }

    @Test
    public void listPostNotFollow(){
        Mockito.when(followService.isFollow(any(), any())).thenReturn(false);

        assertThrows(ForbiddenException.class, () -> postService.getPostByUserId(1L, 2L));
    }
}