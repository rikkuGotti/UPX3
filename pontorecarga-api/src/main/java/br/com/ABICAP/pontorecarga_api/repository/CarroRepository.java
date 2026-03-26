package br.com.ABICAP.pontorecarga_api.repository;


import br.com.ABICAP.pontorecarga_api.model.CarroUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarroRepository extends JpaRepository<CarroUsuario, Integer> {

    Optional<CarroUsuario> findByPlaca(String placa);


}
