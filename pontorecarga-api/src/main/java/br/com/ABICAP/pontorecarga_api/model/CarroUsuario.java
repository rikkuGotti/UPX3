package br.com.ABICAP.pontorecarga_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "carroUsuario")
@Getter@Setter
public class CarroUsuario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "marca", length = 255, nullable = false)
    private Marcas marca;


    @Column(name = "placa", length = 255, nullable = false)
    private String placa;

    @Column(name = "modelo", length = 255, nullable = false)
    private String model;

    @Column(name = "capacidadeBateria", precision = 18, scale = 2)
    private BigDecimal capacidadeBateria;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoConector", length = 8, nullable = false)
    private TipoConector tipoConector;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoCarga", length = 6, nullable = false)
    private TipoCarga tipoCarga;

    @OneToOne(mappedBy = "carroUsuario")
    private Usuario usuario;

    public CarroUsuario(Integer id, Marcas marca, String placa, String model,
                        BigDecimal capacidadeBateria, TipoConector tipoConector,
                        TipoCarga tipoCarga, Usuario usuario) {
        this.id = id;
        this.marca = marca;
        this.placa = placa;
        this.model = model;
        this.capacidadeBateria = capacidadeBateria;
        this.tipoConector = tipoConector;
        this.tipoCarga = tipoCarga;
        this.usuario = usuario;
    }

    public CarroUsuario() {
    }

}
