package io.github.quarkustreinamento.quarkussocial.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePostRequest {

    @NotBlank(message = "Texto do post é obrigatorio")
    private String text;
}
