package pt.ricardocabete.livros.controllers.web;

import org.springframework.web.bind.annotation.*;
import pt.ricardocabete.livros.domain.Author;
import pt.ricardocabete.livros.domain.Book;
import pt.ricardocabete.livros.exception.AuthorValidationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import pt.ricardocabete.livros.services.AuthorService;
import pt.ricardocabete.livros.services.BookService;

import java.util.NoSuchElementException;
import java.util.Set;

@Controller
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/authors")
    public String getAuthors(Model model) {
        var authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);

        return "authors/listar_todos_os_autores";
    }

    // Criar um autor é feito em 2 passos:
    // 1. carregas no link de criar e vais para uma página com o formulário de criação
    //    isso é reflectido no método mostrarFormCriarAutor
    // 2. uma vez nesse formulário carregas no botão para submeter o form e ele envia os dados em POST
    //    isso é reflectido no método createAuthor
    @GetMapping("/authors/show_create_form")
    public String mostrarFormCriarAutor() {
        return "authors/form_criacao_autor";
    }


    @PostMapping("/authors")
    public String createAuthor(@ModelAttribute("author") Author author, Model model) {
        try {
            authorService.createAuthor(author);
            model.addAttribute("author", author);

            return "authors/author_criado_com_sucesso";
        } catch (AuthorValidationException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "authors/errors/erro_criacao_autor";
        }
    }


    // Editar um autor é feito em 2 passos:
    // 1. carregas no link de editar e vais para uma página com o formulário de edição
    //    isso é reflectido no método mostrarFormEditarAutor mas agora é preciso ter os dados actuais e não um form vazio
    // 2. uma vez nesse formulário carregas no botão para submeter o form e ele envia os dados em PUT
    //    isso é reflectido no método updateAuthor
    @GetMapping("/authors/show_update_form/{id}")
    public String mostrarFormEditarAutor(@PathVariable Long id, Model model) {
        return "authors/form_edicao_autor";
    }

    // Se isto fosse em REST devia ser um PUT porque os updates são feitos com PUT ou PATCH
    // Infelizmente os forms de HTTP só gostam de GET e POST e lidam mal com os outros portanto o que fiz foi
    // criar uma nova nota POST (diferente da rota da criação) e fazer o update com um POST.
    // A alternativa era um AJAX com javascript (simples de fazer mas para começar mais vale assim)
    @PostMapping("/authors/{id}")
    public String updateAuthor(@ModelAttribute("author") Author author, @PathVariable Long id, Model model) {
       try {
           authorService.updateAutor(author, id);

           return "redirect:/authors";
       } catch (AuthorValidationException | NoSuchElementException exception) {
           model.addAttribute("errorMessage", exception.getMessage());
           return "erro_edicao_autor";
       }
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") long id, Author author, Model model) {
        //apagar todos os livros que tenham esse autor e depois apagar o autor
        Set<Book> listaDeLivrosDoAutor = bookService.getBooksOfAuthor(author);
        listaDeLivrosDoAutor.forEach(book -> {
            // iterar todos os livros para ver se há mais autores e se houver remover o livro deles
            // bookService.deleteBook(book);
        });

        // delete À listaDeLivrosDoAutor
        authorService.deleteById(id);

        return "redirect:/authors";
    }
}
