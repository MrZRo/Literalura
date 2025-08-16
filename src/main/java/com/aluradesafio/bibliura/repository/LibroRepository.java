package com.aluradesafio.bibliura.repository;

import com.aluradesafio.bibliura.model.Idioma;
import com.aluradesafio.bibliura.model.Autor;
import com.aluradesafio.bibliura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String nombreLibro);

    List<Libro> findByIdiomasIn(List<Idioma> idiomas);
}
