import model.CarroUsuario;
import model.TipoCarga;
import model.TipoConector;
import model.Usuario;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class Application {
    public static void main(String[] args) {

        Usuario usuarioTeste = new Usuario("Gabriel", "xxx@gmail.com", LocalDate.of(2000, Month.DECEMBER, 10),
                                "senhateste", new CarroUsuario("Byd", "Sei la", 1.0,
                                            Arrays.asList(TipoConector.CCS2), TipoCarga.RAPIDA));

        System.out.println(usuarioTeste);
        System.out.println(usuarioTeste.getCarroUsuario());
    }
}