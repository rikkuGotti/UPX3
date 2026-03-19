package DataLoader;

import model.Usuario;

import java.util.List;

public class UsuarioRepository {

    private List<Usuario> listaUsuario;

    public void salvarUsuario(Usuario usuario){
        listaUsuario.add(usuario);
    }


}
