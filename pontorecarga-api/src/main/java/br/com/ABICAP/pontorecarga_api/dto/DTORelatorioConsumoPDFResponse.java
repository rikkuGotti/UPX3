package br.com.ABICAP.pontorecarga_api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter@Setter
@ToString
public class DTORelatorioConsumoPDFResponse {

    private Integer id;
    private String usuario;
    private BigDecimal consumoTotal;
    private Integer tempoDeUso;
    private Integer usos;

}
