package br.com.ABICAP.pontorecarga_api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
@Getter@Setter
@ToString
public class DTOCarroUsuario {

    private int id;

    private String marca;

    private  String placa;

    private String modelo;

}
