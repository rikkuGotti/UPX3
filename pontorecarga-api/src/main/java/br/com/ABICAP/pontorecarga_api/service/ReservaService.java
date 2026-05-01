package br.com.ABICAP.pontorecarga_api.service;

import br.com.ABICAP.pontorecarga_api.dto.DTOReservaRequest;
import br.com.ABICAP.pontorecarga_api.dto.DTOReservaResponse;
import br.com.ABICAP.pontorecarga_api.model.PontoRecarga;
import br.com.ABICAP.pontorecarga_api.model.Reserva;
import br.com.ABICAP.pontorecarga_api.model.StatusReserva;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.repository.CarroRepository;
import br.com.ABICAP.pontorecarga_api.repository.PontoRecargaRepository;
import br.com.ABICAP.pontorecarga_api.repository.ReservaRepository;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService {

    private UsuarioRepository usuarioRepository;
    private CarroRepository carroRepository;
    private ReservaRepository reservaRepository;
    private PontoRecargaRepository pontoRecargaRepository;

    @Autowired
    public ReservaService(UsuarioRepository usuarioRepository, CarroRepository carroRepository,
                          ReservaRepository reservaRepository, PontoRecargaRepository pontoRecargaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.carroRepository = carroRepository;
        this.reservaRepository = reservaRepository;
        this.pontoRecargaRepository = pontoRecargaRepository;
    }

    public boolean verificarDisponibilidade(LocalTime inicio, LocalTime fim, LocalDate data){

        List<Reserva> reservas = reservaRepository.findByData(data);

        for(Reserva i: reservas){
            if(i.getStatusReserva().equals(StatusReserva.CONFIRMADA)) {
                LocalTime inicioAtual = i.getInicio();
                LocalTime fimAtual = i.getFim();


                if (inicio.isBefore(fimAtual) && fim.isAfter(inicioAtual)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void validarRegraHorario(Reserva reserva){
        LocalTime inicio = reserva.getInicio();
        LocalTime fim = reserva.getFim();
        LocalDate data = reserva.getData();
        Integer minutos = reserva.getDuracaoMinutos();

        if(minutos < 30){
            throw new RuntimeException("Tempo minimo da reserva nao foi atingido");
        }

        if(LocalTime.now().isBefore(LocalTime.of(21, 0)) &&
                LocalTime.now().isAfter(LocalTime.of(9, 0))) {
            if (minutos > 240){
                throw new RuntimeException("Nesse horario nao e permitido uma reserva maior que 4 horas");
            }

        }

        if(minutos > 490){
            throw new RuntimeException("Nao e possivel reservar o ponto por mais de 8 horas");
        }

    }

    public void verificarLimites(Usuario usuario, Integer minutos, LocalDate dataNovaReserva) {

        LocalDate hoje = LocalDate.now();
        LocalDate dataMaxima = hoje.plusDays(1);

        if (dataNovaReserva.isBefore(hoje)) {
            throw new RuntimeException("Não é permitido reservar em datas passadas");
        }

        if (dataNovaReserva.isAfter(dataMaxima)) {
            throw new RuntimeException("Só é permitido reservar com no máximo 1 dia de antecedência");
        }

        int reservasHoje = reservaRepository.countByUsuarioAndDataAndStatusReserva(
                usuario, LocalDate.now(), StatusReserva.CONFIRMADA
        );

        if (reservasHoje >= 2) {
            throw new RuntimeException("Limite de 2 reservas por dia atingido");
        }


        int reservasAmanha = reservaRepository.countByUsuarioAndDataAndStatusReserva(
                usuario, LocalDate.now().plusDays(1), StatusReserva.CONFIRMADA
        );

        if (dataNovaReserva.isAfter(LocalDate.now()) && reservasAmanha >= 2) {
            throw new RuntimeException("Limite de 2 reservas para o dia de amanha");
        }


        LocalDate inicio = LocalDate.now().minusDays(7);
        LocalDate fim = LocalDate.now();

        int reservasSemana = reservaRepository.countByUsuarioAndDataBetweenAndStatusReserva(
                usuario, inicio, fim, StatusReserva.CONFIRMADA
        );

        if (reservasSemana >= 5) {
            throw new RuntimeException("Limite de 5 reservas por semana atingido");
        }


        List<Reserva> reservasUltimaSemana = reservaRepository
                .findByUsuarioAndDataBetweenAndStatusReserva(usuario, inicio, fim, StatusReserva.CONFIRMADA);

        int duracaoReservasExistentes = 0;

        for(Reserva i : reservasUltimaSemana){
            duracaoReservasExistentes += i.getDuracaoMinutos();
        }

        int duracaoTotalComNova = duracaoReservasExistentes + minutos;
        int duracaoMaximaPermitida = 960;

        if (duracaoTotalComNova > duracaoMaximaPermitida) {
            throw new RuntimeException(
                    "Limite de 16 horas por semana excedido."
            );
        }

    }



    public Reserva criarReserva(DTOReservaRequest request, Usuario usuario){

        PontoRecarga pontoRecarga = pontoRecargaRepository.findById(request.getPontoRecargaId())
                .orElseThrow(() -> new RuntimeException("Ponto nao encontrado"));



        if(!verificarDisponibilidade(request.getHoraInicio(), request.getHoraFim(), request.getData())){
            throw new RuntimeException("Horario Indisponivel");

        }

        Reserva reserva = new Reserva();

        reserva.setData(request.getData());
        reserva.setInicio(request.getHoraInicio());
        reserva.setFim(request.getHoraFim());

        reserva.setUsuario(usuario);
        reserva.setPontoRecarga(pontoRecarga);
        long minutos = Duration.between(request.getHoraInicio(), request.getHoraFim()).toMinutes();
        reserva.setDuracaoMinutos((int) minutos);
        reserva.setStatusReserva(StatusReserva.CONFIRMADA);

        validarRegraHorario(reserva);
        verificarLimites(usuario, reserva.getDuracaoMinutos(), reserva.getData());

        return  reservaRepository.save(reserva);
    }

    public List<DTOReservaResponse> listarReservas(Usuario usuario){

        List<Reserva> reservas = reservaRepository.findByUsuario(usuario);
        List<DTOReservaResponse> reservaResponses = new ArrayList<>();

        if(reservas.isEmpty()){
            throw new RuntimeException("Nao ha reservas");
        }

        for(Reserva i: reservas){
            DTOReservaResponse response = new DTOReservaResponse();

            response.setId(i.getId());
            response.setPontoRecargaId(i.getPontoRecarga().getId());
            response.setPontoLocalizacao(i.getPontoRecarga().getLocalizacao());
            response.setData(i.getData());
            response.setHoraInicio(i.getInicio());
            response.setHoraFim(i.getFim());
            response.setDuracaoMinutos(i.getDuracaoMinutos());
            response.setStatus(i.getStatusReserva().toString());
            response.setUsuarioNome(i.getUsuario().getUsuario());

            reservaResponses.add(response);
        }

        return reservaResponses;
    }

    public void iniciarReserva(Integer idReserva, Usuario usuario) {

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));


        if (!reserva.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Esta reserva não pertence a você");
        }


        if (reserva.getStatusReserva() != StatusReserva.CONFIRMADA) {
            throw new RuntimeException("Reserva não pode ser iniciada. Status atual: " + reserva.getStatusReserva());
        }


        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime inicioReserva = LocalDateTime.of(reserva.getData(), reserva.getInicio());

        if (agora.isBefore(inicioReserva)) {
            throw new RuntimeException("Ainda não está no horário da reserva");
        }


        reserva.setStatusReserva(StatusReserva.EM_ANDAMENTO);
        reservaRepository.save(reserva);
    }

    public void finalizarReserva(Integer idReserva, Usuario usuario) {

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));


        if (!reserva.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Esta reserva não pertence a você");
        }


        if (reserva.getStatusReserva() != StatusReserva.EM_ANDAMENTO) {
            throw new RuntimeException("Reserva não pode ser finalizada. Status atual: " + reserva.getStatusReserva());
        }

        reserva.setStatusReserva(StatusReserva.FINALIZADA);
        reservaRepository.save(reserva);
    }

    public void finalizarReservasExpiradas() {
        List<Reserva> reservas = reservaRepository.findReservasEmAndamentoExpiradas();

        for (Reserva i: reservas) {
            i.setStatusReserva(StatusReserva.FINALIZADA);
            reservaRepository.save(i);
        }
    }

    public void cancelarReserva(Integer idReserva, Usuario usuario) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        if (!reserva.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Esta reserva não pertence a você");
        }

        if(reserva.getStatusReserva() != StatusReserva.CONFIRMADA){
            throw new RuntimeException("O status atual nao permite o cancelamento da reserva");
        }

        reserva.setStatusReserva(StatusReserva.CANCELADA);
        reservaRepository.save(reserva);
    }
}
