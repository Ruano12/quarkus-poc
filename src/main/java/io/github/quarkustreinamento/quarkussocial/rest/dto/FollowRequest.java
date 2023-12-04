package io.github.quarkustreinamento.quarkussocial.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FollowRequest {

    @NotNull(message = "follower né obrigatorio")
    private Long followerId;
}
