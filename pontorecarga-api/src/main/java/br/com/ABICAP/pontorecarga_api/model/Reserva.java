package br.com.ABICAP.pontorecarga_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name = "inicio")
    private LocalDateTime inicio;
    @Column(name = "fim")
    private LocalDateTime fim;

    @Column(name = "duracaoMinutos")
    private Integer duracaoMinutos;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusReserva statusReserva;

}
