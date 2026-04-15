package br.com.ABICAP.pontorecarga_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private UsuarioService usuarioService;

    @Autowired

    public AdminService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


}
