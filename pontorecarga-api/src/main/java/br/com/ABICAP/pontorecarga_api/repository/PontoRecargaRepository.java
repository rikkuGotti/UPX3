package br.com.ABICAP.pontorecarga_api.repository;

import br.com.ABICAP.pontorecarga_api.model.PontoRecarga;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PontoRecargaRepository extends JpaRepository<PontoRecarga, Integer> {

    boolean existsByCodigoPatrimonio(@NotBlank(message = "Código do patrimônio é obrigatório") String codigoPatrimonio);


}
