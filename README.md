# LibroTech API 📚

A RESTful API built with Spring Boot for managing a community library catalog. This project was developed as part of Module 6.1 — MVC Architecture with Spring Boot.

---

## Tech Stack

- Java 17
- Spring Boot 4.0.6
- Spring Web (embedded Tomcat)
- Spring Boot DevTools
- Lombok
- Jackson (JSON serialization)

---

## Project Structure

```
src/main/java/com/riwi/librotech/
├── LibrotechApplication.java
├── controller/
│   ├── LibroController.java
│   └── CategoriaController.java
└── model/
    ├── Libro.java
    └── Categoria.java
```

---

## Getting Started

### Prerequisites

- JDK 17+
- Maven 3.8+

### Running the application

```bash
./mvnw spring-boot:run
```

The server starts at `http://localhost:8080`.

> **Note:** Data is stored in memory only. All records are lost when the application restarts.

---

## API Reference

### Books — `/api/libros`

#### Get all books
```
GET /api/libros
```
Response `200 OK`
```json
[
  {
    "id": 1,
    "titulo": "Cien años de soledad",
    "autor": "Gabriel García Márquez",
    "isbn": "978-0307474728",
    "anioPublicacion": 1967
  }
]
```

---

#### Get book by ID
```
GET /api/libros/{id}
```
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | `Long` | Book ID |

Response `200 OK` — book found  
Response `404 Not Found` — book does not exist

---

#### Create a book
```
POST /api/libros
```
Request body:
```json
{
  "titulo": "Cien años de soledad",
  "autor": "Gabriel García Márquez",
  "isbn": "978-0307474728",
  "anioPublicacion": 1967
}
```
Response `201 Created`
```json
{
  "id": 1,
  "titulo": "Cien años de soledad",
  "autor": "Gabriel García Márquez",
  "isbn": "978-0307474728",
  "anioPublicacion": 1967
}
```

---

#### Replace a book (full update)
```
PUT /api/libros/{id}
```
All fields must be included in the request body. Replaces the entire resource.

Response `200 OK` — updated  
Response `404 Not Found` — book does not exist

---

#### Update a book (partial update)
```
PATCH /api/libros/{id}
```
Only the fields included in the body are updated. Fields not sent remain unchanged.

```json
{
  "titulo": "Cien años de soledad (revised edition)"
}
```

Response `200 OK` — updated  
Response `404 Not Found` — book does not exist

---

#### Delete a book
```
DELETE /api/libros/{id}
```
Response `204 No Content` — deleted  
Response `404 Not Found` — book does not exist

---

### Categories — `/api/categorias`

#### Get all categories
```
GET /api/categorias
```

#### Create a category
```
POST /api/categorias
```
```json
{
  "nombre": "Ficción"
}
```

---

## HTTP Status Codes

| Code | Meaning |
|------|---------|
| `200 OK` | Request succeeded |
| `201 Created` | Resource created successfully |
| `204 No Content` | Resource deleted, no body returned |
| `400 Bad Request` | Invalid or malformed request body |
| `404 Not Found` | Resource does not exist |

---

## Key Concepts — MVC Pattern in REST

| Layer | Role | Example |
|-------|------|---------|
| **Model** | Represents data | `Libro.java`, `Categoria.java` |
| **View** | JSON response (auto by Spring) | Serialized by Jackson |
| **Controller** | Handles HTTP requests | `LibroController.java` |

---

## Important Notes

- Field names in your JSON body must exactly match the model field names.
- `anioPublicacion` is declared as `Integer` (not `int`) to allow `null` values in partial updates.
- The `id` field is auto-assigned by the controller — do not include it in POST request bodies.
- ISBN is a real-world unique book identifier (e.g. `978-0307474728`). In a production system it would have a `UNIQUE` constraint at the database level.