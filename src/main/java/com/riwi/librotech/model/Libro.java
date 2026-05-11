package com.riwi.librotech.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    private Long id;
    private String tittle;
    private String author;
    private String isbn;
    private int publicationYear;
}
