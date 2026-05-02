package br.com.ABICAP.pontorecarga_api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class DTOReservaRequest {

    @NotNull(message = "Ponto de recarga é obrigatório")
    private Integer pontoRecargaId;

    @NotNull(message = "Horário de início é obrigatório")
    private LocalDateTime inicio;

    @NotNull(message = "Horário de fim é obrigatório")
    private LocalDateTime fim;
}