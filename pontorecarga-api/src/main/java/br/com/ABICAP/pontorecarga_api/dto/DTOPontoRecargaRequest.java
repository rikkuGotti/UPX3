package br.com.ABICAP.pontorecarga_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter@Setter
@ToString
public class DTOPontoRecargaRequest {

    @NotBlank(message = "Código do patrimônio é obrigatório")
    private String codigoPatrimonio;

    @NotBlank(message = "Fabricante é obrigatório")
    private String fabricante;

    @NotBlank(message = "Modelo é obrigatório")
    private String modelo;

    // CARACTERÍSTICAS ELÉTRICAS
    @NotNull(message = "Tipo de carga é obrigatório")
    private String tipoCarga;

    @NotNull(message = "Potência máxima é obrigatória")
    @Min(value = 1, message = "Potência deve ser maior que 0")
    private Integer potenciaMaximaKW;

    private String tensaoNominal; // 220V ou 380V, padrao 220V se nao informado
    private Integer correnteMaximaAmpere; // O sistema calcula se tiver potencia e tensao

    // CONECTORES
    @NotBlank(message = "Tipo de conector é obrigatório")
    private String tipoConector;

    @NotNull(message = "Quantidade de conectores é obrigatório")
    private Integer quantidadeConectores;

    // COMUNICAÇÃO
    private String protocoloComunicacao;
    private String statusConexao; // Ex: "ONLINE", "OFFLINE" Definido pelo sistema, padrao offline na criacao

    // STATUS OPERACIONAL
    private String statusPonto; // Ex: "DISPONIVEL", "OCUPADO" Definido pelo sistema, padrao disponivel
    private String statusCabo; // Ex: Talvez nao tenha

    // MEDIÇÃO
    private BigDecimal medidorKWh; // Talvez nao tenha

    // LOCALIZAÇÃO
    @NotBlank(message = "Localização é obrigatória")
    private String localizacao;
}
