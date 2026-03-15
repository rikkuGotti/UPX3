import model.Usuario;

import java.time.LocalDate;
import java.time.Month;

public class Application {
    public static void main(String[] args) {

        Usuario usuarioTeste = new Usuario("gabriel", "xxx@gmail.com", LocalDate.of(2000, Month.DECEMBER, 10), "senhateste");

        System.out.println(usuarioTeste);
    }
}