package br.com.ABICAP.pontorecarga_api.repository;

import br.com.ABICAP.pontorecarga_api.model.PontoRecarga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PontoRecargaRepository extends JpaRepository<PontoRecarga, Integer> {
}
