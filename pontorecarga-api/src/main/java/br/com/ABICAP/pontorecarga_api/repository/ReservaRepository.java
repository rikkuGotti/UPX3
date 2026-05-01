package br.com.ABICAP.pontorecarga_api.repository;

import br.com.ABICAP.pontorecarga_api.model.Reserva;
import br.com.ABICAP.pontorecarga_api.model.StatusReserva;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByData(LocalDate data);

    List<Reserva> findByUsuario(Usuario usuario);

    @Query("SELECT r FROM Reserva r WHERE r.statusReserva = 'EM_ANDAMENTO' " +
            "AND (r.data < CURRENT_DATE OR (r.data = CURRENT_DATE AND r.fim < CURRENT_TIME))")
    List<Reserva> findReservasEmAndamentoExpiradas();

    int countByUsuarioAndDataAndStatusReservaNot(Usuario usuario, LocalDate now, StatusReserva statusReserva);

    int countByUsuarioAndStatusReserva(Usuario usuario, StatusReserva statusReserva);

    List<Reserva> findByDataAfter(LocalDate data);

    List<Reserva> findByDataBetween(LocalDate inicio, LocalDate fim);

    int countByUsuarioAndDataAndStatusReserva(Usuario usuario, LocalDate now, StatusReserva statusReserva);

    int countByUsuarioAndDataBetweenAndStatusReserva(Usuario usuario, LocalDate inicio, LocalDate fim, StatusReserva statusReserva);

    List<Reserva> findByUsuarioAndDataBetweenAndStatusReserva(Usuario usuario, LocalDate inicio, LocalDate fim, StatusReserva statusReserva);

    List<Reserva> findByPontoRecargaIdAndData(Integer pontoId, LocalDate data);
//
//    @Query("SELECT COUNT(r) FROM Reserva r " +
//            "WHERE r.usuario.id = :usuarioId " +
//            "AND r.dataReserva >= CURRENT_DATE - 7")
//    long contarReservasUltimos7Dias(@Param("usuarioId") Long usuarioId);


}
