package com.aluracursos.challenge_literalura.services;

public interface IConvierteDatos {

    <T> T obtenerDatos(String json, Class<T> clase);

}
