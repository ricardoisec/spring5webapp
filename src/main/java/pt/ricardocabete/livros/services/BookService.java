package pt.ricardocabete.livros.services;

import org.springframework.stereotype.Service;
import pt.ricardocabete.livros.domain.Book;
import pt.ricardocabete.livros.exception.BookValidationException;
import pt.ricardocabete.livros.repositories.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) throws BookValidationException {
        validateBookTitle(book);
       // validateBookIsbn(book);

        return bookRepository.save(book);
    }

    private void validateBookTitle(Book book) throws BookValidationException {
        if (book.getTitle().isEmpty()) {
            throw new BookValidationException("Book title can't be empty or null");
        }

        if (book.getTitle().length() < 3) {
            throw new BookValidationException("Book title must have at least 3 characters");
        }
    }

//
//        //validacao isbn-10
//        if (book.getIsbn().length() == 10) {
//            int somaDigitos = 0;
//            for (int i = 0; i < book.getIsbn().length() - 1; i++) { //lenght menos 1 porque nao conta o ultimo digito
//                int digitosISBN = Character.getNumericValue(book.getIsbn().charAt(i)); //vai buscar cada numero no isbn
//                int calculo = digitosISBN * (10 - i); //(10 - i) porque tem de descer de 10 para 1
//                somaDigitos += calculo;
//                int digitoValidacao = somaDigitos % 11;
//
//                if (digitoValidacao != Character.getNumericValue(book.getIsbn().charAt(10))) {
//                    model.addAttribute("errorMessage", "Isbn invalid");
//                    return "books/errors/erro_criacao_livro";
//                }
//            }
//        }
//
//        //validacao isbn-13
//        if (book.getIsbn().length() == 13) {
//            int somaDigitos = 0;
//            for (int i = 0; i < book.getIsbn().length() - 1; i++) {
//                int digitosISBN = Character.getNumericValue(book.getIsbn().charAt(i));
//
//                if (i % 2 == 0) {
//                    somaDigitos += digitosISBN * 1; //se for par multiplica 1
//                } else { //se for impar multiplica por 3
//                    somaDigitos += somaDigitos * 3;
//                }
//
//                int digitoValidacao = 10 - (somaDigitos % 10);
//
//                if (digitoValidacao != Character.getNumericValue(book.getIsbn().charAt(13))) {
//                    model.addAttribute("errorMessage", "Isbn invalid");
//                    return "books/errors/erro_criacao_livro";
//                }
//
//            }
//        }
//    }
}
