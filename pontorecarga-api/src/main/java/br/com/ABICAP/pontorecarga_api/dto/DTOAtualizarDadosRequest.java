package br.com.ABICAP.pontorecarga_api.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
public class DTOAtualizarDadosRequest {

    private String usuario;
    private String email;

}
