package br.com.ABICAP.pontorecarga_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class DTOCriarContaRequest {

    @NotBlank(message = "Campo usuario nao foi preenchido")
    @Size(min = 5, max = 30)
    @Pattern(regexp = "^[A-Z].*$")
    private String usuario;

    @Email
    @NotBlank(message = "Campo email nao foi preenchido")
    @Size(max = 100, message = "Email muito longo")
    private String email;

    @NotBlank(message = "Campo senha nao foi preenchido")
    @Size(min = 8, max = 50, message = "Senha deve ter entre 8 e 50 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Senha deve ter no mínimo 8 caracteres, com letra maiúscula, letra minúscula e número")
    private String senhaHash;

    @Valid
    private DTOCriarCarroRequest carroUsuario;
}
