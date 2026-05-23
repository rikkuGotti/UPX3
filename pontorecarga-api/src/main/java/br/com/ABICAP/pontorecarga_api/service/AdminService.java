package br.com.ABICAP.pontorecarga_api.service;

import br.com.ABICAP.pontorecarga_api.dto.DTORelatorioConsumoResponse;
import br.com.ABICAP.pontorecarga_api.dto.DTORelatorioListaCarrosResponse;
import br.com.ABICAP.pontorecarga_api.dto.DTOReservaResponse;
import br.com.ABICAP.pontorecarga_api.exception.UsuarioNaoAutenticadoException;
import br.com.ABICAP.pontorecarga_api.exception.UsuarioNaoAutorizadoException;
import br.com.ABICAP.pontorecarga_api.exception.UsuarioNaoEncontradoException;
import br.com.ABICAP.pontorecarga_api.model.*;
import br.com.ABICAP.pontorecarga_api.repository.CarroRepository;
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

    private CarroRepository carroRepository;

    @Autowired
    public AdminService(UsuarioRepository usuarioRepository, PontoRecargaRepository pontoRecargaRepository, ReservaRepository reservaRepository, CarroRepository carroRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pontoRecargaRepository = pontoRecargaRepository;
        this.reservaRepository = reservaRepository;
        this.carroRepository = carroRepository;
    }


    public Usuario validarAdmin(HttpSession session){
        String usuarioLogado = (String) session.getAttribute("USUARIO_LOGADO");

        if (usuarioLogado == null) {
            throw new UsuarioNaoAutenticadoException("Usuário não está logado");
        }

        Usuario usuario = usuarioRepository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (usuario.getTipoUsuario().equals(TipoUsuario.USER)) {
            throw new UsuarioNaoAutorizadoException("Acesso negado. Apenas administradores.");
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


public List<DTORelatorioConsumoResponse> gerarRelatorioConsumo(LocalDate inicio, LocalDate fim, Integer usuarioID) {

        LocalDateTime ini = LocalDateTime.of(inicio, LocalTime.of(0, 0, 0));
        LocalDateTime fi = LocalDateTime.of(fim, LocalTime.of(23, 59, 59));

        Usuario usuario = usuarioRepository.findById(usuarioID)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuario nao encontrado"));

        List<DTORelatorioConsumoResponse> responses = new ArrayList<>();
        List<PontoRecarga> pontos = pontoRecargaRepository.findAll();

        for (PontoRecarga pontoAtual : pontos) {

            List<Reserva> reservas = reservaRepository.findByUsuarioAndPontoRecargaAndStatusReserva(
                    usuario, pontoAtual, StatusReserva.FINALIZADA);

            if (!reservas.isEmpty()) {
                DTORelatorioConsumoResponse response = new DTORelatorioConsumoResponse();
                response.setUsuario(usuario.getUsuario());
                response.setNomePonto(pontoAtual.getLocalizacao());

                int minutosTotais = 0;
                for (Reserva r : reservas) {
                    minutosTotais += r.getDuracaoMinutos();
                }

                double horasTotais = minutosTotais / 60.0;
                double consumoTotal = horasTotais * pontoAtual.getPotenciaMaximaKW().doubleValue();

                response.setConsumoTotal(BigDecimal.valueOf(consumoTotal));
                response.setUsos(reservas.size());

                responses.add(response);
            }
        }

        return responses;
    }

    public List<DTORelatorioListaCarrosResponse> encontrarCarros(String marca) {

        Marcas marcaEnum = Marcas.valueOf(marca.toUpperCase());

        List<CarroUsuario> carros = carroRepository.findByMarca(marcaEnum);
        List<DTORelatorioListaCarrosResponse> responses = new ArrayList<>();

        for(CarroUsuario carro : carros){
            DTORelatorioListaCarrosResponse response = new DTORelatorioListaCarrosResponse();
            response.setUsuario(carro.getUsuario().getUsuario());
            response.setMarca(marca);
            response.setPlaca(carro.getPlaca());
            response.setModelo(carro.getModel());
            response.setCapacidadeBateria(carro.getCapacidadeBateria());
            response.setTipoConector(carro.getTipoConector());
            response.setTipoCarga(carro.getTipoCarga());

            responses.add(response);
        }

        return responses;
    }

    public List<DTOReservaResponse> listarReservas(LocalDate inicio, LocalDate fim){
        LocalDateTime ini = LocalDateTime.of(inicio, LocalTime.of(0, 0, 0));
        LocalDateTime fi = LocalDateTime.of(fim, LocalTime.of(23, 59, 59));

        List<DTOReservaResponse> responses = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findByInicioBetween(ini, fi);

        for(Reserva r : reservas){
            DTOReservaResponse response = new DTOReservaResponse();

            response.setUsuarioNome(r.getUsuario().getUsuario());
            response.setId(r.getId());
            response.setPontoRecargaId(r.getPontoRecarga().getId());
            response.setPontoLocalizacao(r.getPontoRecarga().getLocalizacao());
            response.setHoraInicio(r.getInicio());
            response.setHoraFim(r.getFim());
            response.setDuracaoMinutos(r.getDuracaoMinutos());
            response.setStatus(r.getStatusReserva().toString());

            responses.add(response);
        }

        return responses;
    }

    public List<DTOReservaResponse> listarReservas(LocalDate inicio, LocalDate fim, Integer id){
        LocalDateTime ini = LocalDateTime.of(inicio, LocalTime.of(0, 0, 0));
        LocalDateTime fi = LocalDateTime.of(fim, LocalTime.of(23, 59, 59));

        List<DTOReservaResponse> responses = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findByInicioBetweenAndPontoRecargaId(ini, fi, id);

        for(Reserva r : reservas){
            DTOReservaResponse response = new DTOReservaResponse();

            response.setUsuarioNome(r.getUsuario().getUsuario());
            response.setId(r.getId());
            response.setPontoRecargaId(r.getPontoRecarga().getId());
            response.setPontoLocalizacao(r.getPontoRecarga().getLocalizacao());
            response.setHoraInicio(r.getInicio());
            response.setHoraFim(r.getFim());
            response.setDuracaoMinutos(r.getDuracaoMinutos());
            response.setStatus(r.getStatusReserva().toString());

            responses.add(response);
        }

        return responses;
    }


    public List<DTOReservaResponse> reservasCanceladas(LocalDate inicio, LocalDate fim) {
        LocalDateTime ini = LocalDateTime.of(inicio, LocalTime.of(0, 0, 0));
        LocalDateTime fi = LocalDateTime.of(fim, LocalTime.of(23, 59, 59));

        List<DTOReservaResponse> responses = new ArrayList<>();
        List<Reserva> reservas = reservaRepository.findByInicioBetweenAndStatusReserva(ini, fi, StatusReserva.CANCELADA);

        for(Reserva r : reservas){
            DTOReservaResponse response = new DTOReservaResponse();

            response.setUsuarioNome(r.getUsuario().getUsuario());
            response.setId(r.getId());
            response.setPontoRecargaId(r.getPontoRecarga().getId());
            response.setPontoLocalizacao(r.getPontoRecarga().getLocalizacao());
            response.setHoraInicio(r.getInicio());
            response.setHoraFim(r.getFim());
            response.setDuracaoMinutos(r.getDuracaoMinutos());
            response.setStatus(r.getStatusReserva().toString());

            responses.add(response);
        }


        return  responses;
    }

    public List<DTOReservaResponse> reservasCanceladasUsuario(LocalDate inicio, LocalDate fim, Integer id) {
        LocalDateTime ini = LocalDateTime.of(inicio, LocalTime.of(0, 0, 0));
        LocalDateTime fi = LocalDateTime.of(fim, LocalTime.of(23, 59, 59));

        List<DTOReservaResponse> responses = new ArrayList<>();
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        List<Reserva> reservas = reservaRepository.findByInicioBetweenAndStatusReservaAndUsuario(ini, fi, StatusReserva.CANCELADA, usuario );

        for(Reserva r : reservas){
            DTOReservaResponse response = new DTOReservaResponse();

            response.setUsuarioNome(r.getUsuario().getUsuario());
            response.setId(r.getId());
            response.setPontoRecargaId(r.getPontoRecarga().getId());
            response.setPontoLocalizacao(r.getPontoRecarga().getLocalizacao());
            response.setHoraInicio(r.getInicio());
            response.setHoraFim(r.getFim());
            response.setDuracaoMinutos(r.getDuracaoMinutos());
            response.setStatus(r.getStatusReserva().toString());

            responses.add(response);
        }


        return  responses;
    }
}

