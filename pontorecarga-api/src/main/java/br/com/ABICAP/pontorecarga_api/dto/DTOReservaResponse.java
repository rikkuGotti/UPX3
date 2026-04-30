package br.com.ABICAP.pontorecarga_api.dto;

import br.com.ABICAP.pontorecarga_api.model.PontoRecarga;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter@Setter
@ToString
public class DTOReservaResponse {

    private Integer id;
    private Integer pontoRecargaId;
    private String pontoLocalizacao;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Integer duracaoMinutos;
    private String status;
    private String usuarioNome;
}
