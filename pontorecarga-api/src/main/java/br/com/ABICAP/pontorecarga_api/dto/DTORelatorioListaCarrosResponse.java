package br.com.ABICAP.pontorecarga_api.dto;

import br.com.ABICAP.pontorecarga_api.model.TipoCarga;
import br.com.ABICAP.pontorecarga_api.model.TipoConector;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter@Setter
@ToString
public class DTORelatorioListaCarrosResponse {

    private String usuario;
    private String marca;
    private  String placa;
    private String modelo;
    private BigDecimal capacidadeBateria;
    private TipoConector tipoConector;
    private TipoCarga tipoCarga;

}
