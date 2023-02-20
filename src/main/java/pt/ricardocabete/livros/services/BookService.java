package pt.ricardocabete.livros.services;

import org.springframework.stereotype.Service;
import pt.ricardocabete.livros.domain.Author;
import pt.ricardocabete.livros.domain.Book;
import pt.ricardocabete.livros.exception.BookNotFoundException;
import pt.ricardocabete.livros.exception.BookValidationException;
import pt.ricardocabete.livros.repositories.BookRepository;

import java.util.Set;

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

    public Set<Book> getBooksOfAuthor(Author author) {
        return author.getBooks();
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

        if (isbn.length() < 10) {
            return false;
        }

        // converte ISBN10 em ISBN13
        if (isbn.length() == 10) {
            isbn = "978" + isbn;
        }

        if (isbn.length() != 13) {
            return false;
        }


        Integer resultado = -1;
        int somaDigitos = 0;
        for (int i = 0; i < isbn.length() - 1; i++) {
            try {
                var number = Integer.parseInt(String.valueOf(isbn.charAt(i)));

                if (i % 2 == 0) {   //se for par multiplica 1
                    somaDigitos += number;
                } else {            //se for impar multiplica por 3
                    somaDigitos += number * 3;
                }
            } catch (NumberFormatException exception) {
                return false;
            }
        }

        int digitoValidacao = 10 - (somaDigitos % 10);
        if (Integer.parseInt(String.valueOf(isbn.charAt(isbn.length() - 1))) == digitoValidacao) {
            return true;
        }

//        if (resultado.toString().charAt(0) == isbn.charAt(isbn.length() - 1)) {
//            return true;
//        }

        return false;
    }
}
