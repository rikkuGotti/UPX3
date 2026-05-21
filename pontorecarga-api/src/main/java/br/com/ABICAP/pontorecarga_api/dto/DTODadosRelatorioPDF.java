package br.com.ABICAP.pontorecarga_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter@Setter

@ToString
public class DTODadosRelatorioPDF {

    private String postoMaisUsado;
    private List<DTORelatorioConsumoPDFResponse> infoUsuarios;
    private double gastoMedioEnergia;
}
