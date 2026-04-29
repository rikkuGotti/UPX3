package br.com.ABICAP.pontorecarga_api.controller;

import br.com.ABICAP.pontorecarga_api.dto.DTOReservaRequest;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import br.com.ABICAP.pontorecarga_api.service.PontoRecargaService;
import br.com.ABICAP.pontorecarga_api.service.ReservaService;
import br.com.ABICAP.pontorecarga_api.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reserva")
public class ReservaController {

    private ReservaService reservaService;

    private PontoRecargaService pontoRecargaService;

    private UsuarioService usuarioService;

    @Autowired
    public ReservaController(ReservaService reservaService, PontoRecargaService pontoRecargaService, UsuarioService usuarioService) {
        this.reservaService = reservaService;
        this.pontoRecargaService = pontoRecargaService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("criarreserva")
    public ResponseEntity<?> criarReserva(@RequestBody DTOReservaRequest request, HttpSession session){
        Usuario usuario = usuarioService.validarUsuario(session);




        return ResponseEntity.status(201).body("ok");
    }
}
