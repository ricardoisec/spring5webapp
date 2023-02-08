package pt.ricardocabete.livros.controllers.web;

import org.springframework.web.bind.annotation.*;
import pt.ricardocabete.livros.domain.Author;
import pt.ricardocabete.livros.domain.Book;
import pt.ricardocabete.livros.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping("/books")
    public String getBooks(Model model) {
        var books = bookRepository.findAll();
        model.addAttribute("books", books);
        model.addAttribute("nome", "Inês");

        return "books/lista_de_livros";
    }

    //Formulario para Adicionar livro
    @GetMapping("/books/add_form")
    public String formAddBooks(){
        return "books/form_adicionar_livro";
    }

    //Criar livro
    @PostMapping("/books")
    public String addBook(@ModelAttribute("book") Book book, Model model) {
        var livro = bookRepository.save(book);
        model.addAttribute("book", book);
        //lidar com erro
        return "books/livro_adicionado_com_sucesso";
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

}
