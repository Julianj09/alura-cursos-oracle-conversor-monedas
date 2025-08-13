package main.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.exception.ConversorException;
import main.model.Money;
import main.model.MoneyRate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class ApiService {

    // Reutilizar el cliente HTTP para mejor rendimiento
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String API_URL_BASE = "https://v6.exchangerate-api.com/v6/";

    public static Money servidor(String money_base, String money_conversor, int valor_join) throws IOException, InterruptedException {
        // Cargar la API key desde config.properties
        Properties apiKeyProps = new Properties();
        String key;

        try (InputStream input = ApiService.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Archivo config.properties no encontrado en el classpath.");
            }
            apiKeyProps.load(input);
            key = apiKeyProps.getProperty("API_KEY");
        }

        if (key == null || key.isBlank()) {
            throw new RuntimeException("API_KEY no encontrada o vacía en config.properties.");
        }

        // Construcción de URL
        String direccion = API_URL_BASE + key + "/pair/" + money_base + "/" + money_conversor;

        // Cliente HTTP y solicitud
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Manejar el caso de una respuesta no exitosa
        if (response.statusCode() != 200) {
            throw new ConversorException("Error al conectar con la API: " + response.body());
        }

        // Convertir JSON a objeto Java
        Gson gson = (new GsonBuilder())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();

        String json = response.body();
        MoneyRate miMoneyRate = gson.fromJson(json, MoneyRate.class);

        // Crear objeto Money y devolver
        return new Money(miMoneyRate, valor_join);
    }
}