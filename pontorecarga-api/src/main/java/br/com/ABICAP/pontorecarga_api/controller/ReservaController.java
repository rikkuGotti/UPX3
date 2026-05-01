package br.com.ABICAP.pontorecarga_api.controller;

import br.com.ABICAP.pontorecarga_api.dto.DTOReservaRequest;
import br.com.ABICAP.pontorecarga_api.dto.DTOReservaResponse;
import br.com.ABICAP.pontorecarga_api.model.Reserva;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.repository.ReservaRepository;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import br.com.ABICAP.pontorecarga_api.service.PontoRecargaService;
import br.com.ABICAP.pontorecarga_api.service.ReservaService;
import br.com.ABICAP.pontorecarga_api.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("reserva")
public class ReservaController {

    private ReservaService reservaService;

    private PontoRecargaService pontoRecargaService;

    private UsuarioService usuarioService;

    private ReservaRepository reservaRepository;

    @Autowired
    public ReservaController(ReservaService reservaService, PontoRecargaService pontoRecargaService, UsuarioService usuarioService, ReservaRepository reservaRepository) {
        this.reservaService = reservaService;
        this.pontoRecargaService = pontoRecargaService;
        this.usuarioService = usuarioService;
        this.reservaRepository = reservaRepository;
    }

    @PostMapping("criarreserva")
    public ResponseEntity<?> criarReserva(@RequestBody DTOReservaRequest request, HttpSession session){

        Usuario usuario = usuarioService.validarUsuario(session);

        reservaService.criarReserva(request, usuario);


        return ResponseEntity.status(201).body("ok");
    }

    @GetMapping("meus")
    public ResponseEntity<?> listarReservas(HttpSession session){
        Usuario usuario = usuarioService.validarUsuario(session);

        List<DTOReservaResponse> responses = reservaService.listarReservas(usuario);

        return ResponseEntity.status(201).body(responses);
    }

    @PutMapping("/meus/{id}/iniciar")
    public ResponseEntity<?> iniciarReserva(@PathVariable Integer id, HttpSession session) {
        Usuario usuario = usuarioService.validarUsuario(session);

        reservaService.iniciarReserva(id, usuario);

        return ResponseEntity.status(201).body("reserva iniciada");
    }

    @PutMapping("/meus/{id}/finalizar")
    public ResponseEntity<?> finalizarReserva(@PathVariable Integer id, HttpSession session) {
        Usuario usuario = usuarioService.validarUsuario(session);

        reservaService.finalizarReserva(id, usuario);

        return ResponseEntity.status(201).body("reserva finalizada");
    }

    @PutMapping("/meus/{id}/cancelar")
    public ResponseEntity<?> cancelarReserva(@PathVariable Integer id, HttpSession session) {
        Usuario usuario = usuarioService.validarUsuario(session);

        reservaService.cancelarReserva(id, usuario);

        return ResponseEntity.status(201).body("reserva finalizada");
    }

    @GetMapping("/reservas/disponibilidade")
    public ResponseEntity<List<Reserva>> horariosOcupados(@RequestParam Integer pontoId, @RequestParam LocalDate data) {

        List<Reserva> reservas = reservaRepository.findByPontoRecargaIdAndData(pontoId, data);
        return ResponseEntity.ok(reservas);
    }





}
