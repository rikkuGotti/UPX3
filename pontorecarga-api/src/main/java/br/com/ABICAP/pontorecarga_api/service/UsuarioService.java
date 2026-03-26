package br.com.ABICAP.pontorecarga_api.service;

import br.com.ABICAP.pontorecarga_api.model.CarroUsuario;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.repository.CarroRepository;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    private CarroRepository carroRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, CarroRepository carroRepository) {
        this.usuarioRepository = usuarioRepository;
        this.carroRepository = carroRepository;
    }



    public Usuario  cadastroUsuario(Usuario usuario){
        if (usuario.getEmail().isEmpty() || usuario.getUsuario().isEmpty() ||  usuario.getSenhaHash().isEmpty()){
            throw new RuntimeException("Erro - Campo Vazio");
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
            throw new RuntimeException("Email ja cadastrado");
        }

        CarroUsuario carro = usuario.getCarroUsuario();
        
        if(carro.getPlaca().isEmpty() || carro.getCapacidadeBateria() == null ){
            cont
        }

        return usuarioRepository.save(usuario);
    }
}
