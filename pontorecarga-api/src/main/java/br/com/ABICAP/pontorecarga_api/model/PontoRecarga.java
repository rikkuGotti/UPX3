package br.com.ABICAP.pontorecarga_api.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PontosRecarga")
@Getter@Setter
@ToString

public class PontoRecarga {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigoPatriomonio")
    private String codigoPatrimonio; // Número de série do equipamento
    @Column(name = "fabricante")
    private String fabricante;
    @Column(name = "modelo")
    private String modelo;

    @OneToMany(mappedBy = "pontoRecarga")
    private List<Reserva> reservas = new ArrayList<>();


    // CARACTERÍSTICAS ELÉTRICAS

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoCarga")
    private TipoCarga tipoCarga;
    @Column(name = "potenciaMaximaKW")
    private Integer potenciaMaximaKW;
    @Column(name = "tensaoNominal")
    private String tensaoNominal;
    @Column(name = "correnteMaximaAmpere")
    private Integer correnteMaximaAmpere;


    // CONECTORES

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoConector")
    private TipoConector tipoConector;
    @Column(name = "quantidadeConectores")
    private Integer quantidadeConectores;


    // COMUNICAÇÃO

    @Column(name = "protocoloComunicacao")
    private String protocoloComunicacao;
    @Column(name = "statusConexao")
    private String statusConexao;
    @Column(name = "ultimaComunicacao")
    private LocalDateTime ultimaComunicacao;


    // STATUS OPERACIONAL

    @Enumerated(EnumType.STRING)
    @Column(name = "statusPonto")
    private StatusPonto statusPonto;
    @Column(name = "statusCabo")
    private StatusCaboConexao statusCabo;
    @Column(name = "sesssaoAtivaId")
    private Long sesssaoAtivaId;


    // MEDIÇÃO

    @Column(name = "medidorKWh")
    private BigDecimal medidorKWh;           // Leitura atual do medidor (ex: 1250.3 kWh)
    @Column(name = "dataUltimaLeitura")
    private LocalDateTime dataUltimaLeitura;

    // LOCALIZAÇÃO FÍSICA

    @Column(name = "localizacao")
    private String localizacao;


    public PontoRecarga() {
    }

    public PontoRecarga(Integer id, String codigoPatrimonio, String fabricante, String modelo, TipoCarga tipoCarga,
                        Integer potenciaMaximaKW, String tensaoNominal, Integer correnteMaximaAmpere, TipoConector tipoConector,
                        Integer quantidadeConectores, String protocoloComunicacao, String statusConexao, LocalDateTime ultimaComunicacao,
                        StatusPonto statusPonto, StatusCaboConexao statusCabo, Long sesssaoAtivaId, BigDecimal medidorKWh,
                        LocalDateTime dataUltimaLeitura, String localizacao) {
        this.id = id;
        this.codigoPatrimonio = codigoPatrimonio;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.tipoCarga = tipoCarga;
        this.potenciaMaximaKW = potenciaMaximaKW;
        this.tensaoNominal = tensaoNominal;
        this.correnteMaximaAmpere = correnteMaximaAmpere;
        this.tipoConector = tipoConector;
        this.quantidadeConectores = quantidadeConectores;
        this.protocoloComunicacao = protocoloComunicacao;
        this.statusConexao = statusConexao;
        this.ultimaComunicacao = ultimaComunicacao;
        this.statusPonto = statusPonto;
        this.statusCabo = statusCabo;
        this.sesssaoAtivaId = sesssaoAtivaId;
        this.medidorKWh = medidorKWh;
        this.dataUltimaLeitura = dataUltimaLeitura;
        this.localizacao = localizacao;
    }
}
