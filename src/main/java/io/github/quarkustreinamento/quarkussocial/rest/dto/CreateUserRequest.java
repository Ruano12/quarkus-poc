package io.github.quarkustreinamento.quarkussocial.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CreateUserRequest {

    @NotBlank(message = "Campo nome é obrigatório")
    private String name;

    @NotNull(message = "Campo idade é obrigatório")
    private Integer age;
}

