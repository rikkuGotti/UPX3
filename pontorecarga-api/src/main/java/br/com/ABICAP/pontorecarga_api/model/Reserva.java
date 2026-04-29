package br.com.ABICAP.pontorecarga_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Reservas")
@Getter@Setter
@ToString

public class Reserva {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ponto_recarga_id", nullable = false)
    private PontoRecarga pontoRecarga;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "data")
    private LocalDate data;
    @Column(name = "inicio")
    private LocalTime inicio;
    @Column(name = "fim")
    private LocalTime fim;

    @Column(name = "duracaoMinutos")
    private Integer duracaoMinutos;

}
