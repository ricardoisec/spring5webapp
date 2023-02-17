package pt.ricardocabete.livros.services;

import org.junit.jupiter.api.Test;
import pt.ricardocabete.livros.domain.Book;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    BookService bookService = new BookService(null);

    /**
     * null
     * ""
     * " "
     * "a"
     * "A"
     * "1"
     * ";"
     *
     * ISBN
     * 13-valido : 978-3-16-148410-0
     * 13-invalido : 978-3-16-148410-1
     * 13-invalido : 978-3-16-148410-a
     * 10-valido: 0-545-01022-5
     * 10-invalido : 0-545-01022-1
     * 10-invalido : 0-545-01022-a
     * estúpido pequeno 978-3-16
     * estúpido grande 978-3-16-148410-0-978-3-16-148410-0
     */

    // public boolean validateBookIsbn(String isbn)

    @Test
    void validateBookIsbnNullTest() {
        var resultado = bookService.validateBookIsbn(null);
        assertFalse(resultado);
    }

    @Test
    void validateBookIsbnEmptyStringTest() {
        var resultado = bookService.validateBookIsbn("");
        assertFalse(resultado);
    }

    @Test
    void validateBookIsbnSpecialCharTest() {
        var resultado = bookService.validateBookIsbn(";");
        assertFalse(resultado);
    }
}