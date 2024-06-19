package com.aluracursos.challenge_literalura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos{
    private ObjectMapper objectMapper = new ObjectMapper();
    //Este constructor se usa para se utilizado por JACKSON_DATABIND
    public ConvierteDatos() {
    }

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return this.objectMapper.readValue(json, clase);
        } catch (JsonProcessingException var4) {
            JsonProcessingException e = var4;
            throw new RuntimeException(e);
        }
    }
}
