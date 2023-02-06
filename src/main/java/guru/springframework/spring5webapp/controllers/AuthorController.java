package guru.springframework.spring5webapp.controllers;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @RequestMapping("/authors")
    public String getAuthors(Model model) {
        var authors = authorRepository.findAll(); // vai à BD buscar todos os autores
        model.addAttribute("authors", authors);

        return "authors/list";
    }

    @RequestMapping("/authors/show_create_form")
    public String mostrarFormCriarAutor(Model model) {
        return "authors/form_criacao_autor";
    }

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    public String createAuthor(@ModelAttribute("author") Author author, Model model) {
        var resultado = authorRepository.save(author); // INSERT INTO bla bla bla
        model.addAttribute("author", author);

        // TODO: Falta gerir o drama do erro
        return "authors/author_criado_com_sucesso";
    }
    
}
