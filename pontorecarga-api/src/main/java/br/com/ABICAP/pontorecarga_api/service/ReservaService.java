package br.com.ABICAP.pontorecarga_api.service;

import br.com.ABICAP.pontorecarga_api.dto.DTOReservaRequest;
import br.com.ABICAP.pontorecarga_api.model.PontoRecarga;
import br.com.ABICAP.pontorecarga_api.model.Reserva;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.repository.CarroRepository;
import br.com.ABICAP.pontorecarga_api.repository.PontoRecargaRepository;
import br.com.ABICAP.pontorecarga_api.repository.ReservaRepository;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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
            LocalTime inicioAtual = i.getInicio();
            LocalTime fimAtual = i.getFim();


            if(inicio.isBefore(fimAtual) && fim.isAfter(inicioAtual)){
                return false;
            }
        }

        return true;
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


        return  reservaRepository.save(reserva);
    }




}
