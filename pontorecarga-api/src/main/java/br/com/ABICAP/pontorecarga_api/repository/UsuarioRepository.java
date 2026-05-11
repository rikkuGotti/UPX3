package br.com.ABICAP.pontorecarga_api.repository;

import br.com.ABICAP.pontorecarga_api.model.PontoRecarga;
import br.com.ABICAP.pontorecarga_api.model.Reserva;
import br.com.ABICAP.pontorecarga_api.model.StatusReserva;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUsuario(String usuario);

    @Query("SELECT DISTINCT u FROM Usuario u" +
            " JOIN u.reservas r WHERE r.statusReserva = :status AND " +
            "r.inicio >= :inicio AND r.fim <= :fim AND r.pontoRecarga = :ponto")
    List<Usuario> buscarUsuariosIntervaloDeTempo(@Param("status")StatusReserva statusReserva,
                                                 @Param("inicio") LocalDateTime inicio,
                                                 @Param("fim")LocalDateTime fim,
                                                 @Param("ponto")PontoRecarga ponto);








}
