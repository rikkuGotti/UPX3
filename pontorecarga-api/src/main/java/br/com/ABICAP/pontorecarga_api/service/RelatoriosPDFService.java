package br.com.ABICAP.pontorecarga_api.service;

import br.com.ABICAP.pontorecarga_api.dto.DTODadosRelatorioPDF;
import br.com.ABICAP.pontorecarga_api.dto.DTORelatorioConsumoPDFResponse;
import br.com.ABICAP.pontorecarga_api.model.PontoRecarga;
import br.com.ABICAP.pontorecarga_api.model.Reserva;
import br.com.ABICAP.pontorecarga_api.model.StatusReserva;
import br.com.ABICAP.pontorecarga_api.model.Usuario;
import br.com.ABICAP.pontorecarga_api.repository.PontoRecargaRepository;
import br.com.ABICAP.pontorecarga_api.repository.ReservaRepository;
import br.com.ABICAP.pontorecarga_api.repository.UsuarioRepository;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RelatoriosPDFService {

    ReservaRepository reservaRepository;

    PontoRecargaRepository pontoRecargaRepository;

    UsuarioRepository usuarioRepository;

    @Autowired
    public RelatoriosPDFService(ReservaRepository reservaRepository, PontoRecargaRepository pontoRecargaRepository, UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.pontoRecargaRepository = pontoRecargaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public byte[] gerarRelatorioPDF(DTODadosRelatorioPDF dadosRelatorioPDF){
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument, PageSize.A4);

            DecimalFormat df = new DecimalFormat("#,##0.00");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            dadosRelatorioPDF = pegarDados();

            Paragraph titulo = new Paragraph("RELATÓRIO CHARGENOW").setFontSize(18).setTextAlignment(TextAlignment.CENTER).setMarginBottom(20);

            document.add(titulo);

            Paragraph contexto = new Paragraph("Este relatório apresenta uma análise do consumo de energia elétrica referente ao último mês," +
                    " trazendo informações gerais dos usuários organizadas em tabela e identificando o ponto de recarga mais utilizado no período." +
                    " O objetivo é oferecer uma visão clara dos padrões de uso," +
                    " facilitando a tomada de decisões sobre eficiência energética e alocação de recursos. \n \n").setFontSize(12).setTextAlignment(TextAlignment.LEFT);

            document.add(contexto);


            float[] larguraColunas = {1, 3, 2, 2, 2};

            Table tabela = new Table(UnitValue.createPercentArray(larguraColunas)).useAllAvailableWidth();

            Cell tituloTabela = new Cell(1, 5)
                    .add(new Paragraph("Dados de Consumo dos Usuários"))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(14)
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY).setPadding(5);

            tabela.addCell(tituloTabela);

            tabela.addCell("ID");
            tabela.addCell("Usuario");
            tabela.addCell("Consumo");
            tabela.addCell("Uso Total");
            tabela.addCell("Usos");


            for(DTORelatorioConsumoPDFResponse dados : dadosRelatorioPDF.getInfoUsuarios()){
                tabela.addCell(dados.getId().toString());
                tabela.addCell(dados.getUsuario());
                tabela.addCell(dados.getConsumoTotal().toString());
                tabela.addCell(dados.getTempoDeUso().toString());
                tabela.addCell(dados.getUsos().toString());
            }

            document.add(tabela);

            Paragraph infoGeral = new Paragraph("\n \n Gasto Energia: " + dadosRelatorioPDF.getGastoMedioEnergia() +
                    "\n Ponto mais usado: " + dadosRelatorioPDF.getPostoMaisUsado() + "\n \n").setFontSize(12);

            document.add(infoGeral);

            Paragraph dataEmissao = new Paragraph("Data de emissão: " + LocalDate.now().toString()).setTextAlignment(TextAlignment.RIGHT);

            document.add(dataEmissao);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public DTODadosRelatorioPDF pegarDados() {
        List<PontoRecarga> pontos = pontoRecargaRepository.findAll();
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Map.Entry<PontoRecarga, Integer>> listaMinutos = new ArrayList<>();

        DTODadosRelatorioPDF response = new DTODadosRelatorioPDF();
        List<DTORelatorioConsumoPDFResponse> responses2 = new ArrayList<>();

        // LOGICA DO PONTO MAIS USADO
        for (PontoRecarga pontoRecarga : pontos) {
            Integer minutosPonto = 0;
            for (Reserva reserva : pontoRecarga.getReservas()) {
                if (reserva.getInicio().getMonth() == LocalDate.now().getMonth()
                        && reserva.getInicio().getYear() == LocalDate.now().getYear()) {
                    minutosPonto += reserva.getDuracaoMinutos();
                }
            }
            listaMinutos.add(Map.entry(pontoRecarga, minutosPonto));
        }

        Map.Entry<PontoRecarga, Integer> pontoMaisUsos = listaMinutos.stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
        PontoRecarga pontoMaisUsado = pontoMaisUsos != null ? pontoMaisUsos.getKey() : null;

        double consumoGeralTotal = 0;
        LocalDate inicio = LocalDate.now().minusMonths(1);
        LocalDate fim = LocalDate.now();

        // 2. DADOS DE USUARIOS
        for (Usuario user : usuarios) {
            DTORelatorioConsumoPDFResponse pdfResponse = new DTORelatorioConsumoPDFResponse();
            pdfResponse.setId(user.getId());
            pdfResponse.setUsuario(user.getUsuario());

            List<Reserva> reservas = reservaRepository
                    .findByUsuarioAndDataBetweenAndStatusReserva(
                            user, inicio, fim, StatusReserva.FINALIZADA);

            double consumoTotal = 0;
            int minutosTotais = 0;


            for (Reserva reserva : reservas) {
                int minutosReserva = reserva.getDuracaoMinutos();
                minutosTotais += minutosReserva;


                double horasReserva = minutosReserva / 60.0;
                double potenciaKW = reserva.getPontoRecarga()
                        .getPotenciaMaximaKW()
                        .doubleValue();

                consumoTotal += horasReserva * potenciaKW;
            }

            consumoGeralTotal += consumoTotal;

            pdfResponse.setConsumoTotal(BigDecimal.valueOf(consumoTotal));
            pdfResponse.setTempoDeUso(minutosTotais);
            pdfResponse.setUsos(reservas.size());

            responses2.add(pdfResponse);
        }

        response.setInfoUsuarios(responses2);
        response.setPostoMaisUsado(pontoMaisUsado != null ?
                pontoMaisUsado.getLocalizacao() : "Nenhum");
        response.setGastoMedioEnergia(consumoGeralTotal);

        return response;
    }



}
