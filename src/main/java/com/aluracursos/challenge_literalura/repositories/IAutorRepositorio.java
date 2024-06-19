package com.aluracursos.challenge_literalura.repositories;

import com.aluracursos.challenge_literalura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAutorRepositorio extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a JOIN a.libros l WHERE a.anioDeFallecimiento IS NULL OR a.anioDeFallecimiento > :fecha")
    List<Autor> findAutoresVivosPorFecha(int fecha);
}
