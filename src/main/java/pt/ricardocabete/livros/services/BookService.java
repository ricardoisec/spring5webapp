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

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
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

    public void deleteById(long id) {
        bookRepository.deleteById(id);
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

        //validar se é nulo ou empty
        if (isbn == null || isbn.isEmpty()) {
            return false;
        }

        //remover traços
        isbn = isbn.replace("-", "");

        if (isbn.length() < 10) {
            return false;
        }

        // converter ISBN10 em ISBN13
        if (isbn.length() == 10) {
            isbn = "978" + isbn;
        }

        if (isbn.length() != 13) {
            return false;
        }

        //calculo da soma dos digitos
        int somaDigitos = 0;
        for (int i = 0; i < isbn.length() - 1; i++) {
            try {
                int number = Integer.parseInt(String.valueOf(isbn.charAt(i)));

                if (i % 2 == 0) {  //se par -> multiplica nº por 1
                    somaDigitos += number;
                } else {            //se impar -> multiplica nº por 3
                    somaDigitos += number * 3;
                }
            } catch (NumberFormatException exception) {
                return false;
            }
        }

        //calculo do digito de validacao
        int lastDigit = Integer.parseInt(String.valueOf(isbn.charAt(isbn.length() - 1)));
        int digitoValidacao = 10 - (somaDigitos % 10);

        //excecao porque digito de validacao nao pode ser um nº de 2 digitos.
        // Isto acontece quando a somaDigitos % 10 = 0. Entao converte-se o digitoValidaco para zero
        if (digitoValidacao == 10) {
            digitoValidacao = 0;
        }

        return lastDigit == digitoValidacao;
    }

}
