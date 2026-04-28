package br.com.ABICAP.pontorecarga_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter@Setter
@ToString

public class Usuario
        implements UserDetails
{

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
    private LocalDateTime dataCriacaoConta = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 5, nullable = false)
    private TipoUsuario tipoUsuario = TipoUsuario.USER;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carro_id", referencedColumnName = "id")
    private CarroUsuario carroUsuario;

    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas = new ArrayList<>();

    public Usuario() {
    }

    // ----------------------
    //SPRING SECURITY METODOS
    // ----------------------

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return this.usuario;
    }

    @Override
    public @Nullable String getPassword() {
        return this.senhaHash;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.tipoUsuario.name()));
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
}
