package br.com.ABICAP.pontorecarga_api.service;

import br.com.ABICAP.pontorecarga_api.dto.DTOPontoRecargaRequest;
import br.com.ABICAP.pontorecarga_api.model.PontoRecarga;
import br.com.ABICAP.pontorecarga_api.model.StatusPonto;
import br.com.ABICAP.pontorecarga_api.model.TipoCarga;
import br.com.ABICAP.pontorecarga_api.model.TipoConector;
import br.com.ABICAP.pontorecarga_api.repository.PontoRecargaRepository;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PontoRecargaService {

    private UsuarioRepository usuarioRepository;

    private PontoRecargaRepository pontoRecargaRepository;

    @Autowired
    public PontoRecargaService(UsuarioRepository usuarioRepository, PontoRecargaRepository pontoRecargaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pontoRecargaRepository = pontoRecargaRepository;
    }

    public PontoRecarga cadastrarPontoRecarga(DTOPontoRecargaRequest request){

        if (pontoRecargaRepository.existsByCodigoPatrimonio(request.getCodigoPatrimonio())) {
            throw new RuntimeException("Já existe um ponto com código: " + request.getCodigoPatrimonio());
        }

        PontoRecarga pontoRecarga = new PontoRecarga();

        pontoRecarga.setCodigoPatrimonio(request.getCodigoPatrimonio());
        pontoRecarga.setFabricante(request.getFabricante());
        pontoRecarga.setModelo(request.getModelo());
        pontoRecarga.setTipoCarga(TipoCarga.valueOf(request.getTipoCarga()));
        pontoRecarga.setPotenciaMaximaKW(request.getPotenciaMaximaKW());

        if(request.getTensaoNominal() == null){
            pontoRecarga.setTensaoNominal("220V");
        } else{
            pontoRecarga.setTensaoNominal(request.getTensaoNominal());
        }

        pontoRecarga.setTipoConector(TipoConector.valueOf(request.getTipoConector()));
        pontoRecarga.setStatusPonto(StatusPonto.DISPONIVEL);
        pontoRecarga.setLocalizacao(request.getLocalizacao());


        return pontoRecargaRepository.save(pontoRecarga);
    }


}
