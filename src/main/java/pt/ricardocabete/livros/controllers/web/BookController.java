package pt.ricardocabete.livros.controllers.web;

import org.springframework.web.bind.annotation.*;
import pt.ricardocabete.livros.domain.Author; // CABETE: Porque diabo o livro tem que conhecer autores?
import pt.ricardocabete.livros.domain.Book;
import pt.ricardocabete.livros.exception.BookValidationException;
import pt.ricardocabete.livros.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import pt.ricardocabete.livros.services.BookService;

@Controller
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String getBooks(Model model) {
        var books = bookRepository.findAll();
        model.addAttribute("books", books);
        model.addAttribute("nome", "Inês");

        return "books/lista_de_livros";
    }

    //Formulario para Adicionar livro
    @GetMapping("/books/add_form")
    public String formAddBooks() {
        return "books/form_adicionar_livro";
    }

    //Criar livro
    @PostMapping("/books")
    public String addBook(@ModelAttribute("book") Book book, Model model) {
        try {
            var bookAdicionado = bookService.addBook(book);
            model.addAttribute("book", bookAdicionado);

            return "books/livro_adicionado_com_sucesso";
        } catch (BookValidationException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "books/errors/erro_criacao_livro";
        }
    }

    //Editar o livro

    //mostrar formulario
    @GetMapping("/books/update_form/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        var book = bookRepository.findById(id).orElse(null);
        model.addAttribute("book", book);
        return "books/update_form";
    }

    //update do livro
    @PostMapping("/books/{id}")
    public String updateLivro(@ModelAttribute("book") Book book, @PathVariable Long id, Model model) {
        var existingBook = bookRepository.findById(id).orElseThrow();
        existingBook.setTitle(book.getTitle());
        existingBook.setIsbn(book.getIsbn());

        Book updatedBook = bookRepository.save(existingBook);

        return "redirect:/books";
    }


    //apagar o livro
    @GetMapping("/apagar_livro/{id}")
    public String deletePublisher(@PathVariable("id") long id, Model model) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }


    private boolean validateISBN(String isbn) {
        // 978-0-306-40615-7
        isbn = isbn.replace("-", "");
        // 9780306406157
        Integer resultado = -1;
        int somaDigitos = 0;
        for (int i = 0; i < isbn.length() - 2; i++) {
            try {
                var number = Integer.parseInt(String.valueOf(isbn.charAt(i)));
                // TODO: fazer as contas e ver quanto dá
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
