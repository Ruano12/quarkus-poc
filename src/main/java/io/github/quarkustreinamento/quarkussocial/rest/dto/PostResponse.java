package io.github.quarkustreinamento.quarkussocial.rest.dto;

import io.github.quarkustreinamento.quarkussocial.domain.model.PostUser;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {

    private String text;
    private LocalDateTime time;

    public static PostResponse fromEntity(PostUser postUser){
        PostResponse postResponse = new PostResponse();
        postResponse.setText(postUser.getText());
        postResponse.setTime(postUser.getDateTime());

        return postResponse;
    }
}
