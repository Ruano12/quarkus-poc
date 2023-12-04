package io.github.quarkustreinamento.quarkussocial.service;

import io.github.quarkustreinamento.quarkussocial.domain.model.Follower;
import io.github.quarkustreinamento.quarkussocial.domain.model.User;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.FollowerRepository;
import io.github.quarkustreinamento.quarkussocial.exceptions.ConflictException;
import io.github.quarkustreinamento.quarkussocial.rest.FollowResource;
import io.github.quarkustreinamento.quarkussocial.rest.dto.FollowersResponse;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
class FollowServiceTest {


    @InjectMock
    private FollowerRepository followerRepository;

    @InjectMock
    private UserService userService;

    @Inject
    private FollowService followService;

    @Test
    public void followSuccess(){
        User user = new User(1L, null, null);
        User followerUser = new User(2L, null, null);

        Mockito.when(userService.findById(1L)).thenReturn(user);
        Mockito.when(userService.findById(2L)).thenReturn(followerUser);
        Mockito.when(followerRepository.isFollow(any(), any())).thenReturn(false);

        Follower follower = new Follower();
        follower.setFollower(followerUser);
        follower.setUser(user);


        Follower followerResponse = followService.follow(follower);

        assertEquals(follower.getFollower().getId(), followerResponse.getFollower().getId());
        assertEquals(follower.getUser().getId(), followerResponse.getUser().getId());

    }

    @Test
    public void followewUserSameId(){
        User user = new User(1L, null, null);
        Follower follower = new Follower();
        follower.setFollower(user);
        follower.setUser(user);

        assertThrows(ConflictException.class, () ->  followService.follow(follower));
    }

    @Test
    public void followingTest(){
        Mockito.when(followerRepository.isFollow(any(), any())).thenReturn(true);
        boolean isFollow = followService.isFollow(1L, 2L);

        assertTrue(isFollow);
    }

    @Test
    public void notFollowingTest(){
        Mockito.when(followerRepository.isFollow(any(), any())).thenReturn(false);
        boolean isFollow = followService.isFollow(1L, 2L);

        assertFalse(isFollow);
    }

    @Test
    public void getFollows(){
        User user = new User(1L, null, null);

        Follower followerOne = new Follower();
        followerOne.setId(1L);
        followerOne.setFollower(new User(2L, RandomStringUtils.random(10), null));
        followerOne.setUser(user);

        Follower followerTwo = new Follower();
        followerTwo.setId(2L);
        followerTwo.setFollower(new User(3L, RandomStringUtils.random(10), null));
        followerTwo.setUser(user);

        List<Follower> followers = new ArrayList<>();
        followers.add(followerOne);
        followers.add(followerTwo);

        Mockito.when(followerRepository.findByUser(user.getId())).thenReturn(followers);

        List<FollowersResponse> followersResponse = followService.getFollows(user.getId());

        assertEquals(followers.size(), followersResponse.size());
    }

    @Test
    public void unfollow(){
        User user = new User(1L, null, null);
        Mockito.when(userService.findById(user.getId())).thenReturn(user);

        followService.unfollow(user.getId(), 2L);
    }

}