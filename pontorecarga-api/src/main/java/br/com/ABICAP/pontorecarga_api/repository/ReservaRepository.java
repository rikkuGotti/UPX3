package br.com.ABICAP.pontorecarga_api.repository;

import br.com.ABICAP.pontorecarga_api.model.Reserva;
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


}
