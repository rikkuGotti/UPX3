package br.com.ABICAP.pontorecarga_api.repository;

import br.com.ABICAP.pontorecarga_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUsuario(String usuario);




}
