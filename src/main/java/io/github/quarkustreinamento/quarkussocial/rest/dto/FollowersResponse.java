package io.github.quarkustreinamento.quarkussocial.rest.dto;

import io.github.quarkustreinamento.quarkussocial.domain.model.Follower;
import lombok.Data;

@Data
public class FollowersResponse {

    private Long id;
    private String name;

    public FollowersResponse(){}
    public FollowersResponse(Follower follower){
        this.id = follower.getFollower().getId();
        this.name = follower.getFollower().getName();
    }
}
