package com.aluracursos.challenge_literalura.repositories;

import com.aluracursos.challenge_literalura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ILibroRepositorio extends JpaRepository <Libro, Long>{
    List<Libro> findByIdiomas(String idioma);
}
