package com.aluradesafio.bibliura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    // Relación ManyToOne: Muchos libros pueden tener un solo autor
    @ManyToOne
    private Autor autor;

    // Relación ElementCollection con Enum: Para guardar la lista de idiomas
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "libro_idiomas", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "idioma")
    private List<Idioma> idiomas;

    private Integer numeroDeDescargas;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        if (datosLibro.autores() != null && !datosLibro.autores().isEmpty()) {
            DatosAutor datosAutor = datosLibro.autores().get(0);
            this.autor = new Autor(datosAutor.nombre(), datosAutor.fechaNacimiento(), datosAutor.fechaFallecimiento());
        }
        this.idiomas = datosLibro.idiomas().stream()
                .map(Idioma::fromCodigo)
                .collect(Collectors.toList());

        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        String nombreAutor = (autor != null) ? autor.getNombre() : "Desconocido";
        String listaIdiomas = (idiomas != null) ? idiomas.stream().map(Idioma::getNombreCompleto).collect(Collectors.joining(", ")) : "N/A";

        return "Libro" + '\n' +
                "   Titulo: " + titulo + '\n' +
                "   Autor: " + nombreAutor + '\n' +
                "   Idiomas: " + listaIdiomas + '\n' +
                "   Numero de descargas: " + numeroDeDescargas ;
    }
}