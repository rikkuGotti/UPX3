package br.com.ABICAP.pontorecarga_api.service;

import br.com.ABICAP.pontorecarga_api.dto.DTOPontoRecargaRequest;
import br.com.ABICAP.pontorecarga_api.model.*;
import br.com.ABICAP.pontorecarga_api.repository.PontoRecargaRepository;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private UsuarioRepository usuarioRepository;

    private PontoRecargaRepository pontoRecargaRepository;

    @Autowired
    public AdminService(UsuarioRepository usuarioRepository, PontoRecargaRepository pontoRecargaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pontoRecargaRepository = pontoRecargaRepository;
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

}
