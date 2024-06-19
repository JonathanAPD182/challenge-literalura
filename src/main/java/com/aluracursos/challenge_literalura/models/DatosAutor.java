package com.aluracursos.challenge_literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year")String anioNacimiento,
        @JsonAlias("death_year")String anioFallecimiento)
{
    @JsonCreator
    public static DatosAutor of(
            @JsonProperty("name") String nombre,
            @JsonProperty("birth_year") String anioNacimiento,
            @JsonProperty("death_year") String anioFallecimiento
    ) {

        String nombreNull="";
        String anioNacimientoNull="";
        String anioFallecimientoNull="";
        try {
            anioNacimientoNull = (anioNacimiento == null) ? "0" : anioNacimiento;
            anioFallecimientoNull = (anioFallecimiento == null) ? "0" : anioFallecimiento;
            nombreNull=(nombre == null) ? "Anónimo" : nombre;
            return new DatosAutor(nombreNull, anioNacimientoNull, anioFallecimientoNull);
        } catch (NumberFormatException e) {
            anioNacimientoNull = "0"; // o cualquier otro valor predeterminado
            anioFallecimientoNull = "0";
            nombreNull="Anónimo";
            return new DatosAutor(nombreNull, anioNacimientoNull, anioFallecimientoNull);
        }


    }


}
