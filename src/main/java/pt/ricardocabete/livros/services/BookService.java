package pt.ricardocabete.livros.services;

import org.springframework.stereotype.Service;
import pt.ricardocabete.livros.domain.Author;
import pt.ricardocabete.livros.domain.Book;
import pt.ricardocabete.livros.exception.BookNotFoundException;
import pt.ricardocabete.livros.exception.BookValidationException;
import pt.ricardocabete.livros.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) throws BookValidationException {
        validateBookTitle(book.getTitle());
        validateBookIsbn(book.getIsbn());

        return bookRepository.save(book);
    }

    public void updateBook(Book book, Long id) throws BookValidationException {
        validateBookTitle(book.getTitle());
        validateBookIsbn(book.getIsbn());

        var existingBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        existingBook.setTitle(book.getTitle());
        existingBook.setIsbn(book.getIsbn());

        bookRepository.save(existingBook);
    }

    public List<Book> getBookOfAuthor(Author author) {
        return new ArrayList<>(author.getBooks());
    }






















    public void validateBookTitle(String bookTitle) throws BookValidationException {
        if (bookTitle.isEmpty()) {
            throw new BookValidationException("Book title can't be empty or null");
        }

        if (bookTitle.length() < 3) {
            throw new BookValidationException("Book title must have at least 3 characters");
        }
    }

    public boolean validateBookIsbn(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            return false;
        }

        // 978-0-306-40615-7
        isbn = isbn.replace("-", "");
        // 9780306406157
        Integer resultado = -1;
        int somaDigitos = 0;
        for (int i = 0; i < isbn.length() - 2; i++) {
            try {
                var number = Integer.parseInt(String.valueOf(isbn.charAt(i)));
                if (isbn.length() == 10) {
                    int calculo = number * (10 - i); //(10 - i) porque tem de descer de 10 para 1
                    somaDigitos += calculo;
                    resultado = somaDigitos % 11;
                }

                if (isbn.length() == 13) {
                    StringBuilder isbn13 = new StringBuilder("978");
                    String isbn9 = isbn.substring(0, 9);
                    isbn13.append(isbn9);

                    if (i % 2 == 0) {   //se for par multiplica 1
                        somaDigitos += number * 1;
                    } else {            //se for impar multiplica por 3
                        somaDigitos += somaDigitos * 3;
                    }

                    int digitoValidacao = 10 - (somaDigitos % 10);
                    isbn13.append(digitoValidacao);
                }

            } catch (NumberFormatException exception) {
                return false;
            }
        }
        if (resultado.toString().charAt(0) == isbn.charAt(isbn.length() - 1)) {
            return true;
        }

        return false;
    }
}
