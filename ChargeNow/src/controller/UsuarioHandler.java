package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Usuario;
import tools.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class UsuarioHandler implements HttpHandler {

    static List<Usuario> lista = new ArrayList<>();

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String metodo = exchange.getRequestMethod();
        String resposta = "";

        if(metodo.equals("POST")){
            InputStream is = exchange.getRequestBody();
            String body = new String(is.readAllBytes());

            Usuario usuario = mapper.readValue(body, Usuario.class);

            lista.add(usuario);

            resposta = "usuario cadastrado";
        }

        exchange.getResponseHeaders().add("Content-Type", "appliication/json");

        exchange.sendResponseHeaders(200, resposta.getBytes().length);

        OutputStream os = exchange.getResponseBody();
        os.write(resposta.getBytes());
        os.close();


    }
}
