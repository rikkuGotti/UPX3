package br.com.ABICAP.pontorecarga_api.service;

import br.com.ABICAP.pontorecarga_api.dto.DTORelatorioConsumoResponse;
import br.com.ABICAP.pontorecarga_api.model.*;
import br.com.ABICAP.pontorecarga_api.repository.PontoRecargaRepository;
import br.com.ABICAP.pontorecarga_api.repository.ReservaRepository;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private UsuarioRepository usuarioRepository;

    private PontoRecargaRepository pontoRecargaRepository;

    private ReservaRepository reservaRepository;

    @Autowired
    public AdminService(UsuarioRepository usuarioRepository, PontoRecargaRepository pontoRecargaRepository, ReservaRepository reservaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pontoRecargaRepository = pontoRecargaRepository;
        this.reservaRepository = reservaRepository;
    }


    public Usuario validarAdmin(HttpSession session){
        String usuarioLogado = (String) session.getAttribute("USUARIO_LOGADO");

        if (usuarioLogado == null) {
            throw new RuntimeException("Usuário não está logado");
        }

        Usuario usuario = usuarioRepository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (usuario.getTipoUsuario().equals(TipoUsuario.USER)) {
            throw new RuntimeException("Acesso negado. Apenas administradores.");
        }

        return usuario;
    }

    public List<DTORelatorioConsumoResponse> gerarRelatorioConsumo(LocalDate inicio, LocalDate fim) {

        LocalDateTime ini = LocalDateTime.of(inicio, LocalTime.of(0, 0, 0));
        LocalDateTime fi = LocalDateTime.of(fim, LocalTime.of(23, 59, 59));

        List<DTORelatorioConsumoResponse> responses = new ArrayList<>();
        List<PontoRecarga> pontos = pontoRecargaRepository.findAll();

        for (PontoRecarga pontoAtual : pontos) {

            List<Usuario> usuariosPontoAtual = usuarioRepository.buscarUsuariosIntervaloDeTempo(
                    StatusReserva.FINALIZADA, ini, fi, pontoAtual);

            for (Usuario u : usuariosPontoAtual) {
                DTORelatorioConsumoResponse response = new DTORelatorioConsumoResponse();
                response.setUsuario(u.getUsuario());
                response.setNomePonto(pontoAtual.getLocalizacao());

                List<Reserva> reservasNessePonto = reservaRepository.findByUsuarioAndPontoRecargaAndStatusReserva(u, pontoAtual, StatusReserva.FINALIZADA);

                int minutosTotais = 0;
                for (Reserva r : reservasNessePonto) {
                    minutosTotais += r.getDuracaoMinutos();
                }

                double horasTotais = minutosTotais / 60.0;
                double consumoTotal = horasTotais * pontoAtual.getPotenciaMaximaKW().doubleValue();


                response.setConsumoTotal(BigDecimal.valueOf(consumoTotal));
                response.setUsos(reservasNessePonto.size());

                responses.add(response);
            }
        }

        return responses;
    }
}
