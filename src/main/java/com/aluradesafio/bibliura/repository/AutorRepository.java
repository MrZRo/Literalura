package com.aluradesafio.bibliura.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.aluradesafio.bibliura.model.Autor;

import java.util.List;
import java.util.Optional;


@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);

    @Query("select a from Autor a where a.birthYear <= :anio AND (a.deathYear >= :anio or a.deathYear is null)")
    List<Autor> findAuthorsAliveInYear(Integer anio);
}