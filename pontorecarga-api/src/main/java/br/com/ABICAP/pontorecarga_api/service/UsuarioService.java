package br.com.ABICAP.pontorecarga_api.service;

import br.com.ABICAP.pontorecarga_api.model.CarroUsuario;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.repository.CarroRepository;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    private CarroRepository carroRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, CarroRepository carroRepository) {
        this.usuarioRepository = usuarioRepository;
        this.carroRepository = carroRepository;
    }



    public Usuario  cadastroUsuario(Usuario usuario){
        if (usuario.getEmail().isEmpty() || usuario.getUsuario().isEmpty() ||  usuario.getSenhaHash().isEmpty()){
            throw new RuntimeException("Algum campo do cadastro do usuario não está preenchido, cheque " +
                    "as informações e tente novamente");
        }


        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
            throw new RuntimeException("Email ja cadastrado");
        }

        CarroUsuario carro = usuario.getCarroUsuario();
        
        if(carro.getPlaca().isEmpty() || carro.getCapacidadeBateria() == null || carro.getMarca() == null
                || carro.getModel().isEmpty() || carro.getTipoCarga() == null || carro.getTipoConector() == null){
            throw new RuntimeException("Algum campo do cadstro do carro não está preenchido, cheque " +
                                       "as informações e tente novamente");
        }

        if(carroRepository.findByPlaca(carro.getPlaca()).isPresent()){
            throw new RuntimeException("Um carro com essa placa ja foi cadastrado, cheque as informações" +
                                       " e tente novamente");
        }

        String senhaEncode = usuario.getSenhaHash();
        usuario.setSenhaHash(passwordEncoder.encode(senhaEncode));

        return usuarioRepository.save(usuario);
    }

    public boolean autenticar(String usuario, String senha) {

        return usuarioRepository.findByUsuario(usuario)
                .map(usuarioBanco -> {

                    if (passwordEncoder.matches(senha, usuarioBanco.getSenhaHash())) {
                        return true;
                    }
                    return false;
                })

                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
