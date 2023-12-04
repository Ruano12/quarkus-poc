package io.github.quarkustreinamento.quarkussocial.service;

import io.github.quarkustreinamento.quarkussocial.domain.model.PostUser;
import io.github.quarkustreinamento.quarkussocial.domain.model.User;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.FollowerRepository;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.PostRepository;
import io.github.quarkustreinamento.quarkussocial.domain.model.repository.UserRepository;
import io.quarkus.security.ForbiddenException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class PostService {

    @Inject
    private UserService userService;

    @Inject
    private PostRepository postRepository;

    @Inject
    private FollowService followService;

    public PostUser save(Long userId, String post){
        User user = userService.findById(userId);

        PostUser postUser = new PostUser();
        postUser.setUser(user);
        postUser.setText(post);

        postRepository.persist(postUser);

        return postUser;
    }

    public List<PostUser> getPostByUserId(Long userId, Long followerId){
        if(!followService.isFollow(followerId, userId)){
            throw new ForbiddenException();
        }

        return postRepository.findByUserId(userId);
    }
}
