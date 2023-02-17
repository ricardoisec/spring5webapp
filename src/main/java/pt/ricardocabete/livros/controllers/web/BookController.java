package pt.ricardocabete.livros.controllers.web;

import org.springframework.web.bind.annotation.*;
import pt.ricardocabete.livros.domain.Book;
import pt.ricardocabete.livros.exception.BookValidationException;
import pt.ricardocabete.livros.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import pt.ricardocabete.livros.services.BookService;

import java.util.NoSuchElementException;

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
        try {
            bookService.updateBook(book, id);

            return "redirect:/books";
        } catch (BookValidationException | NoSuchElementException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "books/errors/erro_criacao_livro"; // mudar a vista do erro
        }
    }


    //apagar o livro
    @GetMapping("/apagar_livro/{id}")
    public String deletePublisher(@PathVariable("id") long id, Model model) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }




}
