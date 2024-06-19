package com.aluracursos.challenge_literalura.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        name = "autores"
)
public class Autor {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String nombre;
    private Integer anioDeNacimiento;
    private Integer anioDeFallecimiento;
    @ManyToMany(
            mappedBy = "autor",
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER
    )
    private List<Libro> libros;

    public Autor() {
    }

    public Autor(String nombre, Integer anioDeNacimiento, Integer anioDeFallecimiento) {
        this.nombre = nombre;
        this.anioDeNacimiento = anioDeNacimiento;
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioDeNacimiento() {
        return this.anioDeNacimiento;
    }

    public void setAnioDeNacimiento(Integer anioDeNacimiento) {
        this.anioDeNacimiento = anioDeNacimiento;
    }

    public Integer getAnioDeFallecimiento() {
        return this.anioDeFallecimiento;
    }

    public void setAnioDeFallecimiento(Integer anioDeFallecimiento) {
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return this.libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Autor: '").append(nombre)
                .append("\nAño de nacimiento: ").append(anioDeNacimiento)
                .append("\nAño de fallecimiento: ").append(anioDeFallecimiento)
                .append("\nLibros: [");

        for (Libro libro : getLibros()) {
            sb.append("{").append(libro.getTitulo().toString()).append("}");
        }

        sb.append("]\n");

        return sb.toString();
    }
}
