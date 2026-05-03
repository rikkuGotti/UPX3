package br.com.ABICAP.pontorecarga_api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
public class DTOCarroUsuarioResponse {

    private int id;

    private String marca;

    private  String placa;

    private String modelo;

}
