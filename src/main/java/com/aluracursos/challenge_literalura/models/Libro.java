package com.aluracursos.challenge_literalura.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(
        name = "libros"
)
public class Libro {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            unique = true
    )
    private String titulo;
    private String idiomas;
    private Double numeroDeDescargas;
    @ManyToMany(
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "libro_autor",
            joinColumns = {@JoinColumn(
                    name = "libro_id"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "autor_id"
            )}
    )
    private List<Autor> autor;

    public Libro() {
    }

    //metodo estatico
    public Libro(DatosLibro libro) {
        this.titulo = libro.titulo();
        this.idiomas = libro.idiomas().get(0).split(",")[0].trim();
        this.numeroDeDescargas = libro.numeroDeDescargas();
        this.autor = libro.autor().stream()
                    .map(a -> new Autor(libro.autor().get(0).nombre(),
                            Integer.valueOf(libro.autor().get(0).anioNacimiento()),
                            Integer.valueOf(libro.autor().get(0).anioFallecimiento())))
                    .toList();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return this.numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public List<Autor> getAutor() {
        return this.autor;
    }

    public void setAutor(List<Autor> autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        StringBuilder autoresString = new StringBuilder();
        for (Autor autores : this.autor) {
            autoresString.append("Nombre: ").append(autores.getNombre()).append(", ");
        }
        if (autoresString.isEmpty()) {
            autoresString.setLength(autoresString.length() - 2); // Eliminar la última coma y espacio
        }

        return "------------ Libro -----------\nTitulo: '" + this.titulo + "'\nAutor: '" + autoresString.toString() + "'\nIdioma: '" + this.idiomas + "'\nNumero de descargas: " + this.numeroDeDescargas + "\n------------------------------\n";
    }

}
