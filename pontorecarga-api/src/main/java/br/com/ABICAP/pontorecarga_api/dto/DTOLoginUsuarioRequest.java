package br.com.ABICAP.pontorecarga_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class DTOLoginUsuarioRequest {

    @NotBlank(message = "Digite o seu usuario")
    private String login;

    @NotBlank(message = "Senha é obrigatória para login")
    @Size(min = 6, max = 50, message = "Senha inválida")
    private String senha;

}
