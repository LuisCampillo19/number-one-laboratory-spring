package com.riwi.librotech.controller;

import com.riwi.librotech.model.Libro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    private List<Libro> libros = new ArrayList<>();
    private Long nextId = 1L;

    /**
     * GET /api/libros
     * return books all. If don't anything, return void list
     */
    @GetMapping
    public ResponseEntity<List<Libro>> listarLibros() {
        return ResponseEntity.ok(libros); // return code 200 OK
    }

    /**
     * GET /api/libros/{id}
     * Search a id book. If don't exist, return 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<Libro> obteberLibro(@PathVariable long id) {
        Optional<Libro> libro = buscarPorId(id);

        if (libro.isPresent()) {
            return ResponseEntity.ok(libro.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/libros
     * Create a book new
     */
    @PostMapping
    public ResponseEntity<Libro> crearLibro(@RequestBody Libro libro) {
        libro.setId(nextId++);
        libros.add(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(libro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id, @RequestBody Libro libroActualizado) {
        Optional<Libro> libroExistente = buscarPorId(id);

        if (libroExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Libro libro = libroExistente.get();
        libro.setTittle(libroActualizado.getTittle());
        libro.setAuthor(libroActualizado.getAuthor());
        libro.setIsbn(libroActualizado.getIsbn());
        libro.setPublicationYear(libroActualizado.getPublicationYear());

        return ResponseEntity.ok(libro);
    }

    /**
     * PATCH /api/libros/{id}
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Libro> actualizarParcial(@PathVariable Long id,
                                                    @RequestBody Libro cambios) {
        Optional<Libro> libroExistente = buscarPorId(id);

        if (libroExistente.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        Libro libro = libroExistente.get();

        // Solo actualizamos si el campo viene con valor (no null)
        if (cambios.getTittle() != null)          libro.setTittle(cambios.getTittle());
        if (cambios.getAuthor() != null)           libro.setAuthor(cambios.getAuthor());
        if (cambios.getIsbn() != null)            libro.setIsbn(cambios.getIsbn());
        if (cambios.getPublicationYear() != 0)    libro.setPublicationYear(cambios.getPublicationYear());

        return ResponseEntity.ok(libro); // 200 OK
    }
        @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        Optional<Libro> libro = buscarPorId(id);

        if (libro.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        libros.remove(libro.get());
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    /**
    * Method aux
    */
    private Optional<Libro> buscarPorId(Long id) {
        return libros.stream()
                     .filter(l -> l.getId().equals(id))
                     .findFirst();
    }
}
