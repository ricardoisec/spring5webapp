package pt.ricardocabete.livros.controllers.web;

import org.springframework.web.bind.annotation.*;
import pt.ricardocabete.livros.domain.Author;
import pt.ricardocabete.livros.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Optional;

@Controller
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/authors")
    public String getAuthors(Model model) {
        var authors = authorRepository.findAll(); // vai à BD buscar todos os autores
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
        // CABETE: Validação de dados - esta não é a única maneira de validar coisas mas é a mais simples para já
        if (author.getFirstName().isEmpty()) {
            model.addAttribute("errorMessage", "O first name do autor não pode ficar vazio");
            return "authors/errors/erro_criacao_autor";
        }

        var resultado = authorRepository.save(author); // INSERT INTO bla bla bla
        model.addAttribute("author", author);

        // TODO: Falta gerir o drama do erro
        return "authors/author_criado_com_sucesso";
    }


    // Editar um autor é feito em 2 passos:
    // 1. carregas no link de editar e vais para uma página com o formulário de edição
    //    isso é reflectido no método mostrarFormEditarAutor mas agora é preciso ter os dados actuais e não um form vazio
    // 2. uma vez nesse formulário carregas no botão para submeter o form e ele envia os dados em PUT
    //    isso é reflectido no método updateAuthor
    @GetMapping("/authors/show_update_form/{id}")
    public String mostrarFormEditarAutor(@PathVariable Long id, Model model) {
        var author = authorRepository.findById(id).orElse(null);
        model.addAttribute("author", author);
        return "authors/form_edicao_autor";
    }

    // Se isto fosse em REST devia ser um PUT porque os updates são feitos com PUT ou PATCH
    // Infelizmente os forms de HTTP só gostam de GET e POST e lidam mal com os outros portanto o que fiz foi
    // criar uma nova nota POST (diferente da rota da criação) e fazer o update com um POST.
    // A alternativa era um AJAX com javascript (simples de fazer mas para começar mais vale assim)
    @PostMapping("/authors/{id}")
    public String updateAuthor(@ModelAttribute("author") Author author, @PathVariable Long id, Model model) {
        var existingAuthor = authorRepository.findById(id).orElseThrow();
        existingAuthor.setFirstName(author.getFirstName());
        existingAuthor.setLastName(author.getLastName());

        Author updatedAuthor = authorRepository.save(existingAuthor);

        return "redirect:/authors";
    }



    // DELETE (também vai ter que ser com GET ou POST, inventa uma rota tipo /authors/apagar/{id} ou similar)
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") long id, Model model) {
        authorRepository.deleteById(id);
        return "redirect:/authors";
    }
}
