package pt.ricardocabete.livros.services;

import org.junit.jupiter.api.Test;
import pt.ricardocabete.livros.domain.Book;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    BookService bookService = new BookService(null);

    /**

     * "a"
     * "A"
     * "1"
     * ";"
     *
     * ISBN
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





    @Test
    void validateBookIsbnValidIsbn13aTest() {
        // TODO: Meter este teste a passar
        var resultado = bookService.validateBookIsbn("978-3-16-148410-0");
        assertTrue(resultado);
    }

    @Test
    void validateBookIsbnValidIsbn13bTest() {
        var resultado = bookService.validateBookIsbn("978-0-306-40615-7");
        assertTrue(resultado);
    }

    @Test
    void validateBookIsbnInvalidIsbn13aTest() {
        var resultado = bookService.validateBookIsbn("978-3-16-148410-1");
        assertFalse(resultado);
    }

    @Test
    void validateBookIsbnInvalidIsbn13bTest() {
        assertThrows(NumberFormatException.class, () -> bookService.validateBookIsbn("978-3-16-148410-a"));
    }

}