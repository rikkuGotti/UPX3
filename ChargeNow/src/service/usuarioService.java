package service;

import DataLoader.UsuarioRepository;
import model.Usuario;

public class usuarioService {

    UsuarioRepository usuarioRepository = new UsuarioRepository();

    public void cadastroUsuario(Usuario usuario){
        if(usuario.getEmail() == null){
            throw new RuntimeException("Email obrigatorio");
        } else if (usuario.getSenhaHash() == null){
            throw new RuntimeException("Senha obrigatoria");
        } else if (usuario.getNome() == null) {
            throw new RuntimeException("Nome do usuário Obrigatorio");

        }
        usuarioRepository.salvarUsuario(usuario);

    }


}
