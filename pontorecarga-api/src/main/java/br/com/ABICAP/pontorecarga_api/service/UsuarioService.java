package br.com.ABICAP.pontorecarga_api.service;

import br.com.ABICAP.pontorecarga_api.dto.DTOAtualizarDadosRequest;
import br.com.ABICAP.pontorecarga_api.dto.DTOCriarCarroRequest;
import br.com.ABICAP.pontorecarga_api.dto.DTOCriarContaRequest;
import br.com.ABICAP.pontorecarga_api.dto.DTOTrocarCarroRequest;
import br.com.ABICAP.pontorecarga_api.exception.DadoJaCadastradoException;
import br.com.ABICAP.pontorecarga_api.exception.UsuarioNaoAutenticadoException;
import br.com.ABICAP.pontorecarga_api.exception.UsuarioNaoEncontradoException;
import br.com.ABICAP.pontorecarga_api.model.*;
import br.com.ABICAP.pontorecarga_api.repository.CarroRepository;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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



    public Usuario  cadastroUsuario(DTOCriarContaRequest request){

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()){
            throw new DadoJaCadastradoException("Email ja cadastrado");
        }
        if(request.getSenhaHash().equalsIgnoreCase(request.getUsuario())){
            throw new DadoJaCadastradoException("Senha nao pode ser igual ao usuario");
        }

        DTOCriarCarroRequest carro = request.getCarroUsuario();


        if(carroRepository.findByPlaca(carro.getPlaca()).isPresent()){
            throw new DadoJaCadastradoException("Um carro com essa placa ja foi cadastrado, cheque as informações" +
                                       " e tente novamente");
        }

        String senhaRaw = request.getSenhaHash();
        String senhaEncoded = passwordEncoder.encode(senhaRaw);

        Usuario usuario = new Usuario();
        CarroUsuario carroUsuario= new CarroUsuario();


        usuario.setUsuario(request.getUsuario());
        usuario.setEmail(request.getEmail());
        usuario.setSenhaHash(senhaEncoded);

        carroUsuario.setPlaca(carro.getPlaca());
        carroUsuario.setModel(carro.getModel());
        carroUsuario.setMarca(Marcas.valueOf(carro.getMarca()));
        carroUsuario.setCapacidadeBateria(carro.getCapacidadeBateria());
        carroUsuario.setTipoConector(TipoConector.valueOf(carro.getTipoConector()));
        carroUsuario.setTipoCarga(TipoCarga.valueOf(carro.getTipoCarga()));

        usuario.setCarroUsuario(carroUsuario);

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
                    return new UsuarioNaoEncontradoException("Usuário não encontrado");
                });
    }

    public Usuario validarUsuario(HttpSession session){
        String usuarioLogado = (String) session.getAttribute("USUARIO_LOGADO");

        if (usuarioLogado == null) {
            throw new UsuarioNaoAutenticadoException("Usuário não está logado");
        }

        Usuario usuario = usuarioRepository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));


        return usuario;
    }

    public Usuario alterarDados(DTOAtualizarDadosRequest dados, Usuario usuario){

        if(dados.getUsuario() != null){
            usuario.setUsuario(dados.getUsuario());
        }

        if(dados.getEmail() != null){
            usuario.setEmail(dados.getEmail());
        }

        if(dados.getSenha() != null){
            String senhaRaw = dados.getSenha();
            String senhaEncoded = passwordEncoder.encode(senhaRaw);
            usuario.setSenhaHash(senhaEncoded);
        }

        return usuarioRepository.save(usuario);
    }


    public CarroUsuario alterarCarro( DTOTrocarCarroRequest dados, Usuario usuario) {
        CarroUsuario carro = usuario.getCarroUsuario();

        carro.setPlaca(dados.getPlaca());
        carro.setModel(dados.getModel());
        carro.setMarca(Marcas.valueOf(dados.getMarca()));
        carro.setCapacidadeBateria(dados.getCapacidadeBateria());
        carro.setTipoConector(TipoConector.valueOf(dados.getTipoConector()));
        carro.setTipoCarga(TipoCarga.valueOf(dados.getTipoCarga()));

        return carroRepository.save(carro);

    }
}
