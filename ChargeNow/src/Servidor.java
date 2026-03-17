import com.sun.net.httpserver.HttpServer;
import controller.UsuarioHandler;
import model.CarroUsuario;
import model.TipoCarga;
import model.TipoConector;
import model.Usuario;

import java.net.InetSocketAddress;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

public class Servidor {
    public static void main(String[] args)
    throws Exception{

//        Criação do servidor
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/usuarios", new UsuarioHandler());

        server.start();
        System.out.println("Servidor rodando em http://localhost:8080");







//        Usuario usuarioTeste = new Usuario("Gabriel", "xxx@gmail.com", LocalDate.of(2000, Month.DECEMBER, 10),
//                                "senhateste", new CarroUsuario("Byd", "Sei la", 1.0,
//                                            Arrays.asList(TipoConector.CCS2), TipoCarga.RAPIDA));
//
//        System.out.println(usuarioTeste);
//        System.out.println(usuarioTeste.getCarroUsuario());
    }
}