package br.com.ABICAP.pontorecarga_api.dto;

import br.com.ABICAP.pontorecarga_api.model.Usuario;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter@Setter
@ToString
public class DTORelatorioConsumoResponse {

    private String usuario;
    private String nomePonto;
    private BigDecimal consumoTotal;
    private Integer usos;
}
