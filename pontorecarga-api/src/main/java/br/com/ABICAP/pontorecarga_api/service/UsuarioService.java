package br.com.ABICAP.pontorecarga_api.service;

import br.com.ABICAP.pontorecarga_api.model.CarroUsuario;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.repository.CarroRepository;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {


    private PasswordEncoder passwordEncoder;

    private UsuarioRepository usuarioRepository;

    private CarroRepository carroRepository;



    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, CarroRepository carroRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.carroRepository = carroRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public Usuario  cadastroUsuario(Usuario usuario){
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new RuntimeException("Email é obrigatório");
        }

        if (usuario.getUsuario() == null || usuario.getUsuario().isEmpty()) {
            throw new RuntimeException("Usuário é obrigatório");
        }

        if (usuario.getSenhaHash() == null || usuario.getSenhaHash().isEmpty()) {
            throw new RuntimeException("Senha é obrigatória");
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
        System.out.println("Tentando autenticar: " + usuario);

        return usuarioRepository.findByUsuario(usuario)
                .map(usuarioBanco -> {
                    System.out.println("Usuário encontrado: " + usuarioBanco.getUsuario());
                    System.out.println("Senha do banco: " + usuarioBanco.getSenhaHash());
                    System.out.println("Senha fornecida: " + senha);

                    boolean matches = passwordEncoder.matches(senha, usuarioBanco.getSenhaHash());
                    System.out.println("Senha correta? " + matches);

                    return matches;
                })
                .orElseThrow(() -> {
                    System.out.println("Usuário NÃO encontrado: " + usuario);
                    return new RuntimeException("Usuário não encontrado");
                });
    }
}
