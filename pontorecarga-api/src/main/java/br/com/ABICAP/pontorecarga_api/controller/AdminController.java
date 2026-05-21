package br.com.ABICAP.pontorecarga_api.controller;

import br.com.ABICAP.pontorecarga_api.dto.DTOPontoRecargaRequest;
import br.com.ABICAP.pontorecarga_api.dto.DTORelatorioConsumoResponse;
import br.com.ABICAP.pontorecarga_api.dto.DTORelatorioListaCarrosResponse;
import br.com.ABICAP.pontorecarga_api.dto.DTOReservaResponse;
import br.com.ABICAP.pontorecarga_api.model.*;
import br.com.ABICAP.pontorecarga_api.repository.PontoRecargaRepository;
import br.com.ABICAP.pontorecarga_api.repository.ReservaRepository;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import br.com.ABICAP.pontorecarga_api.service.AdminService;
import br.com.ABICAP.pontorecarga_api.service.PontoRecargaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private UsuarioRepository usuarioRepository;

    private AdminService adminService;

    private PontoRecargaService pontoRecargaService;

    private ReservaRepository reservaRepository;

    PontoRecargaRepository pontoRecargaRepository;

    @Autowired
    public AdminController(UsuarioRepository usuarioRepository, AdminService adminService, PontoRecargaService pontoRecargaService, ReservaRepository reservaRepository, PontoRecargaRepository pontoRecargaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.adminService = adminService;
        this.pontoRecargaService = pontoRecargaService;
        this.reservaRepository = reservaRepository;
        this.pontoRecargaRepository = pontoRecargaRepository;
    }

    @GetMapping("/teste")
    private ResponseEntity<?> teste(HttpSession session){
        String adminEstaLogado = (String) session.getAttribute("USUARIO_LOGADO");
        Usuario usuario = usuarioRepository.findByUsuario(adminEstaLogado).orElseThrow(null);



        if(usuario.getTipoUsuario().equals(TipoUsuario.USER) || adminEstaLogado == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
        }


        return ResponseEntity.ok("Sucesso");
    }

    @PostMapping("/criarponto")
    public ResponseEntity<?> criarPonto(@RequestBody @Valid DTOPontoRecargaRequest request, HttpSession session){

        adminService.validarAdmin(session);

        PontoRecarga ponto = pontoRecargaService.cadastrarPontoRecarga(request);


        return ResponseEntity.status(200).body(ponto);
    }

    @GetMapping("/relatorio/consumo-moradores")
    public ResponseEntity<?> relatorioConsumo(@RequestParam LocalDate inicio,
                                              @RequestParam LocalDate fim,
                                              HttpSession session){
        adminService.validarAdmin(session);

        List<DTORelatorioConsumoResponse> responses = adminService.gerarRelatorioConsumo(inicio, fim);

        return ResponseEntity.status(200).body(responses);
    }




    @GetMapping("/relatorio/consumo-por-morador/{usuarioID}")
    public ResponseEntity<?> relatorioConsumoMorador(@PathVariable Integer usuarioID,
                                                     @RequestParam LocalDate inicio,
                                                     @RequestParam LocalDate fim,
                                                     HttpSession session){
        adminService.validarAdmin(session);

        List<DTORelatorioConsumoResponse> responses = adminService.gerarRelatorioConsumo(inicio, fim, usuarioID);

        return ResponseEntity.status(200).body(responses);
    }

    @GetMapping("/relatorio/carros-por-marca/{marca}")
    public ResponseEntity<?> relatorioCarrosPorMarca(@PathVariable String marca, HttpSession session){
        adminService.validarAdmin(session);

        List<DTORelatorioListaCarrosResponse> responses =  adminService.encontrarCarros(marca);

        return ResponseEntity.status(200).body(responses);
    }

    @GetMapping("/relatorio/total-reservas")
    public ResponseEntity<?> totalReservas(@RequestParam LocalDate inicio, @RequestParam LocalDate fim, HttpSession session){
        adminService.validarAdmin(session);

        LocalDateTime ini = LocalDateTime.of(inicio, LocalTime.of(0, 0, 0));
        LocalDateTime fi = LocalDateTime.of(fim, LocalTime.of(23, 59, 59));

        Integer totalReservas = reservaRepository.countByInicioBetween(ini, fi);

        return ResponseEntity.status(200).body(totalReservas);
    }

    @GetMapping("/relatorio/reservas")
    public ResponseEntity<?> reservas(@RequestParam LocalDate inicio, @RequestParam LocalDate fim, HttpSession session){
        adminService.validarAdmin(session);

        List<DTOReservaResponse> responses = adminService.listarReservas(inicio, fim);

        return ResponseEntity.status(200).body(responses);
    }

    @GetMapping("/relatorio/reservas/{id}")
    public ResponseEntity<?> reservasPorPonto(@PathVariable Integer id, @RequestParam LocalDate inicio, @RequestParam LocalDate fim, HttpSession session){
        adminService.validarAdmin(session);

        if(!pontoRecargaRepository.existsById(id)){
            return ResponseEntity.status(404).body("Ponto nao encontrado");
        }

        List<DTOReservaResponse> responses = adminService.listarReservas(inicio, fim, id);

        return ResponseEntity.status(200).body(responses);
    }

    @GetMapping("/relatorio/reservas/canceladas")
    public ResponseEntity<?> reservasCanceladasPorTempo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim, HttpSession session){
        adminService.validarAdmin(session);

        List<DTOReservaResponse> responses = adminService.reservasCanceladas(inicio, fim);

        if(responses.isEmpty()){
            return ResponseEntity.status(404).body("Reservas canceladas nao encontradas");
        }

        return ResponseEntity.status(200).body(responses);
    }

    @GetMapping("/relatorio/reservas/canceladasUsuario")
    public ResponseEntity<?> reservasCanceladasPorUsuario(@RequestParam Integer id ,@RequestParam LocalDate inicio, @RequestParam LocalDate fim, HttpSession session){
        adminService.validarAdmin(session);

        List<DTOReservaResponse> responses = adminService.reservasCanceladasUsuario(inicio, fim, id);

        if(responses.isEmpty()){
            return ResponseEntity.status(404).body("Reservas canceladas nao encontradas");
        }

        return ResponseEntity.status(200).body(responses);
    }













//    @PostMapping("/cadastrar-novo-admin")
//    private ResponseEntity<?> criarAdmin(@RequestBody Usuario adminUsuario, HttpSession session){
//        String adminEstaLogado = (String) session.getAttribute("USUARIO_LOGADO");
//
//        if(adminEstaLogado == null){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nao Logado");
//        }
//    }


}
