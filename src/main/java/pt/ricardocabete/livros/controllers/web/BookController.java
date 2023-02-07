package pt.ricardocabete.livros.controllers.web;

import pt.ricardocabete.livros.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

        return "books/list";
    }
}
