package com.aluracursos.challenge_literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record DatosLibro(@JsonAlias("title") String titulo,
                         @JsonAlias("authors") List<DatosAutor> autor,
                         @JsonAlias("languages") List<String> idiomas,
                         @JsonAlias("download_count") Double numeroDeDescargas) {

    @JsonCreator
    public static DatosLibro of(@JsonProperty("title") String titulo,
                                @JsonProperty("authors") List<DatosAutor> autor,
                                @JsonProperty("languages") List<String> idiomas,
                                @JsonProperty("download_count") Double numeroDeDescargas) {

        List<DatosAutor> listaAutores = new ArrayList<>();
        try {
            if (!autor.isEmpty()) {
                listaAutores.addAll(autor);
            } else {
                DatosAutor NuevoAutor = new DatosAutor("anonimo", "0", "0");
                listaAutores.add(NuevoAutor);
            }
        } catch (Exception e) {
            DatosAutor NuevoAutor = new DatosAutor("anonimo", "0", "0");
            listaAutores.add(NuevoAutor);
        }

        return new DatosLibro(titulo, listaAutores, idiomas, numeroDeDescargas);
    }
}
