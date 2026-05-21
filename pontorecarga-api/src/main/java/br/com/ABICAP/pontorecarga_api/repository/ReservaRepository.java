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

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByUsuario(Usuario usuario);

    List<Reserva> findByUsuarioAndPontoRecargaAndStatusReserva(Usuario usuario, PontoRecarga pontoRecarga, StatusReserva statusReserva);


    @Query("SELECT r FROM Reserva r WHERE r.statusReserva = 'EM_ANDAMENTO' AND r.fim < CURRENT_TIMESTAMP")
    List<Reserva> findReservasEmAndamentoExpiradas();


    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.usuario = :usuario AND CAST(r.inicio AS date) = :data AND r.statusReserva != :status")
    int countByUsuarioAndDataAndStatusReservaNot(@Param("usuario") Usuario usuario,
                                                 @Param("data") LocalDate data,
                                                 @Param("status") StatusReserva status);

    int countByUsuarioAndStatusReserva(Usuario usuario, StatusReserva statusReserva);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.usuario = :usuario AND CAST(r.inicio AS date) = :data AND r.statusReserva = :status")
    int countByUsuarioAndDataAndStatusReserva(@Param("usuario") Usuario usuario,
                                              @Param("data") LocalDate data,
                                              @Param("status") StatusReserva status);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.usuario = :usuario AND CAST(r.inicio AS date) BETWEEN :inicio AND :fim AND r.statusReserva = :status")
    int countByUsuarioAndDataBetweenAndStatusReserva(@Param("usuario") Usuario usuario,
                                                     @Param("inicio") LocalDate inicio,
                                                     @Param("fim") LocalDate fim,
                                                     @Param("status") StatusReserva status);

    @Query("SELECT r FROM Reserva r WHERE r.usuario = :usuario AND CAST(r.inicio AS date) BETWEEN :inicio AND :fim AND r.statusReserva = :status")
    List<Reserva> findByUsuarioAndDataBetweenAndStatusReserva(@Param("usuario") Usuario usuario,
                                                              @Param("inicio") LocalDate inicio,
                                                              @Param("fim") LocalDate fim,
                                                              @Param("status") StatusReserva status);

    @Query("SELECT r FROM Reserva r WHERE r.pontoRecarga.id = :pontoId AND CAST(r.inicio AS date) = :data")
    List<Reserva> findByPontoRecargaIdAndData(@Param("pontoId") Integer pontoId,
                                              @Param("data") LocalDate data);

    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :usuarioID AND " +
            "r.statusReserva = :status AND r.inicio >= :inicio AND r.fim <= :fim")
    List<Reserva> reservasPorUsuario(@Param("usuario") Integer usuario,
                                     @Param("status") StatusReserva statusReserva,
                                     @Param("inicio") LocalDateTime inicio,
                                     @Param("fim") LocalDateTime fim);

    boolean existsByUsuarioAndPontoRecargaAndStatusReserva(Usuario usuario, PontoRecarga pontoAtual, StatusReserva statusReserva);

    Integer countByInicioBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Reserva> findByInicioBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Reserva> findByInicioBetweenAndPontoRecargaId(LocalDateTime inicio, LocalDateTime fim, Integer id);

    List<Reserva> findByInicioBetweenAndStatusReserva(LocalDateTime ini, LocalDateTime fi, StatusReserva statusReserva);

    List<Reserva> findByInicioBetweenAndStatusReservaAndUsuario(LocalDateTime ini, LocalDateTime fi, StatusReserva statusReserva, Usuario usuario);
}

