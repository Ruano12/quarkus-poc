package io.github.quarkustreinamento.quarkussocial.service;

import io.github.quarkustreinamento.quarkussocial.domain.model.Follower;
import io.github.quarkustreinamento.quarkussocial.domain.model.User;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.FollowerRepository;
import io.github.quarkustreinamento.quarkussocial.exceptions.ConflictException;
import io.github.quarkustreinamento.quarkussocial.rest.dto.FollowersResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FollowService {

    @Inject
    private FollowerRepository followerRepository;

    @Inject
    private UserService userService;

    public Follower follow(Follower follower){
        if(follower.getUser().getId().equals(follower.getFollower().getId())){
            throw new ConflictException("Não é possivel usuario seguir ele mesmo.");
        }

        User user = userService.findById(follower.getUser().getId());
        User followerUser = userService.findById(follower.getFollower().getId());

        if(!followerRepository.isFollow(follower.getFollower().getId(), follower.getUser().getId())){
            followerRepository.persist(follower);
        }

        return follower;
    }

    public List<FollowersResponse> getFollows(Long userId){
        List<Follower> followers = followerRepository.findByUser(userId);

        List<FollowersResponse> followersResponses= followers.stream()
                .map(FollowersResponse::new)
                .collect(Collectors.toList());

        return followersResponses;
    }

    public void unfollow(Long userId, Long followerId){
        User user = userService.findById(userId);
        followerRepository.deleteByUserAndFollower(followerId, userId);
    }

    public boolean isFollow(Long followerId, Long userId){
        return followerRepository.isFollow(followerId, userId);
    }
}
