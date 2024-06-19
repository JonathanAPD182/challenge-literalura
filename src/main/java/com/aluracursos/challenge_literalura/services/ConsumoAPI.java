package com.aluracursos.challenge_literalura.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    public ConsumoAPI() {
    }

    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = (String)response.body();
            return json;
        } catch (InterruptedException | IOException var6) {
            Exception e = var6;
            throw new RuntimeException(e);
        }
    }

}
