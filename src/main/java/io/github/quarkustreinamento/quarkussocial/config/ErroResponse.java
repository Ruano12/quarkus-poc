package io.github.quarkustreinamento.quarkussocial.config;

import lombok.Data;

@Data
public class ErroResponse {

    private String mensagem;
    private Integer code;

    public ErroResponse(String mensagem, Integer code) {
        this.mensagem = mensagem;
        this.code = code;
    }
}
