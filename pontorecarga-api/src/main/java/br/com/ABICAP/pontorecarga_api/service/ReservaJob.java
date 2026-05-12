package br.com.ABICAP.pontorecarga_api.service;

import br.com.ABICAP.pontorecarga_api.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservaJob {

    @Autowired
    private ReservaService reservaService;

    @Scheduled(fixedDelay = 86400000)
    public void checarReservasExpiradas(){
        reservaService.finalizarReservasExpiradas();
    }
}
