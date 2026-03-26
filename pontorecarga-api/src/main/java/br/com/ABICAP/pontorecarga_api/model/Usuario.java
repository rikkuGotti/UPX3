package br.com.ABICAP.pontorecarga_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Getter@Setter
@ToString
public class Usuario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usuario", length = 255, nullable = false)
    private String usuario;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "senhaHash", length = 60, nullable = false)
    private String senhaHash;

    @Column(name = "dataCriacaoConta")
    private LocalDateTime dataCriacaoConta;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carro_id", referencedColumnName = "id")
    private CarroUsuario carroUsuario;

    public Usuario(Integer id, String usuario, String email, String senhaHash,
                   LocalDateTime dataCriacaoConta, CarroUsuario carroUsuario) {
        this.id = id;
        this.usuario = usuario;
        this.email = email;
        this.senhaHash = senhaHash;
        this.dataCriacaoConta = LocalDateTime.now();
        this.carroUsuario = carroUsuario;
    }

    public Usuario() {

    }


}
