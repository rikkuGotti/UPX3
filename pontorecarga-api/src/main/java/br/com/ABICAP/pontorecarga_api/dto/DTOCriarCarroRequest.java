package br.com.ABICAP.pontorecarga_api.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@ToString
public class DTOCriarCarroRequest {


    @NotBlank(message = "Campo placa precisa ser preenchido")
    @Pattern(regexp = "^[A-Z]{3}-?[0-9]{4}$|^[A-Z]{3}[0-9][A-Z][0-9]{2}$",
            message = "Placa inválida. Use formato antigo (ABC-1234) ou Mercosul (ABC1D23)")
    private String placa;

    @NotBlank(message = "Campo modelo precisa ser preenchido")
    @Size(min = 3, max = 20)
    private String model;

    @NotBlank(message = "Campo marca precisa ser preenchido")
    @Size(min = 3, max = 15)
    private String marca;

    @NotNull(message = "Campo capacidade da bateria precisa ser preenchido")
    @DecimalMin("1.00")
    private BigDecimal capacidadeBateria;

    @NotBlank(message = "Campo tipo do conector precisa ser preenchido")
    private String tipoConector;

    @NotBlank(message = "Campo tipo de carga precisa ser preenchido")
    private String tipoCarga;

}
