package br.com.ABICAP.pontorecarga_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter@Setter
@ToString

public class DTOReservaRequest {

    @NotNull(message = "Ponto de recarga é obrigatório")
    private Integer pontoRecargaId;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotNull(message = "Horário de início é obrigatório")
    private LocalTime horaInicio;  // Pode ser 08:53, 15:06, etc.

    @NotNull(message = "Horário de fim é obrigatório")
    private LocalTime horaFim;      // Pode ser 09:10, 15:23, etc.
}
